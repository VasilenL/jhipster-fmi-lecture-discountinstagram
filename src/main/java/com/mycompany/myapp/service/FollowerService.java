package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Follower;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Follower}.
 */
public interface FollowerService {

    /**
     * Save a follower.
     *
     * @param follower the entity to save.
     * @return the persisted entity.
     */
    Follower save(Follower follower);

    /**
     * Get all the followers.
     *
     * @return the list of entities.
     */
    List<Follower> findAll();

    /**
     * Get the "id" follower.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Follower> findOne(String id);

    /**
     * Delete the "id" follower.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
