package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profile.
 */
@Document(collection = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("profile_name")
    private String profileName;

    @Field("name")
    private String name;

    @Field("total_posts")
    private Integer totalPosts;

    @Field("total_followers")
    private Integer totalFollowers;

    @Field("follows")
    private Integer follows;

    @Field("profile_description")
    private String profileDescription;

    @Field("profile_picture")
    private String profilePicture;

    @DBRef
    @Field("topStories")
    private Set<TopStory> topStories = new HashSet<>();

    @DBRef
    @Field("followers")
    private Set<Follower> followers = new HashSet<>();

    @DBRef
    @Field("pictures")
    private Set<Pictures> pictures = new HashSet<>();

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

    public Profile profileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getName() {
        return name;
    }

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalPosts() {
        return totalPosts;
    }

    public Profile totalPosts(Integer totalPosts) {
        this.totalPosts = totalPosts;
        return this;
    }

    public void setTotalPosts(Integer totalPosts) {
        this.totalPosts = totalPosts;
    }

    public Integer getTotalFollowers() {
        return totalFollowers;
    }

    public Profile totalFollowers(Integer totalFollowers) {
        this.totalFollowers = totalFollowers;
        return this;
    }

    public void setTotalFollowers(Integer totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public Integer getFollows() {
        return follows;
    }

    public Profile follows(Integer follows) {
        this.follows = follows;
        return this;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public Profile profileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
        return this;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Profile profilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<TopStory> getTopStories() {
        return topStories;
    }

    public Profile topStories(Set<TopStory> topStories) {
        this.topStories = topStories;
        return this;
    }

    public Profile addTopStories(TopStory topStory) {
        this.topStories.add(topStory);
        topStory.setProfile(this);
        return this;
    }

    public Profile removeTopStories(TopStory topStory) {
        this.topStories.remove(topStory);
        topStory.setProfile(null);
        return this;
    }

    public void setTopStories(Set<TopStory> topStories) {
        this.topStories = topStories;
    }

    public Set<Follower> getFollowers() {
        return followers;
    }

    public Profile followers(Set<Follower> followers) {
        this.followers = followers;
        return this;
    }

    public Profile addFollowers(Follower follower) {
        this.followers.add(follower);
        follower.setProfile(this);
        return this;
    }

    public Profile removeFollowers(Follower follower) {
        this.followers.remove(follower);
        follower.setProfile(null);
        return this;
    }

    public void setFollowers(Set<Follower> followers) {
        this.followers = followers;
    }

    public Set<Pictures> getPictures() {
        return pictures;
    }

    public Profile pictures(Set<Pictures> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Profile addPictures(Pictures pictures) {
        this.pictures.add(pictures);
        pictures.setProfile(this);
        return this;
    }

    public Profile removePictures(Pictures pictures) {
        this.pictures.remove(pictures);
        pictures.setProfile(null);
        return this;
    }

    public void setPictures(Set<Pictures> pictures) {
        this.pictures = pictures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", profileName='" + getProfileName() + "'" +
            ", name='" + getName() + "'" +
            ", totalPosts=" + getTotalPosts() +
            ", totalFollowers=" + getTotalFollowers() +
            ", follows=" + getFollows() +
            ", profileDescription='" + getProfileDescription() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            "}";
    }
}
