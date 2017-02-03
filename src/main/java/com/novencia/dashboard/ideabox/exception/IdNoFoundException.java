package com.novencia.dashboard.ideabox.exception;

public class IdNoFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7459228201333159555L;
	
	public IdNoFoundException(String errKey, long id) {		
		super(errKey + " with id : " + id + " not exist");
	}
	

}
