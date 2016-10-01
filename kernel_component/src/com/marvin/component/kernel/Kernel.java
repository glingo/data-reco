package com.marvin.component.kernel;

import com.marvin.Component.EventDispatcher.Dispatcher;
import com.marvin.Component.EventDispatcher.Event;
import com.marvin.Component.EventDispatcher.EventDispatcher;
import com.marvin.component.kernel.bundle.Bundle;
import com.marvin.component.kernel.event.KernelEvent;
import com.marvin.component.kernel.event.KernelEvents;
import java.util.HashMap;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Kernel {
    
    protected static final Logger LOGGER = Logger.getLogger(Kernel.class.getName());
    
    protected boolean booted = false;
    
    protected Dispatcher<Event> dispatcher = new EventDispatcher();

    protected int thread = 10;
    protected ExecutorService pool; 
    protected Thread running;
    
    protected HashMap<String, Bundle> bundles;
    protected Container container;

    public Kernel() {
        super();
        this.pool = Executors.newFixedThreadPool(thread);
    }
    
    protected void boot() throws Exception {
        
        if(this.isBooted()) {
            return;
        }
        
        this.dispatcher.dispatch(KernelEvents.BEFORE_LOAD, new KernelEvent(this));
        System.out.println("kernel booting ...");
        
        this.initializeBundles();
        
        this.booted = true;
        
        System.out.println("kernel booted");
        this.dispatcher.dispatch(KernelEvents.AFTER_LOAD, new KernelEvent(this));
    }
    
    protected String getParameterPath() {
        return "config/parameters.xml";
    };
    
    protected String getConfigPath() {
        return "config/config.xml";
    };

    abstract protected Bundle[] registerBundles();
    
    public void terminate() {
        this.dispatcher.dispatch(KernelEvents.TERMINATE, new KernelEvent(this));
        this.pool.shutdown();
    }
    
    public void handle(InputStream in, OutputStream out){
        System.out.println("Method handle du kernel");
        System.out.println(String.format("Adresse du kernel actuel : %s", this.getClass().getName()));

        try {
            
            this.boot();
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Kernel try to boot but got an Exception :", ex);
        }
    }

    protected void initializeBundles() throws Exception {
        System.out.println("kernel initializeBundles ...");
        
        this.bundles = new HashMap<>();
        for (Bundle bundle : this.registerBundles()) {
            String name = bundle.getName();
            
            System.out.format("kernel initializing bundle %s\n", name);
        
            if (bundles.containsKey(name)) {
                throw new Exception(String.format("Double insert %s", name));
            }
            
            bundles.put(name, bundle);
        }
    }
    
    protected void initializeContainer() throws Exception{
        System.out.println("kernel initialize Container ...");
        
        this.buildContainer();
    }
    
    protected void buildContainer(){
        System.out.println("kernel build Container ...");
        
        ContainerBuilder builder = this.getContainerBuilder();
        builder.addObjectRessource(this);
        
    }
    
    protected ContainerBuilder getContainerBuilder() {
        return new ContainerBuilder();
    }

    public boolean isBooted() {
        return this.booted;
    }
    
}
