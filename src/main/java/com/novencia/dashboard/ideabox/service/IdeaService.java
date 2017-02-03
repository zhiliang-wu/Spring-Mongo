package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.exception.IdNoFoundException;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.json.IdeaJson;

public interface IdeaService {

	List<Idea> search(String key);
	
	void createIdea(Idea idea) throws SequenceException,IdNoFoundException;
	
	void updateIdea(long ideaId,Idea idea) throws IdNoFoundException;
	
	boolean isIdeaExist(long ideaId);
	
	Idea likeIdea(long ideaId);
	
	Idea dislikeIdea(long ideaId);
	
	List<Idea> findByUserId(long userId);
	
	IdeaJson findOne(long ideaId);
	
	void deleteById(long id) throws IdNoFoundException;
	
    List<Idea> findAllIdeas();
    
    void deleteAllIdeas();
}
