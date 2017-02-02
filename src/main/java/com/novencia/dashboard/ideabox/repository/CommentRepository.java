package com.novencia.dashboard.ideabox.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.novencia.dashboard.ideabox.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

	List<Comment> findByUserId(@Param("userId") long userId);
	
	List<Comment> findByIdeaId(@Param("ideaId") long ideaId);
	
}
