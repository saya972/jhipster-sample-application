package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CampagneMapperTest {

    private CampagneMapper campagneMapper;

    @BeforeEach
    public void setUp() {
        campagneMapper = new CampagneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(campagneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(campagneMapper.fromId(null)).isNull();
    }
}
