package com.novencia.dashboard.ideabox.configure;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.novencia.dashboard.ideabox.domain.Comment;
import com.novencia.dashboard.ideabox.domain.Idea;
import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.json.IdeaJson;

@Configuration
public class JsonConfiguration {

	
	@Bean(name="ideaJson")
	public IdeaJson ideaJson(){
		Idea idea = new Idea("idea",1);
		User user = new User("login","first","last");
		Comment[] comments = {new Comment(1, "comment for idea1 user1", 1),new Comment(1, "comment for idea1 user2", 2)};
		List<Comment> commentList = Arrays.asList(comments);	
		IdeaJson ideaJson = new IdeaJson(idea, user, commentList);
		return ideaJson;
	}
	
	
}
