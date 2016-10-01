package com.glingo.marvin.server.listener;

import com.glingo.marvin.eventDispatcher.Event;
import com.glingo.marvin.eventDispatcher.EventSubscriber;
import com.glingo.marvin.server.event.ConnectionEvent;

public class ConnectionListener extends EventSubscriber {

	@Override
	public void recieve(Event recieve) {

		if(recieve instanceof ConnectionEvent)
			System.out.println("C'est un connection event");
		System.out.println("connection event");
		
	}

}
