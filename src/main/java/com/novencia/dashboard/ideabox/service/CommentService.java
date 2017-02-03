package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.exception.IdNoFoundException;
import com.novencia.dashboard.ideabox.exception.SequenceException;

public interface CommentService {

	List<Comment> search(String key);
	
	void createComment(Comment comment) throws SequenceException, IdNoFoundException;
	
	boolean isCommentExist(long commentId);
	
	void updateComment(long commentId, Comment comment) throws IdNoFoundException;
	
	void deleteById(long id) throws IdNoFoundException;
}
