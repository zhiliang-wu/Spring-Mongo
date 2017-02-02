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

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.repository.CommentRepository;
import com.novencia.dashboard.ideabox.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private CommentService commentService;
	
   
    /**
     * Retrieve all comments
     * @return
     */
	@GetMapping
    public ResponseEntity<List<Comment>> listAllComments() {
        List<Comment> comments = repository.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
	
   /**
    * Retrieve one comment 
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable("id") long id) {
        logger.info("Fetching Comment with id {}", id);
        Comment comment = repository.findOne(id);
        if (comment == null) {
            logger.error("Comment with id {} not found.", id);
            return new ResponseEntity<>(("Comment with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }
 
    /**
     * Create one comment
     * @param comment
     * @param ucBuilder
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Comment : {}", comment);
 
        try {
			commentService.createComment(comment);
		} catch (Exception e) {
			logger.error("Unable to create comment." + e.getMessage());
			return new ResponseEntity<Object>(("Unable to create comment." + e.getMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/comment/{id}").buildAndExpand(comment.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
  
    /**
     * Update one comment
     * @param id
     * @param comment
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") long id, @RequestBody Comment comment){
		logger.info("Updating Comment:{}", comment);
		
		try {
			commentService.updateComment(comment);
		} catch (Exception e) {
			logger.error("Unable to update comment." + e.getMessage());
			return new ResponseEntity<Object>(("Unable to update comment." + e.getMessage()),
					HttpStatus.CONFLICT);
		}
    	
		return new ResponseEntity<Comment>(comment,HttpStatus.OK);
    }
    
    
    
    /**
     * Delete one comment
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
    	logger.info("Delete comment id:{}",id);
    	Comment comment = repository.findOne(id);
    	if(comment != null){
    		repository.delete(comment);
    	}
    	return new ResponseEntity<Comment>(HttpStatus.OK);	
    }
    
    /**
     * Delete all comments
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Comment> deleteComments() {
    	logger.info("Delete All comments");
    	repository.deleteAll();   	
    	return new ResponseEntity<Comment>(HttpStatus.OK);	
    }
    
  
}
