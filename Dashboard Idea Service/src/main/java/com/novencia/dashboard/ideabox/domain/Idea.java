package main.java.com.novencia.dashboard.ideabox.model;

import org.springframework.data.annotation.Id;

public class Idea {

	@Id private long id;
	private String title;
	private String message;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
