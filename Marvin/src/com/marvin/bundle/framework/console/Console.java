package com.marvin.bundle.framework.console;

import com.marvin.component.eventDispatcher.Dispatcher;
import com.marvin.component.eventDispatcher.Event;
import com.marvin.component.eventDispatcher.EventDispatcher;
import com.marvin.component.kernel.Kernel;
import com.marvin.bundle.framework.console.event.ConsoleEvent;
import com.marvin.bundle.framework.console.event.ConsoleEvents;
//import com.marvin.console.listener.ConsoleSubscriber;

public class Console {
    
    protected Kernel kernel;
    protected Dispatcher<Event> dispatcher = new EventDispatcher();

    public Console(Kernel kernel) {
        this.kernel = kernel;
//        dispatcher.addSubscriber(new ConsoleSubscriber());
    }
    
    public synchronized void start() {
        try {
            this.dispatcher.dispatch(ConsoleEvents.START, new ConsoleEvent(this));
//            this.kernel.load();
            this.kernel.handle(System.in, System.out);
        } catch (Exception e) {
            System.exit(-1);
        }
    }
    
    public synchronized void terminate() throws Exception {
        this.dispatcher.dispatch(ConsoleEvents.TERMINATE, new ConsoleEvent(this));
        this.kernel.terminate();
    }

    public Kernel getKernel() {
        return kernel;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public Dispatcher<Event> getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher<Event> dispatcher) {
        this.dispatcher = dispatcher;
    }
}
