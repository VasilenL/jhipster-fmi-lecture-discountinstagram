package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PicturesService;
import com.mycompany.myapp.domain.Pictures;
import com.mycompany.myapp.repository.PicturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Pictures}.
 */
@Service
public class PicturesServiceImpl implements PicturesService {

    private final Logger log = LoggerFactory.getLogger(PicturesServiceImpl.class);

    private final PicturesRepository picturesRepository;

    public PicturesServiceImpl(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    /**
     * Save a pictures.
     *
     * @param pictures the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pictures save(Pictures pictures) {
        log.debug("Request to save Pictures : {}", pictures);
        return picturesRepository.save(pictures);
    }

    /**
     * Get all the pictures.
     *
     * @return the list of entities.
     */
    @Override
    public List<Pictures> findAll() {
        log.debug("Request to get all Pictures");
        return picturesRepository.findAll();
    }

    /**
     * Get one pictures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Pictures> findOne(String id) {
        log.debug("Request to get Pictures : {}", id);
        return picturesRepository.findById(id);
    }

    /**
     * Delete the pictures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Pictures : {}", id);
        picturesRepository.deleteById(id);
    }
}
