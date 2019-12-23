package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Campagne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Campagne} entity.
 */
public interface CampagneSearchRepository extends ElasticsearchRepository<Campagne, Long> {
}
