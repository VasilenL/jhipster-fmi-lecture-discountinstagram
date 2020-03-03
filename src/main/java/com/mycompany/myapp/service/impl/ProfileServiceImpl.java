package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProfileService;
import com.mycompany.myapp.domain.Profile;
import com.mycompany.myapp.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Save a profile.
     *
     * @param profile the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);
        return profileRepository.save(profile);
    }

    /**
     * Get all the profiles.
     *
     * @return the list of entities.
     */
    @Override
    public List<Profile> findAll() {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll();
    }

    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Profile> findOne(String id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }
}
