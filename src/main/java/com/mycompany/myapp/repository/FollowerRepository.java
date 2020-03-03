package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Follower;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Follower entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowerRepository extends MongoRepository<Follower, String> {

}
