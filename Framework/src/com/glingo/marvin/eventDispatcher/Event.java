package com.glingo.marvin.eventDispatcher;

import java.util.Date;

public class Event {

	private long id;
	private String name;
	private EventDispatcher dispatcher;
	
	public Event() {
		super();
		this.setId(new Date().getTime() + Math.round(Math.random() * 10));
	}
	
	public Event(String name) {
		super();
		this.setId(new Date().getTime() + Math.round(Math.random() * 10));
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public EventDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
