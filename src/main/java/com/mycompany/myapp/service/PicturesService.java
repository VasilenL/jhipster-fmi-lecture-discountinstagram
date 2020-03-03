package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Pictures;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pictures}.
 */
public interface PicturesService {

    /**
     * Save a pictures.
     *
     * @param pictures the entity to save.
     * @return the persisted entity.
     */
    Pictures save(Pictures pictures);

    /**
     * Get all the pictures.
     *
     * @return the list of entities.
     */
    List<Pictures> findAll();

    /**
     * Get the "id" pictures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pictures> findOne(String id);

    /**
     * Delete the "id" pictures.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
