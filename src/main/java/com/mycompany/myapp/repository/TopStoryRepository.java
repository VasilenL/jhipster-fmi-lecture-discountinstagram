package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TopStory;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TopStory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopStoryRepository extends MongoRepository<TopStory, String> {

}
