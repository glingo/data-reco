package com.marvin.console.event;

import com.marvin.component.eventDispatcher.Event;
import com.marvin.console.Console;

public class ConsoleEvent extends Event {

    protected Console console;
    
    public ConsoleEvent(Console console) {
        super();
        this.console = console;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }
    
}
