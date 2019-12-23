package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.Languages;

import com.mycompany.myapp.domain.enumeration.Prenium;

/**
 * A Client.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "langue_client")
    private Languages langueClient;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_client")
    private Prenium statutClient;

    @OneToMany(mappedBy = "campagneName")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Campagne> campagnes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Client lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Client phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public Client addressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public Client city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Client country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Client createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Languages getLangueClient() {
        return langueClient;
    }

    public Client langueClient(Languages langueClient) {
        this.langueClient = langueClient;
        return this;
    }

    public void setLangueClient(Languages langueClient) {
        this.langueClient = langueClient;
    }

    public Prenium getStatutClient() {
        return statutClient;
    }

    public Client statutClient(Prenium statutClient) {
        this.statutClient = statutClient;
        return this;
    }

    public void setStatutClient(Prenium statutClient) {
        this.statutClient = statutClient;
    }

    public Set<Campagne> getCampagnes() {
        return campagnes;
    }

    public Client campagnes(Set<Campagne> campagnes) {
        this.campagnes = campagnes;
        return this;
    }

    public Client addCampagnes(Campagne campagne) {
        this.campagnes.add(campagne);
        campagne.setCampagneName(this);
        return this;
    }

    public Client removeCampagnes(Campagne campagne) {
        this.campagnes.remove(campagne);
        campagne.setCampagneName(null);
        return this;
    }

    public void setCampagnes(Set<Campagne> campagnes) {
        this.campagnes = campagnes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", addressLine='" + getAddressLine() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", langueClient='" + getLangueClient() + "'" +
            ", statutClient='" + getStatutClient() + "'" +
            "}";
    }
}
