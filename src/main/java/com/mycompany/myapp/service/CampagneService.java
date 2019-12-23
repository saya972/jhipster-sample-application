package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CampagneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Campagne}.
 */
public interface CampagneService {

    /**
     * Save a campagne.
     *
     * @param campagneDTO the entity to save.
     * @return the persisted entity.
     */
    CampagneDTO save(CampagneDTO campagneDTO);

    /**
     * Get all the campagnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampagneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campagne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampagneDTO> findOne(Long id);

    /**
     * Delete the "id" campagne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the campagne corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampagneDTO> search(String query, Pageable pageable);
}
