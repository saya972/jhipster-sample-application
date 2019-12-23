package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CampagneService;
import com.mycompany.myapp.domain.Campagne;
import com.mycompany.myapp.repository.CampagneRepository;
import com.mycompany.myapp.repository.search.CampagneSearchRepository;
import com.mycompany.myapp.service.dto.CampagneDTO;
import com.mycompany.myapp.service.mapper.CampagneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Campagne}.
 */
@Service
@Transactional
public class CampagneServiceImpl implements CampagneService {

    private final Logger log = LoggerFactory.getLogger(CampagneServiceImpl.class);

    private final CampagneRepository campagneRepository;

    private final CampagneMapper campagneMapper;

    private final CampagneSearchRepository campagneSearchRepository;

    public CampagneServiceImpl(CampagneRepository campagneRepository, CampagneMapper campagneMapper, CampagneSearchRepository campagneSearchRepository) {
        this.campagneRepository = campagneRepository;
        this.campagneMapper = campagneMapper;
        this.campagneSearchRepository = campagneSearchRepository;
    }

    /**
     * Save a campagne.
     *
     * @param campagneDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CampagneDTO save(CampagneDTO campagneDTO) {
        log.debug("Request to save Campagne : {}", campagneDTO);
        Campagne campagne = campagneMapper.toEntity(campagneDTO);
        campagne = campagneRepository.save(campagne);
        CampagneDTO result = campagneMapper.toDto(campagne);
        campagneSearchRepository.save(campagne);
        return result;
    }

    /**
     * Get all the campagnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CampagneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campagnes");
        return campagneRepository.findAll(pageable)
            .map(campagneMapper::toDto);
    }


    /**
     * Get one campagne by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampagneDTO> findOne(Long id) {
        log.debug("Request to get Campagne : {}", id);
        return campagneRepository.findById(id)
            .map(campagneMapper::toDto);
    }

    /**
     * Delete the campagne by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campagne : {}", id);
        campagneRepository.deleteById(id);
        campagneSearchRepository.deleteById(id);
    }

    /**
     * Search for the campagne corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CampagneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Campagnes for query {}", query);
        return campagneSearchRepository.search(queryStringQuery(query), pageable)
            .map(campagneMapper::toDto);
    }
}
