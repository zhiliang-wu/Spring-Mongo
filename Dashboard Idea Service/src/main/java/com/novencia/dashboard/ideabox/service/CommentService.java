package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.exception.SequenceException;

public interface CommentService {

	List<Comment> search(String key);
	
	void createComment(Comment comment) throws SequenceException, Exception;
	
	boolean isCommentExist(long commentId);
	
	void updateComment(Comment comment) throws Exception;
	
}
