package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Campagne;
import com.mycompany.myapp.repository.CampagneRepository;
import com.mycompany.myapp.repository.search.CampagneSearchRepository;
import com.mycompany.myapp.service.CampagneService;
import com.mycompany.myapp.service.dto.CampagneDTO;
import com.mycompany.myapp.service.mapper.CampagneMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CampagneResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CampagneResourceIT {

    private static final String DEFAULT_CAMPAGNE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAGNE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_CAMPAGNE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_CAMPAGNE = "BBBBBBBBBB";

    private static final String DEFAULT_BANNERS = "AAAAAAAAAA";
    private static final String UPDATED_BANNERS = "BBBBBBBBBB";

    @Autowired
    private CampagneRepository campagneRepository;

    @Autowired
    private CampagneMapper campagneMapper;

    @Autowired
    private CampagneService campagneService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.CampagneSearchRepositoryMockConfiguration
     */
    @Autowired
    private CampagneSearchRepository mockCampagneSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCampagneMockMvc;

    private Campagne campagne;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampagneResource campagneResource = new CampagneResource(campagneService);
        this.restCampagneMockMvc = MockMvcBuilders.standaloneSetup(campagneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campagne createEntity(EntityManager em) {
        Campagne campagne = new Campagne()
            .campagneName(DEFAULT_CAMPAGNE_NAME)
            .lienCampagne(DEFAULT_LIEN_CAMPAGNE)
            .banners(DEFAULT_BANNERS);
        return campagne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campagne createUpdatedEntity(EntityManager em) {
        Campagne campagne = new Campagne()
            .campagneName(UPDATED_CAMPAGNE_NAME)
            .lienCampagne(UPDATED_LIEN_CAMPAGNE)
            .banners(UPDATED_BANNERS);
        return campagne;
    }

    @BeforeEach
    public void initTest() {
        campagne = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampagne() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();

        // Create the Campagne
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isCreated());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate + 1);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getCampagneName()).isEqualTo(DEFAULT_CAMPAGNE_NAME);
        assertThat(testCampagne.getLienCampagne()).isEqualTo(DEFAULT_LIEN_CAMPAGNE);
        assertThat(testCampagne.getBanners()).isEqualTo(DEFAULT_BANNERS);

        // Validate the Campagne in Elasticsearch
        verify(mockCampagneSearchRepository, times(1)).save(testCampagne);
    }

    @Test
    @Transactional
    public void createCampagneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();

        // Create the Campagne with an existing ID
        campagne.setId(1L);
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Campagne in Elasticsearch
        verify(mockCampagneSearchRepository, times(0)).save(campagne);
    }


    @Test
    @Transactional
    public void getAllCampagnes() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get all the campagneList
        restCampagneMockMvc.perform(get("/api/campagnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campagne.getId().intValue())))
            .andExpect(jsonPath("$.[*].campagneName").value(hasItem(DEFAULT_CAMPAGNE_NAME)))
            .andExpect(jsonPath("$.[*].lienCampagne").value(hasItem(DEFAULT_LIEN_CAMPAGNE)))
            .andExpect(jsonPath("$.[*].banners").value(hasItem(DEFAULT_BANNERS)));
    }
    
    @Test
    @Transactional
    public void getCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", campagne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campagne.getId().intValue()))
            .andExpect(jsonPath("$.campagneName").value(DEFAULT_CAMPAGNE_NAME))
            .andExpect(jsonPath("$.lienCampagne").value(DEFAULT_LIEN_CAMPAGNE))
            .andExpect(jsonPath("$.banners").value(DEFAULT_BANNERS));
    }

    @Test
    @Transactional
    public void getNonExistingCampagne() throws Exception {
        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // Update the campagne
        Campagne updatedCampagne = campagneRepository.findById(campagne.getId()).get();
        // Disconnect from session so that the updates on updatedCampagne are not directly saved in db
        em.detach(updatedCampagne);
        updatedCampagne
            .campagneName(UPDATED_CAMPAGNE_NAME)
            .lienCampagne(UPDATED_LIEN_CAMPAGNE)
            .banners(UPDATED_BANNERS);
        CampagneDTO campagneDTO = campagneMapper.toDto(updatedCampagne);

        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isOk());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getCampagneName()).isEqualTo(UPDATED_CAMPAGNE_NAME);
        assertThat(testCampagne.getLienCampagne()).isEqualTo(UPDATED_LIEN_CAMPAGNE);
        assertThat(testCampagne.getBanners()).isEqualTo(UPDATED_BANNERS);

        // Validate the Campagne in Elasticsearch
        verify(mockCampagneSearchRepository, times(1)).save(testCampagne);
    }

    @Test
    @Transactional
    public void updateNonExistingCampagne() throws Exception {
        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // Create the Campagne
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Campagne in Elasticsearch
        verify(mockCampagneSearchRepository, times(0)).save(campagne);
    }

    @Test
    @Transactional
    public void deleteCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeDelete = campagneRepository.findAll().size();

        // Delete the campagne
        restCampagneMockMvc.perform(delete("/api/campagnes/{id}", campagne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Campagne in Elasticsearch
        verify(mockCampagneSearchRepository, times(1)).deleteById(campagne.getId());
    }

    @Test
    @Transactional
    public void searchCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);
        when(mockCampagneSearchRepository.search(queryStringQuery("id:" + campagne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(campagne), PageRequest.of(0, 1), 1));
        // Search the campagne
        restCampagneMockMvc.perform(get("/api/_search/campagnes?query=id:" + campagne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campagne.getId().intValue())))
            .andExpect(jsonPath("$.[*].campagneName").value(hasItem(DEFAULT_CAMPAGNE_NAME)))
            .andExpect(jsonPath("$.[*].lienCampagne").value(hasItem(DEFAULT_LIEN_CAMPAGNE)))
            .andExpect(jsonPath("$.[*].banners").value(hasItem(DEFAULT_BANNERS)));
    }
}
