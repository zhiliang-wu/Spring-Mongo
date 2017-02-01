package com.novencia.dashboard.ideabox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.json.IdeaJson;
import com.novencia.dashboard.ideabox.repository.CommentRepository;
import com.novencia.dashboard.ideabox.repository.IdeaRepository;
import com.novencia.dashboard.ideabox.service.IdeaService;
import com.novencia.dashboard.ideabox.service.SequenceService;
import com.novencia.dashboard.ideabox.service.UserService;

@Service("ideaService")
public class IdeaServiceImpl implements IdeaService {

	@Autowired
    private IdeaRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
 
    @Autowired
    private SequenceService sequenceService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CommentRepository commentRepository;
    
	@Override
	public List<Idea> search(String key) {
		return mongoTemplate.find(Query.query(new Criteria().orOperator(
				Criteria.where("text").regex(key, "i"))
				), Idea.class);
	}

	@Override
	public void createIdea(Idea idea) throws SequenceException,Exception {
		idea.setId(sequenceService.getNextSequenceId("idea"));
		if(!userService.isUserExist(idea.getUserId())){
			throw new Exception("User with id "+idea.getUserId()+" not exist");
		}		
		repository.save(idea);
	}

	@Override
	public void updateIdea(Idea idea) throws Exception {		
		
		if(!isIdeaExist(idea.getId())){
        	throw new Exception("Idea with id "+idea.getId()+" not exist");
        }
		if(!userService.isUserExist(idea.getUserId())){
			throw new Exception("User with id "+idea.getUserId()+" not exist");
		}	
		repository.save(idea);		
	}
	
	@Override
	public boolean isIdeaExist(long ideaId) {		
		return repository.findOne(ideaId)!=null;
	}

	@Override
	public void likeIdea(long ideaId) {		
		mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(ideaId)), new Update().inc("like", 1), Idea.class);
	}
	
	@Override
	public void dislikeIdea(long ideaId) {		
		mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(ideaId)), new Update().inc("dislike", 1), Idea.class);
	}

	@Override
	public List<Idea> findByUserId(long userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public IdeaJson findOne(long ideaId) {
		Idea idea = repository.findOne(ideaId);
		User user = userService.findById(idea.getUserId());
		List<Comment> commentList = commentRepository.findByIdeaId(ideaId);
		IdeaJson ideaJson = new IdeaJson(idea, user, commentList);		
		return ideaJson;
	}

}
