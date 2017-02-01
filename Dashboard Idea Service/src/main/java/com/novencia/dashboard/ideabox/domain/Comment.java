package com.novencia.dashboard.ideabox.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
public class Comment {

	@Id 
	private long id;
	private long ideaId;
	private String text;
	private long userId;
	
	public Comment(){};
	
	public Comment(long ideaId,String text){
		this.ideaId=ideaId;
		this.text=text;
	}
	
	public Comment(long ideaId,String text,long userId){
		this.ideaId=ideaId;
		this.text=text;
		this.userId=userId;
	}
	
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
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String toString() {
		return String.format(
                "Comment[id=%s, ideaId='%s, text=%s, userId=%s']",
                id, ideaId, text, userId);
	}
	
}
