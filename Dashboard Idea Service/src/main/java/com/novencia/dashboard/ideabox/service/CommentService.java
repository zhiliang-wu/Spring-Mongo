package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.model.Comment;

public interface CommentService {

	List<Comment> search(String key);
	
	void saveComment(Comment comment) throws SequenceException, Exception;
	
	boolean isCommentExist(Comment comment);
	
	void updateComment(Comment comment) throws Exception;
	
}
