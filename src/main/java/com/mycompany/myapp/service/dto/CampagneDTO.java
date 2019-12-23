package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Campagne} entity.
 */
public class CampagneDTO implements Serializable {

    private Long id;

    private String campagneName;

    private String lienCampagne;

    private String banners;


    private Long campagneNameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampagneName() {
        return campagneName;
    }

    public void setCampagneName(String campagneName) {
        this.campagneName = campagneName;
    }

    public String getLienCampagne() {
        return lienCampagne;
    }

    public void setLienCampagne(String lienCampagne) {
        this.lienCampagne = lienCampagne;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public Long getCampagneNameId() {
        return campagneNameId;
    }

    public void setCampagneNameId(Long clientId) {
        this.campagneNameId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CampagneDTO campagneDTO = (CampagneDTO) o;
        if (campagneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campagneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampagneDTO{" +
            "id=" + getId() +
            ", campagneName='" + getCampagneName() + "'" +
            ", lienCampagne='" + getLienCampagne() + "'" +
            ", banners='" + getBanners() + "'" +
            ", campagneNameId=" + getCampagneNameId() +
            "}";
    }
}
