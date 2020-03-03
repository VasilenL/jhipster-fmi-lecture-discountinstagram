package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Pictures;
import com.mycompany.myapp.service.PicturesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Pictures}.
 */
@RestController
@RequestMapping("/api")
public class PicturesResource {

    private final Logger log = LoggerFactory.getLogger(PicturesResource.class);

    private static final String ENTITY_NAME = "pictures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PicturesService picturesService;

    public PicturesResource(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    /**
     * {@code POST  /pictures} : Create a new pictures.
     *
     * @param pictures the pictures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pictures, or with status {@code 400 (Bad Request)} if the pictures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pictures")
    public ResponseEntity<Pictures> createPictures(@RequestBody Pictures pictures) throws URISyntaxException {
        log.debug("REST request to save Pictures : {}", pictures);
        if (pictures.getId() != null) {
            throw new BadRequestAlertException("A new pictures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pictures result = picturesService.save(pictures);
        return ResponseEntity.created(new URI("/api/pictures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pictures} : Updates an existing pictures.
     *
     * @param pictures the pictures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pictures,
     * or with status {@code 400 (Bad Request)} if the pictures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pictures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pictures")
    public ResponseEntity<Pictures> updatePictures(@RequestBody Pictures pictures) throws URISyntaxException {
        log.debug("REST request to update Pictures : {}", pictures);
        if (pictures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pictures result = picturesService.save(pictures);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pictures.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pictures} : get all the pictures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pictures in body.
     */
    @GetMapping("/pictures")
    public List<Pictures> getAllPictures() {
        log.debug("REST request to get all Pictures");
        return picturesService.findAll();
    }

    /**
     * {@code GET  /pictures/:id} : get the "id" pictures.
     *
     * @param id the id of the pictures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pictures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pictures/{id}")
    public ResponseEntity<Pictures> getPictures(@PathVariable String id) {
        log.debug("REST request to get Pictures : {}", id);
        Optional<Pictures> pictures = picturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pictures);
    }

    /**
     * {@code DELETE  /pictures/:id} : delete the "id" pictures.
     *
     * @param id the id of the pictures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pictures/{id}")
    public ResponseEntity<Void> deletePictures(@PathVariable String id) {
        log.debug("REST request to delete Pictures : {}", id);
        picturesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
