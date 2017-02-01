package com.novencia.dashboard.ideabox.service;

import java.util.List;

import com.novencia.dashboard.ideabox.model.Idea;

public interface IdeaService {

	List<Idea> search(String key);
	
	void saveIdea(Idea idea);
	
	boolean isIdeaExist(Idea idea);
	
	void likeIdea(long id);
}
