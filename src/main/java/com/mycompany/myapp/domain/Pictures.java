package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Pictures.
 */
@Document(collection = "pictures")
public class Pictures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("full_title")
    private String fullTitle;

    @Field("description")
    private String description;

    @DBRef
    @Field("profile")
    @JsonIgnoreProperties("pictures")
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pictures name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public Pictures fullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
        return this;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getDescription() {
        return description;
    }

    public Pictures description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Profile getProfile() {
        return profile;
    }

    public Pictures profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pictures)) {
            return false;
        }
        return id != null && id.equals(((Pictures) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pictures{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fullTitle='" + getFullTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
