package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Banner} entity.
 */
public class BannerDTO implements Serializable {

    private Long id;

    private String bannerName;

    private String bannerSize;


    private Long bannerNameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerSize() {
        return bannerSize;
    }

    public void setBannerSize(String bannerSize) {
        this.bannerSize = bannerSize;
    }

    public Long getBannerNameId() {
        return bannerNameId;
    }

    public void setBannerNameId(Long campagneId) {
        this.bannerNameId = campagneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BannerDTO bannerDTO = (BannerDTO) o;
        if (bannerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bannerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BannerDTO{" +
            "id=" + getId() +
            ", bannerName='" + getBannerName() + "'" +
            ", bannerSize='" + getBannerSize() + "'" +
            ", bannerNameId=" + getBannerNameId() +
            "}";
    }
}
