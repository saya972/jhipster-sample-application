package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CampagneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampagneDTO.class);
        CampagneDTO campagneDTO1 = new CampagneDTO();
        campagneDTO1.setId(1L);
        CampagneDTO campagneDTO2 = new CampagneDTO();
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
        campagneDTO2.setId(campagneDTO1.getId());
        assertThat(campagneDTO1).isEqualTo(campagneDTO2);
        campagneDTO2.setId(2L);
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
        campagneDTO1.setId(null);
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
    }
}
