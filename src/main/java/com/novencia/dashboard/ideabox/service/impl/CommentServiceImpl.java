package com.novencia.dashboard.ideabox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.exception.IdNoFoundException;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.repository.CommentRepository;
import com.novencia.dashboard.ideabox.service.CommentService;
import com.novencia.dashboard.ideabox.service.IdeaService;
import com.novencia.dashboard.ideabox.service.SequenceService;
import com.novencia.dashboard.ideabox.service.UserService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	   
    @Autowired
    private CommentRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
 
    @Autowired
    private SequenceService sequenceService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private IdeaService ideaService;
    
	@Override
	public List<Comment> search(String key) {
		return mongoTemplate.find(Query.query(new Criteria().orOperator(
				Criteria.where("text").regex(key, "i"))
				), Comment.class);
	}

	@Override
	public void createComment(Comment comment) throws SequenceException,IdNoFoundException {
		
		comment.setId(sequenceService.getNextSequenceId("comment"));		
		if(!userService.isUserExist(comment.getUserId()))
			throw new IdNoFoundException("User",comment.getUserId());
		if(!ideaService.isIdeaExist(comment.getIdeaId()))
			throw new IdNoFoundException("Idea",comment.getIdeaId());
		
		repository.save(comment);
	}


	@Override
	public void updateComment(long commentId, Comment comment) throws IdNoFoundException {

		if(!isCommentExist(commentId))
			throw new IdNoFoundException("Comment",commentId);
		if(!userService.isUserExist(comment.getUserId()))
			throw new IdNoFoundException("User",comment.getUserId());
		if(!ideaService.isIdeaExist(comment.getIdeaId()))
			throw new IdNoFoundException("Idea",comment.getIdeaId());
		comment.setId(commentId);
		repository.save(comment);
	}

	
	@Override
	public boolean isCommentExist(long commentId) {
		return repository.findOne(commentId)!=null;
	}

	@Override
	public void deleteById(long id) throws IdNoFoundException {
		if (!isCommentExist(id)) {
			throw new IdNoFoundException("Comment", id);
		}
		repository.delete(id);
	}

}
