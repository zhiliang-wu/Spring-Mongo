package com.novencia.dashboard.ideabox.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This collection is used to store the auto increase sequence id 
 * When a new collection want to use this feature, you need to init the sequence in mongo db by  
 * db.sequence.insert({_id: "new collection name",sequence: 0})
 * @author zhiliang.wu
 *
 */
@Document(collection = "sequence")
public class SequenceId {

	@Id
	private String id;

	private long sequence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	
}