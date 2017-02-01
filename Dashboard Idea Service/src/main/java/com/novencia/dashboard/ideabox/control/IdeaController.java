package com.novencia.dashboard.ideabox.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.novencia.dashboard.ideabox.CustomErrorType;
import com.novencia.dashboard.ideabox.model.Idea;
import com.novencia.dashboard.ideabox.model.Idea;
import com.novencia.dashboard.ideabox.model.Idea;
import com.novencia.dashboard.ideabox.repository.IdeaRepository;

@RestController
@RequestMapping("/ideas")
public class IdeaController {

	public static final Logger logger = LoggerFactory.getLogger(IdeaController.class);

	@Autowired
	private IdeaRepository repository;
	
    // -------------------Retrieve All---------------------------------------------    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Idea>> listAllIdeas() {
        List<Idea> ideas = repository.findAll();
        if (ideas.isEmpty()) {
            return new ResponseEntity<List<Idea>>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Idea>>(ideas, HttpStatus.OK);
    }
	
    // -------------------Retrieve Single------------------------------------------    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getIdea(@PathVariable("id") long id) {
        logger.info("Fetching Idea with id {}", id);
        Idea idea = repository.findOne(id);
        if (idea == null) {
            logger.error("Idea with id {} not found.", id);
            return new ResponseEntity<Object>(new CustomErrorType("Idea with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Idea>(idea, HttpStatus.OK);
    }
 
    // -------------------Create a Idea-------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createIdea(@RequestBody Idea idea, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Idea : {}", idea);
 
        /*
        if (ideaService.isIdeaExist(idea)) {
            logger.error("Unable to create. A Idea with name {} already exist", idea.getFirstName()+"."+idea.getLastName());
            return new ResponseEntity<Object>(new CustomErrorType("Unable to create. A Idea with name "),HttpStatus.CONFLICT);
        }
        ideaService.saveIdea(idea);
 		*/
        repository.save(idea);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/idea/{id}").buildAndExpand(idea.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
}
