package com.novencia.dashboard.ideabox.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 
    @Autowired
    UserService userService;

       
    /**
     * Get all users
     * @return
     */
    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
    	logger.info("Fetching all users");
    	List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
        	return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
 
    /**
     * Get one user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<>(("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
 
  
    /**
     * Create one user
     * @param user
     * @param ucBuilder
     * @return
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
		userService.createUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    
    /**
     * Update one user 
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);      
		userService.updateUser(id,user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
 
    /**
     * Delete one user by id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);
        userService.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.OK);
    }
 
    /**
     * Delete all users
     * @return
     */
    @DeleteMapping
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.OK);
    }
 
    
}
