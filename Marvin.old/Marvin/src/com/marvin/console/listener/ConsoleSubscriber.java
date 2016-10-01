package com.marvin.console.listener;

import com.marvin.component.eventDispatcher.EventSubscriberInterface;
import com.marvin.console.event.ConsoleEvent;

public class ConsoleSubscriber implements EventSubscriberInterface<ConsoleEvent> {

    @Override
    public void recieve(ConsoleEvent recieve) {
        System.out.println("event");
    }

    @Override
    public boolean support(String name) {
        System.out.println(name);
        return name.matches("^console_(.)");
    }

}
