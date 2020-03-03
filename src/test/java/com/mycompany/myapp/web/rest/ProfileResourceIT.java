package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DiscountInstagramApp;
import com.mycompany.myapp.domain.Profile;
import com.mycompany.myapp.repository.ProfileRepository;
import com.mycompany.myapp.service.ProfileService;
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
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = DiscountInstagramApp.class)
public class ProfileResourceIT {

    private static final String DEFAULT_PROFILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_POSTS = 1;
    private static final Integer UPDATED_TOTAL_POSTS = 2;

    private static final Integer DEFAULT_TOTAL_FOLLOWERS = 1;
    private static final Integer UPDATED_TOTAL_FOLLOWERS = 2;

    private static final Integer DEFAULT_FOLLOWS = 1;
    private static final Integer UPDATED_FOLLOWS = 2;

    private static final String DEFAULT_PROFILE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity() {
        Profile profile = new Profile()
            .profileName(DEFAULT_PROFILE_NAME)
            .name(DEFAULT_NAME)
            .totalPosts(DEFAULT_TOTAL_POSTS)
            .totalFollowers(DEFAULT_TOTAL_FOLLOWERS)
            .follows(DEFAULT_FOLLOWS)
            .profileDescription(DEFAULT_PROFILE_DESCRIPTION)
            .profilePicture(DEFAULT_PROFILE_PICTURE);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity() {
        Profile profile = new Profile()
            .profileName(UPDATED_PROFILE_NAME)
            .name(UPDATED_NAME)
            .totalPosts(UPDATED_TOTAL_POSTS)
            .totalFollowers(UPDATED_TOTAL_FOLLOWERS)
            .follows(UPDATED_FOLLOWS)
            .profileDescription(UPDATED_PROFILE_DESCRIPTION)
            .profilePicture(UPDATED_PROFILE_PICTURE);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profileRepository.deleteAll();
        profile = createEntity();
    }

    @Test
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getProfileName()).isEqualTo(DEFAULT_PROFILE_NAME);
        assertThat(testProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfile.getTotalPosts()).isEqualTo(DEFAULT_TOTAL_POSTS);
        assertThat(testProfile.getTotalFollowers()).isEqualTo(DEFAULT_TOTAL_FOLLOWERS);
        assertThat(testProfile.getFollows()).isEqualTo(DEFAULT_FOLLOWS);
        assertThat(testProfile.getProfileDescription()).isEqualTo(DEFAULT_PROFILE_DESCRIPTION);
        assertThat(testProfile.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
    }

    @Test
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId())))
            .andExpect(jsonPath("$.[*].profileName").value(hasItem(DEFAULT_PROFILE_NAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].totalPosts").value(hasItem(DEFAULT_TOTAL_POSTS)))
            .andExpect(jsonPath("$.[*].totalFollowers").value(hasItem(DEFAULT_TOTAL_FOLLOWERS)))
            .andExpect(jsonPath("$.[*].follows").value(hasItem(DEFAULT_FOLLOWS)))
            .andExpect(jsonPath("$.[*].profileDescription").value(hasItem(DEFAULT_PROFILE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)));
    }
    
    @Test
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId()))
            .andExpect(jsonPath("$.profileName").value(DEFAULT_PROFILE_NAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.totalPosts").value(DEFAULT_TOTAL_POSTS))
            .andExpect(jsonPath("$.totalFollowers").value(DEFAULT_TOTAL_FOLLOWERS))
            .andExpect(jsonPath("$.follows").value(DEFAULT_FOLLOWS))
            .andExpect(jsonPath("$.profileDescription").value(DEFAULT_PROFILE_DESCRIPTION))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE));
    }

    @Test
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        updatedProfile
            .profileName(UPDATED_PROFILE_NAME)
            .name(UPDATED_NAME)
            .totalPosts(UPDATED_TOTAL_POSTS)
            .totalFollowers(UPDATED_TOTAL_FOLLOWERS)
            .follows(UPDATED_FOLLOWS)
            .profileDescription(UPDATED_PROFILE_DESCRIPTION)
            .profilePicture(UPDATED_PROFILE_PICTURE);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getProfileName()).isEqualTo(UPDATED_PROFILE_NAME);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getTotalPosts()).isEqualTo(UPDATED_TOTAL_POSTS);
        assertThat(testProfile.getTotalFollowers()).isEqualTo(UPDATED_TOTAL_FOLLOWERS);
        assertThat(testProfile.getFollows()).isEqualTo(UPDATED_FOLLOWS);
        assertThat(testProfile.getProfileDescription()).isEqualTo(UPDATED_PROFILE_DESCRIPTION);
        assertThat(testProfile.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
    }

    @Test
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
