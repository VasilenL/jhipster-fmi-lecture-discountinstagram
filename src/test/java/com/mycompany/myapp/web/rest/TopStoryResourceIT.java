package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DiscountInstagramApp;
import com.mycompany.myapp.domain.TopStory;
import com.mycompany.myapp.repository.TopStoryRepository;
import com.mycompany.myapp.service.TopStoryService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopStoryResource} REST controller.
 */
@SpringBootTest(classes = DiscountInstagramApp.class)
public class TopStoryResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private TopStoryRepository topStoryRepository;

    @Autowired
    private TopStoryService topStoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restTopStoryMockMvc;

    private TopStory topStory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopStoryResource topStoryResource = new TopStoryResource(topStoryService);
        this.restTopStoryMockMvc = MockMvcBuilders.standaloneSetup(topStoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopStory createEntity() {
        TopStory topStory = new TopStory()
            .title(DEFAULT_TITLE);
        return topStory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopStory createUpdatedEntity() {
        TopStory topStory = new TopStory()
            .title(UPDATED_TITLE);
        return topStory;
    }

    @BeforeEach
    public void initTest() {
        topStoryRepository.deleteAll();
        topStory = createEntity();
    }

    @Test
    public void createTopStory() throws Exception {
        int databaseSizeBeforeCreate = topStoryRepository.findAll().size();

        // Create the TopStory
        restTopStoryMockMvc.perform(post("/api/top-stories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topStory)))
            .andExpect(status().isCreated());

        // Validate the TopStory in the database
        List<TopStory> topStoryList = topStoryRepository.findAll();
        assertThat(topStoryList).hasSize(databaseSizeBeforeCreate + 1);
        TopStory testTopStory = topStoryList.get(topStoryList.size() - 1);
        assertThat(testTopStory.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    public void createTopStoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topStoryRepository.findAll().size();

        // Create the TopStory with an existing ID
        topStory.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopStoryMockMvc.perform(post("/api/top-stories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topStory)))
            .andExpect(status().isBadRequest());

        // Validate the TopStory in the database
        List<TopStory> topStoryList = topStoryRepository.findAll();
        assertThat(topStoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllTopStories() throws Exception {
        // Initialize the database
        topStoryRepository.save(topStory);

        // Get all the topStoryList
        restTopStoryMockMvc.perform(get("/api/top-stories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topStory.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    public void getTopStory() throws Exception {
        // Initialize the database
        topStoryRepository.save(topStory);

        // Get the topStory
        restTopStoryMockMvc.perform(get("/api/top-stories/{id}", topStory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topStory.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    public void getNonExistingTopStory() throws Exception {
        // Get the topStory
        restTopStoryMockMvc.perform(get("/api/top-stories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTopStory() throws Exception {
        // Initialize the database
        topStoryService.save(topStory);

        int databaseSizeBeforeUpdate = topStoryRepository.findAll().size();

        // Update the topStory
        TopStory updatedTopStory = topStoryRepository.findById(topStory.getId()).get();
        updatedTopStory
            .title(UPDATED_TITLE);

        restTopStoryMockMvc.perform(put("/api/top-stories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopStory)))
            .andExpect(status().isOk());

        // Validate the TopStory in the database
        List<TopStory> topStoryList = topStoryRepository.findAll();
        assertThat(topStoryList).hasSize(databaseSizeBeforeUpdate);
        TopStory testTopStory = topStoryList.get(topStoryList.size() - 1);
        assertThat(testTopStory.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    public void updateNonExistingTopStory() throws Exception {
        int databaseSizeBeforeUpdate = topStoryRepository.findAll().size();

        // Create the TopStory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopStoryMockMvc.perform(put("/api/top-stories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topStory)))
            .andExpect(status().isBadRequest());

        // Validate the TopStory in the database
        List<TopStory> topStoryList = topStoryRepository.findAll();
        assertThat(topStoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTopStory() throws Exception {
        // Initialize the database
        topStoryService.save(topStory);

        int databaseSizeBeforeDelete = topStoryRepository.findAll().size();

        // Delete the topStory
        restTopStoryMockMvc.perform(delete("/api/top-stories/{id}", topStory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopStory> topStoryList = topStoryRepository.findAll();
        assertThat(topStoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
