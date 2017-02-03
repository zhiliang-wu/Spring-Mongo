package com.novencia.dashboard.ideabox.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.novencia.dashboard.ideabox.domain.User;

public interface UserRepository extends MongoRepository<User, Long>{

	User findByLogin(@Param("login") String login);
	
}
