package com.novencia.dashboard.ideabox.service;

import com.novencia.dashboard.ideabox.exception.SequenceException;

public interface SequenceService {

	public long getNextSequenceId(String key) throws SequenceException;
	
}
