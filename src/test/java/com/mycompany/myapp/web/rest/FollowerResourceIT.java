package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DiscountInstagramApp;
import com.mycompany.myapp.domain.Follower;
import com.mycompany.myapp.repository.FollowerRepository;
import com.mycompany.myapp.service.FollowerService;
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
 * Integration tests for the {@link FollowerResource} REST controller.
 */
@SpringBootTest(classes = DiscountInstagramApp.class)
public class FollowerResourceIT {

    private static final String DEFAULT_PROFILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_NAME = "BBBBBBBBBB";

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private FollowerService followerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restFollowerMockMvc;

    private Follower follower;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowerResource followerResource = new FollowerResource(followerService);
        this.restFollowerMockMvc = MockMvcBuilders.standaloneSetup(followerResource)
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
    public static Follower createEntity() {
        Follower follower = new Follower()
            .profileName(DEFAULT_PROFILE_NAME);
        return follower;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follower createUpdatedEntity() {
        Follower follower = new Follower()
            .profileName(UPDATED_PROFILE_NAME);
        return follower;
    }

    @BeforeEach
    public void initTest() {
        followerRepository.deleteAll();
        follower = createEntity();
    }

    @Test
    public void createFollower() throws Exception {
        int databaseSizeBeforeCreate = followerRepository.findAll().size();

        // Create the Follower
        restFollowerMockMvc.perform(post("/api/followers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(follower)))
            .andExpect(status().isCreated());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeCreate + 1);
        Follower testFollower = followerList.get(followerList.size() - 1);
        assertThat(testFollower.getProfileName()).isEqualTo(DEFAULT_PROFILE_NAME);
    }

    @Test
    public void createFollowerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followerRepository.findAll().size();

        // Create the Follower with an existing ID
        follower.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowerMockMvc.perform(post("/api/followers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(follower)))
            .andExpect(status().isBadRequest());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllFollowers() throws Exception {
        // Initialize the database
        followerRepository.save(follower);

        // Get all the followerList
        restFollowerMockMvc.perform(get("/api/followers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follower.getId())))
            .andExpect(jsonPath("$.[*].profileName").value(hasItem(DEFAULT_PROFILE_NAME)));
    }
    
    @Test
    public void getFollower() throws Exception {
        // Initialize the database
        followerRepository.save(follower);

        // Get the follower
        restFollowerMockMvc.perform(get("/api/followers/{id}", follower.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(follower.getId()))
            .andExpect(jsonPath("$.profileName").value(DEFAULT_PROFILE_NAME));
    }

    @Test
    public void getNonExistingFollower() throws Exception {
        // Get the follower
        restFollowerMockMvc.perform(get("/api/followers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFollower() throws Exception {
        // Initialize the database
        followerService.save(follower);

        int databaseSizeBeforeUpdate = followerRepository.findAll().size();

        // Update the follower
        Follower updatedFollower = followerRepository.findById(follower.getId()).get();
        updatedFollower
            .profileName(UPDATED_PROFILE_NAME);

        restFollowerMockMvc.perform(put("/api/followers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFollower)))
            .andExpect(status().isOk());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeUpdate);
        Follower testFollower = followerList.get(followerList.size() - 1);
        assertThat(testFollower.getProfileName()).isEqualTo(UPDATED_PROFILE_NAME);
    }

    @Test
    public void updateNonExistingFollower() throws Exception {
        int databaseSizeBeforeUpdate = followerRepository.findAll().size();

        // Create the Follower

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowerMockMvc.perform(put("/api/followers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(follower)))
            .andExpect(status().isBadRequest());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteFollower() throws Exception {
        // Initialize the database
        followerService.save(follower);

        int databaseSizeBeforeDelete = followerRepository.findAll().size();

        // Delete the follower
        restFollowerMockMvc.perform(delete("/api/followers/{id}", follower.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
