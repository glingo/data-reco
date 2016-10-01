package com.glingo.marvin.server.event;

import java.net.Socket;

import com.glingo.marvin.eventDispatcher.Event;

public class ConnectionEvent extends Event {

	private Socket remote;
	
	public ConnectionEvent(Socket remote) {
		super("server_connection");
		this.remote = remote;
	}

	public Socket getRemote() {
		return remote;
	}

	public void setRemote(Socket remote) {
		this.remote = remote;
	}
	
	public boolean hasRemote() {
		return this.remote != null;
	}
	
}
