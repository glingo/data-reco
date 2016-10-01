package com.marvin.Component.EventDispatcher;



public interface EventSubscriberInterface<T extends Event> {

    public void recieve(T recieve);
    public boolean support(String name);
}
