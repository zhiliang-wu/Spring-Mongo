package com.novencia.dashboard.ideabox.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.model.Idea;
import com.novencia.dashboard.ideabox.repository.IdeaRepository;
import com.novencia.dashboard.ideabox.service.IdeaService;

@Service("ideaService")
public class IdeaServiceImpl implements IdeaService {

	private static AtomicLong counter = new AtomicLong();
	   
    @Autowired
    private IdeaRepository repository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
 
	@Override
	public List<Idea> search(String key) {
		return mongoTemplate.find(Query.query(new Criteria().orOperator(
				Criteria.where("message").regex(key, "i"))
				), Idea.class);
	}

	@Override
	public void saveIdea(Idea idea) {
		idea.setId(counter.incrementAndGet());
		repository.save(idea);
	}

	@Override
	public boolean isIdeaExist(Idea idea) {		
		return repository.findOne(idea.getId())!=null;
	}

	@Override
	public void likeIdea(long id) {		
		mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(id)), new Update().inc("like", 1), Idea.class);
	}
	
	 public static List<Idea> populateDummyIdeas(){
	        List<Idea> Ideas = new ArrayList<Idea>();
	        Ideas.add(new Idea(counter.incrementAndGet(),"Sam"));
	        Ideas.add(new Idea(counter.incrementAndGet(),"Tom"));
	        Ideas.add(new Idea(counter.incrementAndGet(),"Jerome"));
	        Ideas.add(new Idea(counter.incrementAndGet(),"Silvia"));
	        return Ideas;
	    }


}
