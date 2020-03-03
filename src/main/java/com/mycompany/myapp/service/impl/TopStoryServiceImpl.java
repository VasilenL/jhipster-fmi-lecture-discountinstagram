package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TopStoryService;
import com.mycompany.myapp.domain.TopStory;
import com.mycompany.myapp.repository.TopStoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TopStory}.
 */
@Service
public class TopStoryServiceImpl implements TopStoryService {

    private final Logger log = LoggerFactory.getLogger(TopStoryServiceImpl.class);

    private final TopStoryRepository topStoryRepository;

    public TopStoryServiceImpl(TopStoryRepository topStoryRepository) {
        this.topStoryRepository = topStoryRepository;
    }

    /**
     * Save a topStory.
     *
     * @param topStory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TopStory save(TopStory topStory) {
        log.debug("Request to save TopStory : {}", topStory);
        return topStoryRepository.save(topStory);
    }

    /**
     * Get all the topStories.
     *
     * @return the list of entities.
     */
    @Override
    public List<TopStory> findAll() {
        log.debug("Request to get all TopStories");
        return topStoryRepository.findAll();
    }

    /**
     * Get one topStory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<TopStory> findOne(String id) {
        log.debug("Request to get TopStory : {}", id);
        return topStoryRepository.findById(id);
    }

    /**
     * Delete the topStory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete TopStory : {}", id);
        topStoryRepository.deleteById(id);
    }
}
