package com.glingo.marvin.server.event;

import com.glingo.marvin.dispatching.Event;
import com.glingo.marvin.server.Server;

public class ServerEvent extends Event {

	public static final String START = "START";
	
	private Server server;
	
	public ServerEvent(String name, Server server) {
		super(name);
		this.setServer(server);
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
