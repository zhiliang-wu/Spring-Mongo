package com.novencia.dashboard.ideabox.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.novencia.dashboard.ideabox.domain.Idea;

public interface IdeaRepository extends MongoRepository<Idea, Long> {

	List<Idea> findByText(@Param("text") String text);
	
	List<Idea> findByUserId(@Param("userId") long userId);

}