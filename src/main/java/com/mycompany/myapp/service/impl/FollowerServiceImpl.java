package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.FollowerService;
import com.mycompany.myapp.domain.Follower;
import com.mycompany.myapp.repository.FollowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Follower}.
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    private final Logger log = LoggerFactory.getLogger(FollowerServiceImpl.class);

    private final FollowerRepository followerRepository;

    public FollowerServiceImpl(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    /**
     * Save a follower.
     *
     * @param follower the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Follower save(Follower follower) {
        log.debug("Request to save Follower : {}", follower);
        return followerRepository.save(follower);
    }

    /**
     * Get all the followers.
     *
     * @return the list of entities.
     */
    @Override
    public List<Follower> findAll() {
        log.debug("Request to get all Followers");
        return followerRepository.findAll();
    }

    /**
     * Get one follower by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Follower> findOne(String id) {
        log.debug("Request to get Follower : {}", id);
        return followerRepository.findById(id);
    }

    /**
     * Delete the follower by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Follower : {}", id);
        followerRepository.deleteById(id);
    }
}
