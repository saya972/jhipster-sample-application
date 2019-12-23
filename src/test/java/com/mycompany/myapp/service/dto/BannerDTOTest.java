package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BannerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannerDTO.class);
        BannerDTO bannerDTO1 = new BannerDTO();
        bannerDTO1.setId(1L);
        BannerDTO bannerDTO2 = new BannerDTO();
        assertThat(bannerDTO1).isNotEqualTo(bannerDTO2);
        bannerDTO2.setId(bannerDTO1.getId());
        assertThat(bannerDTO1).isEqualTo(bannerDTO2);
        bannerDTO2.setId(2L);
        assertThat(bannerDTO1).isNotEqualTo(bannerDTO2);
        bannerDTO1.setId(null);
        assertThat(bannerDTO1).isNotEqualTo(bannerDTO2);
    }
}
