package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Follower.
 */
@Document(collection = "follower")
public class Follower implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("profile_name")
    private String profileName;

    @DBRef
    @Field("profile")
    @JsonIgnoreProperties("followers")
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public Follower profileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Profile getProfile() {
        return profile;
    }

    public Follower profile(Profile profile) {
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
        if (!(o instanceof Follower)) {
            return false;
        }
        return id != null && id.equals(((Follower) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Follower{" +
            "id=" + getId() +
            ", profileName='" + getProfileName() + "'" +
            "}";
    }
}
