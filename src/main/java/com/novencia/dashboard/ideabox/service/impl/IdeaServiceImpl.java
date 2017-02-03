package com.novencia.dashboard.ideabox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.exception.IdNoFoundException;
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
	public void createIdea(Idea idea) throws SequenceException,IdNoFoundException {
		idea.setId(sequenceService.getNextSequenceId("Idea"));
		if(!userService.isUserExist(idea.getUserId())){
			throw new IdNoFoundException("user",idea.getUserId());
		}		
		repository.save(idea);
	}

	@Override
	public void updateIdea(long ideaId, Idea idea) throws IdNoFoundException {		
		
		if(!isIdeaExist(ideaId)){
        	throw new IdNoFoundException("Idea",ideaId);
        }
		if(!userService.isUserExist(idea.getUserId())){
			throw new IdNoFoundException("User",idea.getUserId());
		}
		idea.setId(ideaId);
		repository.save(idea);		
	}
	
	@Override
	public boolean isIdeaExist(long ideaId) {		
		return repository.findOne(ideaId)!=null;
	}

	@Override
	public Idea likeIdea(long ideaId) {
		return mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(ideaId)), new Update().inc("like", 1),
				FindAndModifyOptions.options().returnNew(true), Idea.class);
	}

	@Override
	public Idea dislikeIdea(long ideaId) {
		return mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(ideaId)), new Update().inc("dislike", 1),
				FindAndModifyOptions.options().returnNew(true), Idea.class);
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

	@Override
	public void deleteById(long id) throws IdNoFoundException {
		if (!isIdeaExist(id)) {
			throw new IdNoFoundException("Idea", id);
		}
		repository.delete(id);
	}

	@Override
	public List<Idea> findAllIdeas() {
		return repository.findAll();
	}
	
	@Override
	public void deleteAllIdeas() {
		repository.deleteAll();
	}
}
