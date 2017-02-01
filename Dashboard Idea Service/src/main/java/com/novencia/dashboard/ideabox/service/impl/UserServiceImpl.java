package com.novencia.dashboard.ideabox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.repository.UserRepository;
import com.novencia.dashboard.ideabox.service.SequenceService;
import com.novencia.dashboard.ideabox.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
       
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private SequenceService sequenceService;
 
    public List<User> findAllUsers() {
    	return repository.findAll();
    }
     
    public User findById(long id) {      
        return repository.findOne(id);
    }

    public void createUser(User user) throws SequenceException {
        user.setId(sequenceService.getNextSequenceId("user"));
        repository.save(user);
    }
 
    public void updateUser(long userId, User user) throws Exception{
        if(!isUserExist(userId)){
        	throw new Exception("User with id "+user.getId()+" not exist");
        }
        user.setId(userId);
    	repository.save(user);
    }
 
    public void deleteUserById(long id) {    
       repository.delete(id);
    }
 
    public boolean isUserExist(long userId) {
        return findById(userId)!=null;
    }
     
    public void deleteAllUsers() {
        repository.deleteAll();
    }
    
	public List<User> search(String key) {
		return mongoTemplate.find(Query.query(new Criteria().orOperator(
				Criteria.where("login").regex(key, "i"),
				Criteria.where("firstName").regex(key, "i"),
				Criteria.where("lastName").regex(key, "i"))
				), User.class);
	}

}