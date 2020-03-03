package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TopStory;
import com.mycompany.myapp.service.TopStoryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TopStory}.
 */
@RestController
@RequestMapping("/api")
public class TopStoryResource {

    private final Logger log = LoggerFactory.getLogger(TopStoryResource.class);

    private static final String ENTITY_NAME = "topStory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopStoryService topStoryService;

    public TopStoryResource(TopStoryService topStoryService) {
        this.topStoryService = topStoryService;
    }

    /**
     * {@code POST  /top-stories} : Create a new topStory.
     *
     * @param topStory the topStory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topStory, or with status {@code 400 (Bad Request)} if the topStory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/top-stories")
    public ResponseEntity<TopStory> createTopStory(@RequestBody TopStory topStory) throws URISyntaxException {
        log.debug("REST request to save TopStory : {}", topStory);
        if (topStory.getId() != null) {
            throw new BadRequestAlertException("A new topStory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopStory result = topStoryService.save(topStory);
        return ResponseEntity.created(new URI("/api/top-stories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /top-stories} : Updates an existing topStory.
     *
     * @param topStory the topStory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topStory,
     * or with status {@code 400 (Bad Request)} if the topStory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topStory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/top-stories")
    public ResponseEntity<TopStory> updateTopStory(@RequestBody TopStory topStory) throws URISyntaxException {
        log.debug("REST request to update TopStory : {}", topStory);
        if (topStory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopStory result = topStoryService.save(topStory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topStory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /top-stories} : get all the topStories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topStories in body.
     */
    @GetMapping("/top-stories")
    public List<TopStory> getAllTopStories() {
        log.debug("REST request to get all TopStories");
        return topStoryService.findAll();
    }

    /**
     * {@code GET  /top-stories/:id} : get the "id" topStory.
     *
     * @param id the id of the topStory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topStory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/top-stories/{id}")
    public ResponseEntity<TopStory> getTopStory(@PathVariable String id) {
        log.debug("REST request to get TopStory : {}", id);
        Optional<TopStory> topStory = topStoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topStory);
    }

    /**
     * {@code DELETE  /top-stories/:id} : delete the "id" topStory.
     *
     * @param id the id of the topStory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/top-stories/{id}")
    public ResponseEntity<Void> deleteTopStory(@PathVariable String id) {
        log.debug("REST request to delete TopStory : {}", id);
        topStoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
