package com.marvin.server.listener;

import com.marvin.component.eventDispatcher.EventSubscriberInterface;
import com.marvin.server.event.ServerEvent;

public class ServerSubscriber implements EventSubscriberInterface<ServerEvent> {

    @Override
    public void recieve(ServerEvent recieve) {
        System.out.println("event");
    }

    @Override
    public boolean support(String name) {
        System.out.println(name);
        return name.matches("^server_(.)");
    }


}
