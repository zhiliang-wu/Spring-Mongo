package main.java.com.novencia.dashboard.idea.service;

import java.util.List;

import main.java.com.novencia.dashboard.idea.model.User;

public interface UserService {
     
    User findById(long id);
    
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserById(long id);
 
    List<User> findAllUsers();
     
    void deleteAllUsers();
     
    boolean isUserExist(User user);
     
}