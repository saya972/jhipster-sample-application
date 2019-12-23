package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Banner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Banner} entity.
 */
public interface BannerSearchRepository extends ElasticsearchRepository<Banner, Long> {
}
