package com.glingo.marvin.eventDispatcher;

public interface Dispatcher<T> {

	public void dispatch(String name, T msg);
	
	public void addSubscriber(EventSubscriber sub);
	
	public void removeSubscriber(EventSubscriber sub);

}
