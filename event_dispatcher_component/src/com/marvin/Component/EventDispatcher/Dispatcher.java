package com.marvin.Component.EventDispatcher;



public interface Dispatcher<T extends Event> {

    void dispatch(String name, T event);

    void addSubscriber(EventSubscriberInterface sub);

    void removeSubscriber(EventSubscriberInterface sub);

}
