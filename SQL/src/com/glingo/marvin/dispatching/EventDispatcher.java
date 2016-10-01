package com.glingo.marvin.dispatching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class EventDispatcher<T extends Event> {


	protected List<EventListener<T>> listeners;	
	
	public EventDispatcher() {
		super();
	}
	
	public EventDispatcher(List<EventListener<T>> listeners) {
		super();
		this.listeners = listeners;
	}
	
	public void dispatch(T event) {
		
		if(event == null)
			return;
		
		this.doDispatch(listeners, event);
		
	}
	
	public void removeSubscriber(EventListener<T> subscriber) {
		if(listeners == null || listeners.isEmpty() || !listeners.contains(subscriber)) {
			return;
		}
		
		listeners.remove(subscriber);
	}
	
	public void addSubscriber(EventListener<T> subscriber) {
		if(listeners == null) {
			listeners = new ArrayList<EventListener<T>>();
		}
		listeners.add(subscriber);
	}
	
	protected void doDispatch(List<EventListener<T>> listeners, T event) {
		
		if(listeners == null || listeners.isEmpty())
			return;
		
		for (Iterator<EventListener<T>> iterator = listeners.iterator(); iterator.hasNext();) {
			EventListener<T> subscriber = (EventListener<T>) iterator.next();
			subscriber.recieve(event.getName(), event);
			if(event.isFrozen())
				break;
		}
	}
}
