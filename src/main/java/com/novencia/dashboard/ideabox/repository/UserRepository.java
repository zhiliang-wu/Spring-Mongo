package com.novencia.dashboard.ideabox.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.novencia.dashboard.ideabox.domain.User;

public interface UserRepository extends MongoRepository<User, Long>{

}
