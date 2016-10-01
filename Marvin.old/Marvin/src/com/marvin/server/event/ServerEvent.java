package com.marvin.server.event;

import com.marvin.component.eventDispatcher.Event;
import com.marvin.server.Server;

public class ServerEvent extends Event {

    private Server server;

    public ServerEvent(Server server) {
        super();
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
