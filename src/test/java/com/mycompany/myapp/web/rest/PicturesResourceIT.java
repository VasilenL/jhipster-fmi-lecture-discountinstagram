package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DiscountInstagramApp;
import com.mycompany.myapp.domain.Pictures;
import com.mycompany.myapp.repository.PicturesRepository;
import com.mycompany.myapp.service.PicturesService;
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
 * Integration tests for the {@link PicturesResource} REST controller.
 */
@SpringBootTest(classes = DiscountInstagramApp.class)
public class PicturesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FULL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PicturesRepository picturesRepository;

    @Autowired
    private PicturesService picturesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPicturesMockMvc;

    private Pictures pictures;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PicturesResource picturesResource = new PicturesResource(picturesService);
        this.restPicturesMockMvc = MockMvcBuilders.standaloneSetup(picturesResource)
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
    public static Pictures createEntity() {
        Pictures pictures = new Pictures()
            .name(DEFAULT_NAME)
            .fullTitle(DEFAULT_FULL_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return pictures;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pictures createUpdatedEntity() {
        Pictures pictures = new Pictures()
            .name(UPDATED_NAME)
            .fullTitle(UPDATED_FULL_TITLE)
            .description(UPDATED_DESCRIPTION);
        return pictures;
    }

    @BeforeEach
    public void initTest() {
        picturesRepository.deleteAll();
        pictures = createEntity();
    }

    @Test
    public void createPictures() throws Exception {
        int databaseSizeBeforeCreate = picturesRepository.findAll().size();

        // Create the Pictures
        restPicturesMockMvc.perform(post("/api/pictures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pictures)))
            .andExpect(status().isCreated());

        // Validate the Pictures in the database
        List<Pictures> picturesList = picturesRepository.findAll();
        assertThat(picturesList).hasSize(databaseSizeBeforeCreate + 1);
        Pictures testPictures = picturesList.get(picturesList.size() - 1);
        assertThat(testPictures.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPictures.getFullTitle()).isEqualTo(DEFAULT_FULL_TITLE);
        assertThat(testPictures.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createPicturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = picturesRepository.findAll().size();

        // Create the Pictures with an existing ID
        pictures.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPicturesMockMvc.perform(post("/api/pictures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pictures)))
            .andExpect(status().isBadRequest());

        // Validate the Pictures in the database
        List<Pictures> picturesList = picturesRepository.findAll();
        assertThat(picturesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPictures() throws Exception {
        // Initialize the database
        picturesRepository.save(pictures);

        // Get all the picturesList
        restPicturesMockMvc.perform(get("/api/pictures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pictures.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fullTitle").value(hasItem(DEFAULT_FULL_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getPictures() throws Exception {
        // Initialize the database
        picturesRepository.save(pictures);

        // Get the pictures
        restPicturesMockMvc.perform(get("/api/pictures/{id}", pictures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pictures.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fullTitle").value(DEFAULT_FULL_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    public void getNonExistingPictures() throws Exception {
        // Get the pictures
        restPicturesMockMvc.perform(get("/api/pictures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePictures() throws Exception {
        // Initialize the database
        picturesService.save(pictures);

        int databaseSizeBeforeUpdate = picturesRepository.findAll().size();

        // Update the pictures
        Pictures updatedPictures = picturesRepository.findById(pictures.getId()).get();
        updatedPictures
            .name(UPDATED_NAME)
            .fullTitle(UPDATED_FULL_TITLE)
            .description(UPDATED_DESCRIPTION);

        restPicturesMockMvc.perform(put("/api/pictures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPictures)))
            .andExpect(status().isOk());

        // Validate the Pictures in the database
        List<Pictures> picturesList = picturesRepository.findAll();
        assertThat(picturesList).hasSize(databaseSizeBeforeUpdate);
        Pictures testPictures = picturesList.get(picturesList.size() - 1);
        assertThat(testPictures.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPictures.getFullTitle()).isEqualTo(UPDATED_FULL_TITLE);
        assertThat(testPictures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingPictures() throws Exception {
        int databaseSizeBeforeUpdate = picturesRepository.findAll().size();

        // Create the Pictures

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPicturesMockMvc.perform(put("/api/pictures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pictures)))
            .andExpect(status().isBadRequest());

        // Validate the Pictures in the database
        List<Pictures> picturesList = picturesRepository.findAll();
        assertThat(picturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePictures() throws Exception {
        // Initialize the database
        picturesService.save(pictures);

        int databaseSizeBeforeDelete = picturesRepository.findAll().size();

        // Delete the pictures
        restPicturesMockMvc.perform(delete("/api/pictures/{id}", pictures.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pictures> picturesList = picturesRepository.findAll();
        assertThat(picturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
