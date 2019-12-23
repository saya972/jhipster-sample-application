package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Banner;
import com.mycompany.myapp.repository.BannerRepository;
import com.mycompany.myapp.repository.search.BannerSearchRepository;
import com.mycompany.myapp.service.BannerService;
import com.mycompany.myapp.service.dto.BannerDTO;
import com.mycompany.myapp.service.mapper.BannerMapper;
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
 * Integration tests for the {@link BannerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BannerResourceIT {

    private static final String DEFAULT_BANNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_SIZE = "BBBBBBBBBB";

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BannerService bannerService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.BannerSearchRepositoryMockConfiguration
     */
    @Autowired
    private BannerSearchRepository mockBannerSearchRepository;

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

    private MockMvc restBannerMockMvc;

    private Banner banner;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BannerResource bannerResource = new BannerResource(bannerService);
        this.restBannerMockMvc = MockMvcBuilders.standaloneSetup(bannerResource)
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
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner()
            .bannerName(DEFAULT_BANNER_NAME)
            .bannerSize(DEFAULT_BANNER_SIZE);
        return banner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banner createUpdatedEntity(EntityManager em) {
        Banner banner = new Banner()
            .bannerName(UPDATED_BANNER_NAME)
            .bannerSize(UPDATED_BANNER_SIZE);
        return banner;
    }

    @BeforeEach
    public void initTest() {
        banner = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getBannerName()).isEqualTo(DEFAULT_BANNER_NAME);
        assertThat(testBanner.getBannerSize()).isEqualTo(DEFAULT_BANNER_SIZE);

        // Validate the Banner in Elasticsearch
        verify(mockBannerSearchRepository, times(1)).save(testBanner);
    }

    @Test
    @Transactional
    public void createBannerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner with an existing ID
        banner.setId(1L);
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Banner in Elasticsearch
        verify(mockBannerSearchRepository, times(0)).save(banner);
    }


    @Test
    @Transactional
    public void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc.perform(get("/api/banners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME)))
            .andExpect(jsonPath("$.[*].bannerSize").value(hasItem(DEFAULT_BANNER_SIZE)));
    }
    
    @Test
    @Transactional
    public void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
            .andExpect(jsonPath("$.bannerName").value(DEFAULT_BANNER_NAME))
            .andExpect(jsonPath("$.bannerSize").value(DEFAULT_BANNER_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findById(banner.getId()).get();
        // Disconnect from session so that the updates on updatedBanner are not directly saved in db
        em.detach(updatedBanner);
        updatedBanner
            .bannerName(UPDATED_BANNER_NAME)
            .bannerSize(UPDATED_BANNER_SIZE);
        BannerDTO bannerDTO = bannerMapper.toDto(updatedBanner);

        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getBannerName()).isEqualTo(UPDATED_BANNER_NAME);
        assertThat(testBanner.getBannerSize()).isEqualTo(UPDATED_BANNER_SIZE);

        // Validate the Banner in Elasticsearch
        verify(mockBannerSearchRepository, times(1)).save(testBanner);
    }

    @Test
    @Transactional
    public void updateNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Create the Banner
        BannerDTO bannerDTO = bannerMapper.toDto(banner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bannerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Banner in Elasticsearch
        verify(mockBannerSearchRepository, times(0)).save(banner);
    }

    @Test
    @Transactional
    public void deleteBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Delete the banner
        restBannerMockMvc.perform(delete("/api/banners/{id}", banner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Banner in Elasticsearch
        verify(mockBannerSearchRepository, times(1)).deleteById(banner.getId());
    }

    @Test
    @Transactional
    public void searchBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);
        when(mockBannerSearchRepository.search(queryStringQuery("id:" + banner.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(banner), PageRequest.of(0, 1), 1));
        // Search the banner
        restBannerMockMvc.perform(get("/api/_search/banners?query=id:" + banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
            .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME)))
            .andExpect(jsonPath("$.[*].bannerSize").value(hasItem(DEFAULT_BANNER_SIZE)));
    }
}
