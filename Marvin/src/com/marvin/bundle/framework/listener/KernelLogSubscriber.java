/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.bundle.framework.listener;

import com.marvin.component.eventDispatcher.Event;
import com.marvin.component.eventDispatcher.EventSubscriberInterface;

/**
 *
 * @author Dr.Who
 */
public class KernelLogSubscriber  implements EventSubscriberInterface {

    @Override
    public void recieve(Event event) {
        System.out.println("KernelLogListener");
        System.out.format("event : %s\n", event);
    }

    @Override
    public boolean support(String name) {
        System.out.println(name);
        return name.matches("(.*)");
    }

}
