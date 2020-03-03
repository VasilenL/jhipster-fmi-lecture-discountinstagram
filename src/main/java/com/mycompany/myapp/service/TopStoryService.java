package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TopStory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TopStory}.
 */
public interface TopStoryService {

    /**
     * Save a topStory.
     *
     * @param topStory the entity to save.
     * @return the persisted entity.
     */
    TopStory save(TopStory topStory);

    /**
     * Get all the topStories.
     *
     * @return the list of entities.
     */
    List<TopStory> findAll();

    /**
     * Get the "id" topStory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopStory> findOne(String id);

    /**
     * Delete the "id" topStory.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
