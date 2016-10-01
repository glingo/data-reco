package com.marvin.component.eventDispatcher;

public interface Dispatcher<T extends Event> {

    void dispatch(String name, T event);

    void addSubscriber(EventSubscriberInterface sub);

    void removeSubscriber(EventSubscriberInterface sub);

}
