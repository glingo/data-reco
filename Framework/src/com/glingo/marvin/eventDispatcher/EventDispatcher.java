package com.glingo.marvin.eventDispatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventDispatcher implements Dispatcher<Event> {
	
	protected List<EventSubscriber> subscribers;

	@Override
	public void dispatch(String name, Event event) {

		if (event == null) {
			event = new Event(name);
		}

		event.setDispatcher(this);

		this.doDispatch(subscribers, event);
	}

	protected void doDispatch(List<EventSubscriber> subscribers, Event msg) {
		
		if(subscribers == null || subscribers.isEmpty())
			return;
		
		for (Iterator<EventSubscriber> iterator = subscribers.iterator(); iterator.hasNext();) {
			EventSubscriber subscriber = (EventSubscriber) iterator.next();
			subscriber.recieve(msg);
		}
	}

	@Override
	public void addSubscriber(EventSubscriber sub) {
		if(subscribers == null)
			subscribers = new ArrayList<EventSubscriber>();
		
		subscribers.add(sub);
	}

	@Override
	public void removeSubscriber(EventSubscriber sub) {
		if(subscribers == null || subscribers.isEmpty() || !subscribers.contains(sub))
			return;
			
		subscribers.remove(sub);
	}
}
