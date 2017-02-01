package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.json.IdeaJson;

public interface IdeaService {

	List<Idea> search(String key);
	
	void createIdea(Idea idea) throws SequenceException,Exception;
	
	void updateIdea(Idea idea) throws Exception;
	
	boolean isIdeaExist(long ideaId);
	
	void likeIdea(long ideaId);
	
	void dislikeIdea(long ideaId);
	
	List<Idea> findByUserId(long userId);
	
	IdeaJson findOne(long ideaId);
}
