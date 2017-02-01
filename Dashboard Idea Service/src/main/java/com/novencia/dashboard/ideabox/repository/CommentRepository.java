package com.novencia.dashboard.ideabox.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.novencia.dashboard.ideabox.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

}
