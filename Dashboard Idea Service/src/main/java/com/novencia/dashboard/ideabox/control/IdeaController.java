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

import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.json.IdeaJson;
import com.novencia.dashboard.ideabox.repository.IdeaRepository;
import com.novencia.dashboard.ideabox.service.IdeaService;

@RestController
@RequestMapping("/ideas")
public class IdeaController {

	public static final Logger logger = LoggerFactory.getLogger(IdeaController.class);

	@Autowired
	private IdeaRepository repository;
	
	@Autowired
	private IdeaService ideaService;
	
   
    /**
     * Retrieve all ideas
     * @return
     */
	@GetMapping
    public ResponseEntity<List<Idea>> listAllIdeas() {
        List<Idea> ideas = repository.findAll();
        if (ideas.isEmpty()) {
            return new ResponseEntity<List<Idea>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Idea>>(ideas, HttpStatus.OK);
    }
	
   /**
    * Retrieve one idea by ideaId
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    public ResponseEntity<?> getIdea(@PathVariable("id") long id) {
        logger.info("Fetching Idea with id {}", id);
        IdeaJson idea = ideaService.findOne(id);
        if (idea == null) {
            logger.error("Idea with id {} not found.", id);
            return new ResponseEntity<>(("Idea with id " + id  + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<IdeaJson>(idea, HttpStatus.OK);
    }
 
    /**
     * Retrieve all ideas by userId
     * @param useId
     * @return
     */
     @GetMapping("/{userId}")
     public ResponseEntity<List<Idea>> getIdeas(@PathVariable("userId") long userId) {
         logger.info("Fetching Idea with userId {}", userId);
         List<Idea> ideas = ideaService.findByUserId(userId);
         if (ideas.isEmpty()) {
             return new ResponseEntity<List<Idea>>(HttpStatus.NO_CONTENT);
         }
         return new ResponseEntity<List<Idea>>(ideas, HttpStatus.OK);
     }
     
    /**
     * Create one idea
     * @param idea
     * @param ucBuilder
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createIdea(@RequestBody Idea idea, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Idea : {}", idea);
		
        try {
			ideaService.createIdea(idea);
		} catch (Exception e) {
			logger.error("Unable to create idea." + e.getMessage());
			return new ResponseEntity<Object>(("Unable to create idea." + e.getMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/idea/{id}").buildAndExpand(idea.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
  
    /**
     * Update one idea
     * @param id
     * @param idea
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIdea(@PathVariable("id") long id, @RequestBody Idea idea){
		logger.info("Updating Idea:{}", idea);
		
		try {
			ideaService.updateIdea(idea);
		} catch (Exception e) {
			logger.error("Unable to update idea." + e.getMessage());
			return new ResponseEntity<Object>(("Unable to update idea." + e.getMessage()),HttpStatus.CONFLICT);
		}
    	
		return new ResponseEntity<Idea>(idea,HttpStatus.OK);
    }
    
    
    
    /**
     * Delete one idea
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Idea> deleteIdea(@PathVariable("id") long id) {
    	logger.info("Delete idea id:{}",id);
    	Idea idea = repository.findOne(id);
    	if(idea != null){
    		repository.delete(idea);
    	}
    	return new ResponseEntity<Idea>(HttpStatus.OK);	
    }
    
    /**
     * Delete all ideas
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Idea> deleteIdeas() {
    	logger.info("Delete All ideas");
    	repository.deleteAll();   	
    	return new ResponseEntity<Idea>(HttpStatus.OK);	
    }
    
    /**
     * Like an idea
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public ResponseEntity<Idea> likeIdea(@PathVariable("id") long id){
    	logger.info("Like idea id:{}",id);
    	ideaService.likeIdea(id);
    	return new ResponseEntity<Idea>(HttpStatus.OK);	
    }
    
    /**
     * Dislike an idea
     * @param id
     * @return
     */
    @PutMapping("/dislike/{id}")
    public ResponseEntity<Idea> dislikeIdea(@PathVariable("id") long id){
    	logger.info("Dislike idea id:{}",id);
    	ideaService.dislikeIdea(id);
    	return new ResponseEntity<Idea>(HttpStatus.OK);	
    }
    
}
