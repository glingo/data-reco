package com.glingo.marvin.dispatching;

public interface EventListener<T> {
	
	void recieve(String name, T event);
}
