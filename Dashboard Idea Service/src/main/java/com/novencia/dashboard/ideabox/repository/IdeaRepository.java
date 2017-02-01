package com.novencia.dashboard.ideabox.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.novencia.dashboard.ideabox.model.Idea;

@RepositoryRestResource(collectionResourceRel = "idea", path = "idea")
public interface IdeaRepository extends MongoRepository<Idea, String> {

	List<Idea> findByTitle(@Param("title") String title);

}