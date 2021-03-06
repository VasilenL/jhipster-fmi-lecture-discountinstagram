package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pictures;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Pictures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PicturesRepository extends MongoRepository<Pictures, String> {

}
