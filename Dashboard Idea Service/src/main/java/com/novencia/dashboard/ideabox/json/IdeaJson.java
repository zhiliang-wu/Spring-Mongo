package com.novencia.dashboard.ideabox.json;

import java.util.List;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.domain.User;

public class IdeaJson {

	private long id;
	private String text;
	private int like;
	private int dislike;
	private User user;
	private List<Comment> commentList;
	
	public IdeaJson(long id, String text, int like, int dislike, User user, List<Comment> commentList) {
		super();
		this.id = id;
		this.text = text;
		this.like = like;
		this.dislike = dislike;
		this.user = user;
		this.commentList = commentList;
	}

	public IdeaJson(Idea idea, User user, List<Comment> commentList) {
		super();
		this.id = idea.getId();
		this.text = idea.getText();
		this.like = idea.getLike();
		this.dislike = idea.getDislike();
		this.user = user;
		this.commentList = commentList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
}
