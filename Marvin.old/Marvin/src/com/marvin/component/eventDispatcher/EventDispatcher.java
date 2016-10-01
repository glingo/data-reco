package com.marvin.component.eventDispatcher;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher implements Dispatcher {

    protected List<EventSubscriberInterface> subscribers;

    @Override
    public void dispatch(String name, Event event) {

        if (event == null) {
            event = new Event();
        }

        event.setDispatcher(this);

        this.doDispatch(subscribers, name, event);
    }

    protected void doDispatch(List<EventSubscriberInterface> subscribers, String name, Event event) {

        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }
        
        System.out.format("Dispatch event %s\n", name);

        subscribers.stream().forEach((subscriber) -> {
            if(subscriber.support(name))  {
                subscriber.recieve(event);
            }
        });
    }

    @Override
    public void addSubscriber(EventSubscriberInterface sub) {
        if (subscribers == null) {
            subscribers = new ArrayList<>();
        }

        subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(EventSubscriberInterface sub) {
        if (subscribers == null || subscribers.isEmpty() || !subscribers.contains(sub)) {
            return;
        }

        subscribers.remove(sub);
    }
}
