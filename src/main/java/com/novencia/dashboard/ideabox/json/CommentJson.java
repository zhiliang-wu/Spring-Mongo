package com.novencia.dashboard.ideabox.json;

import com.novencia.dashboard.ideabox.domain.User;

public class CommentJson {

	private long id;
	private long ideaId;
	private String text;
	private User user;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
