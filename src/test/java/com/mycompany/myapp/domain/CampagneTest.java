package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CampagneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campagne.class);
        Campagne campagne1 = new Campagne();
        campagne1.setId(1L);
        Campagne campagne2 = new Campagne();
        campagne2.setId(campagne1.getId());
        assertThat(campagne1).isEqualTo(campagne2);
        campagne2.setId(2L);
        assertThat(campagne1).isNotEqualTo(campagne2);
        campagne1.setId(null);
        assertThat(campagne1).isNotEqualTo(campagne2);
    }
}
