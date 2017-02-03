package com.novencia.dashboard.ideabox.exception;
public class SequenceException extends RuntimeException {

	private static final long serialVersionUID = -2707780892970401763L;

	public SequenceException(String errKey) {		
		super("Unable to get sequence value for key : "+errKey+"\n Please add this key in Sequence table.");
	}
	
}