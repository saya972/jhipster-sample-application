package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Campagne.
 */
@Entity
@Table(name = "campagne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "campagne")
public class Campagne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "campagne_name")
    private String campagneName;

    @Column(name = "lien_campagne")
    private String lienCampagne;

    @Column(name = "banners")
    private String banners;

    @OneToMany(mappedBy = "bannerName")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banner> banners = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("campagnes")
    private Client campagneName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampagneName() {
        return campagneName;
    }

    public Campagne campagneName(String campagneName) {
        this.campagneName = campagneName;
        return this;
    }

    public void setCampagneName(String campagneName) {
        this.campagneName = campagneName;
    }

    public String getLienCampagne() {
        return lienCampagne;
    }

    public Campagne lienCampagne(String lienCampagne) {
        this.lienCampagne = lienCampagne;
        return this;
    }

    public void setLienCampagne(String lienCampagne) {
        this.lienCampagne = lienCampagne;
    }

    public String getBanners() {
        return banners;
    }

    public Campagne banners(String banners) {
        this.banners = banners;
        return this;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public Set<Banner> getBanners() {
        return banners;
    }

    public Campagne banners(Set<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public Campagne addBanner(Banner banner) {
        this.banners.add(banner);
        banner.setBannerName(this);
        return this;
    }

    public Campagne removeBanner(Banner banner) {
        this.banners.remove(banner);
        banner.setBannerName(null);
        return this;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public Client getCampagneName() {
        return campagneName;
    }

    public Campagne campagneName(Client client) {
        this.campagneName = client;
        return this;
    }

    public void setCampagneName(Client client) {
        this.campagneName = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campagne)) {
            return false;
        }
        return id != null && id.equals(((Campagne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Campagne{" +
            "id=" + getId() +
            ", campagneName='" + getCampagneName() + "'" +
            ", lienCampagne='" + getLienCampagne() + "'" +
            ", banners='" + getBanners() + "'" +
            "}";
    }
}
