package com.novencia.dashboard.ideabox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.model.SequenceId;
import com.novencia.dashboard.ideabox.service.SequenceService;

@Service("sequenceService")
public class SequenceServiceImpl implements SequenceService{

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	public long getNextSequenceId(String key) throws SequenceException {
		// get sequence id
		Query query = new Query(Criteria.where("_id").is(key));

		// increase sequence id by 1
		Update update = new Update();
		update.inc("sequence", 1);

		// return new increased id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		// this is the magic happened.
		SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);

		// if no id, throws SequenceException
		// optional, just a way to tell user when the sequence id is failed to
		// generate.
		if (seqId == null) {
			throw new SequenceException(key);
		}

		return seqId.getSequence();
	}

}
