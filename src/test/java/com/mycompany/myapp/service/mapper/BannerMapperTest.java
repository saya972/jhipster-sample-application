package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BannerMapperTest {

    private BannerMapper bannerMapper;

    @BeforeEach
    public void setUp() {
        bannerMapper = new BannerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(bannerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bannerMapper.fromId(null)).isNull();
    }
}
