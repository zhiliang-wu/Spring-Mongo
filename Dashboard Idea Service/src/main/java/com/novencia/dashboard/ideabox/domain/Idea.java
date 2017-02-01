package com.novencia.dashboard.ideabox.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "idea")
public class Idea {

	@Id 
	private long id;
	private String text;
	private int like;
	private int dislike;
	private long userId;

	public Idea(){}
	
	public Idea(String text, long userId){
		this.text=text;
		this.userId=userId;
		this.like=0;
		this.dislike=0;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}
	
	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String toString() {
		return String.format(
                "Idea[id=%s, text='%s, like=%s, dislike=%s, userId=%s']",
                id, text, like, dislike, userId);
	}
	
	
}
