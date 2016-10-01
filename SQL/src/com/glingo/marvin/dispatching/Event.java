package com.glingo.marvin.dispatching;

import java.util.Date;


public class Event {

	protected long id;
	private String name;
	private boolean frozen = false;
	
	public Event() {
		super();
		this.id = getId();
	}

	public Event(String name) {
		super();
		this.id = getId();
		this.setName(name);
	}

	public Event(long id, String name) {
		super();
		this.id = id;
		this.setName(name);
	}	

	protected long getId() {
		if(this.id == 0)
			this.id = new Date().getTime() * Math.min(1, Math.round(Math.random()));
		
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void freeze() {
		setFrozen(true);
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	
}
