package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Follower;
import com.mycompany.myapp.service.FollowerService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Follower}.
 */
@RestController
@RequestMapping("/api")
public class FollowerResource {

    private final Logger log = LoggerFactory.getLogger(FollowerResource.class);

    private static final String ENTITY_NAME = "follower";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowerService followerService;

    public FollowerResource(FollowerService followerService) {
        this.followerService = followerService;
    }

    /**
     * {@code POST  /followers} : Create a new follower.
     *
     * @param follower the follower to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new follower, or with status {@code 400 (Bad Request)} if the follower has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/followers")
    public ResponseEntity<Follower> createFollower(@RequestBody Follower follower) throws URISyntaxException {
        log.debug("REST request to save Follower : {}", follower);
        if (follower.getId() != null) {
            throw new BadRequestAlertException("A new follower cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Follower result = followerService.save(follower);
        return ResponseEntity.created(new URI("/api/followers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /followers} : Updates an existing follower.
     *
     * @param follower the follower to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated follower,
     * or with status {@code 400 (Bad Request)} if the follower is not valid,
     * or with status {@code 500 (Internal Server Error)} if the follower couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/followers")
    public ResponseEntity<Follower> updateFollower(@RequestBody Follower follower) throws URISyntaxException {
        log.debug("REST request to update Follower : {}", follower);
        if (follower.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Follower result = followerService.save(follower);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, follower.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /followers} : get all the followers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of followers in body.
     */
    @GetMapping("/followers")
    public List<Follower> getAllFollowers() {
        log.debug("REST request to get all Followers");
        return followerService.findAll();
    }

    /**
     * {@code GET  /followers/:id} : get the "id" follower.
     *
     * @param id the id of the follower to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the follower, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/followers/{id}")
    public ResponseEntity<Follower> getFollower(@PathVariable String id) {
        log.debug("REST request to get Follower : {}", id);
        Optional<Follower> follower = followerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(follower);
    }

    /**
     * {@code DELETE  /followers/:id} : delete the "id" follower.
     *
     * @param id the id of the follower to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/followers/{id}")
    public ResponseEntity<Void> deleteFollower(@PathVariable String id) {
        log.debug("REST request to delete Follower : {}", id);
        followerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
