package com.marvin.component.eventDispatcher;

public interface EventSubscriberInterface<T extends Event> {

    public void recieve(T recieve);
    public boolean support(String name);
}
