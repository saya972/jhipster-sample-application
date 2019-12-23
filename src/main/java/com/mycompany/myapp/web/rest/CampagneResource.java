package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CampagneService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CampagneDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Campagne}.
 */
@RestController
@RequestMapping("/api")
public class CampagneResource {

    private final Logger log = LoggerFactory.getLogger(CampagneResource.class);

    private static final String ENTITY_NAME = "campagne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampagneService campagneService;

    public CampagneResource(CampagneService campagneService) {
        this.campagneService = campagneService;
    }

    /**
     * {@code POST  /campagnes} : Create a new campagne.
     *
     * @param campagneDTO the campagneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campagneDTO, or with status {@code 400 (Bad Request)} if the campagne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campagnes")
    public ResponseEntity<CampagneDTO> createCampagne(@RequestBody CampagneDTO campagneDTO) throws URISyntaxException {
        log.debug("REST request to save Campagne : {}", campagneDTO);
        if (campagneDTO.getId() != null) {
            throw new BadRequestAlertException("A new campagne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampagneDTO result = campagneService.save(campagneDTO);
        return ResponseEntity.created(new URI("/api/campagnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campagnes} : Updates an existing campagne.
     *
     * @param campagneDTO the campagneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campagneDTO,
     * or with status {@code 400 (Bad Request)} if the campagneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campagneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campagnes")
    public ResponseEntity<CampagneDTO> updateCampagne(@RequestBody CampagneDTO campagneDTO) throws URISyntaxException {
        log.debug("REST request to update Campagne : {}", campagneDTO);
        if (campagneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampagneDTO result = campagneService.save(campagneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campagneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campagnes} : get all the campagnes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campagnes in body.
     */
    @GetMapping("/campagnes")
    public ResponseEntity<List<CampagneDTO>> getAllCampagnes(Pageable pageable) {
        log.debug("REST request to get a page of Campagnes");
        Page<CampagneDTO> page = campagneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campagnes/:id} : get the "id" campagne.
     *
     * @param id the id of the campagneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campagneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campagnes/{id}")
    public ResponseEntity<CampagneDTO> getCampagne(@PathVariable Long id) {
        log.debug("REST request to get Campagne : {}", id);
        Optional<CampagneDTO> campagneDTO = campagneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campagneDTO);
    }

    /**
     * {@code DELETE  /campagnes/:id} : delete the "id" campagne.
     *
     * @param id the id of the campagneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campagnes/{id}")
    public ResponseEntity<Void> deleteCampagne(@PathVariable Long id) {
        log.debug("REST request to delete Campagne : {}", id);
        campagneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/campagnes?query=:query} : search for the campagne corresponding
     * to the query.
     *
     * @param query the query of the campagne search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/campagnes")
    public ResponseEntity<List<CampagneDTO>> searchCampagnes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Campagnes for query {}", query);
        Page<CampagneDTO> page = campagneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
