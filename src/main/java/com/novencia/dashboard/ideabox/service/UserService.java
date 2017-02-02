package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.exception.SequenceException;

public interface UserService {
     
    User findById(long id);
    
    void createUser(User user) throws SequenceException;
     
    void updateUser(long userId, User user) throws Exception;
     
    void deleteUserById(long id);
 
    List<User> findAllUsers();
     
    void deleteAllUsers();
     
    boolean isUserExist(long userId);
    
    List<User> search(String key);
     
}