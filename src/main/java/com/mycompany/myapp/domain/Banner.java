package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Banner.
 */
@Entity
@Table(name = "banner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "banner_name")
    private String bannerName;

    @Column(name = "banner_size")
    private String bannerSize;

    @ManyToOne
    @JsonIgnoreProperties("banners")
    private Campagne bannerName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public Banner bannerName(String bannerName) {
        this.bannerName = bannerName;
        return this;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerSize() {
        return bannerSize;
    }

    public Banner bannerSize(String bannerSize) {
        this.bannerSize = bannerSize;
        return this;
    }

    public void setBannerSize(String bannerSize) {
        this.bannerSize = bannerSize;
    }

    public Campagne getBannerName() {
        return bannerName;
    }

    public Banner bannerName(Campagne campagne) {
        this.bannerName = campagne;
        return this;
    }

    public void setBannerName(Campagne campagne) {
        this.bannerName = campagne;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banner)) {
            return false;
        }
        return id != null && id.equals(((Banner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banner{" +
            "id=" + getId() +
            ", bannerName='" + getBannerName() + "'" +
            ", bannerSize='" + getBannerSize() + "'" +
            "}";
    }
}
