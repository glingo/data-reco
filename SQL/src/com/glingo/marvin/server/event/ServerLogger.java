package com.glingo.marvin.server.event;

import com.glingo.marvin.dispatching.EventListener;

public class ServerLogger implements EventListener<ServerEvent> {
	
	@Override
	public void recieve(String name, ServerEvent event) {
		if(name == null)
			return;
		
		switch (name) {
		case ServerEvent.START:
			System.out.println("Kernel starting ... debug : " + event.getServer().isDebug());
			break;
			
		default:
			System.out.println("Event non reconnu");
			break;
		}
	}

}
