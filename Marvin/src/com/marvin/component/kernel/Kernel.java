package com.marvin.component.kernel;

import com.marvin.component.eventDispatcher.Dispatcher;
import com.marvin.component.eventDispatcher.Event;
import com.marvin.component.eventDispatcher.EventDispatcher;
import com.marvin.component.dependency.Container;
import com.marvin.component.dependency.ContainerBuilder;
import com.marvin.component.dependency.Definition;
import com.marvin.component.dependency.loader.ContainerLoader;
import com.marvin.component.kernel.bundle.Bundle;
import com.marvin.component.kernel.event.KernelEvent;
import com.marvin.component.kernel.event.KernelEvents;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.resource.locator.QueuedFileLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

/**
 * Le Kernel va handle un inputstream et repondre sur un outputstream.
 * @author Dr.Who
 */
public abstract class Kernel {
    protected static final Logger LOGGER = Logger.getLogger(Kernel.class.getName());
    protected static final int thread = 10;
    
    protected boolean booted = false;
    protected ExecutorService pool; 
    protected Thread running;
    protected HashMap<String, Bundle> bundles;
    
    /** c'est un service */
    protected Dispatcher<Event> dispatcher = new EventDispatcher();
    /** c'est un service */
    protected Container container;

    public Kernel() {
        super();
    }
    
    protected void boot() throws Exception {
        
        if(this.isBooted()) {
            return;
        }
        
        this.pool = Executors.newFixedThreadPool(thread);
//        this.dispatcher.addSubscriber(new KernelLogListener());
        
        this.dispatcher.dispatch(KernelEvents.BEFORE_LOAD, new KernelEvent(this));
        System.out.println("kernel booting ...");
        
        this.initializeBundles();
        this.initializeContainer();
        
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
    
    public void terminate() throws Exception {
        this.dispatcher.dispatch(KernelEvents.TERMINATE, new KernelEvent(this));
        this.pool.shutdown();
    }
    
    public void handle(InputStream in, OutputStream out){
        try {
            this.boot();
            PrintWriter writer = new PrintWriter(out, true);
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader buffered = new BufferedReader(reader);
            writer.print("Bonjour utilisateur !");
            writer.flush();
            
            String s = buffered.readLine();
	    
            writer.format("demande recue : %s\n", s);
            writer.format("reponse : %s\n", "ceci est ma reponse a votre demande");
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
    
    protected void buildContainer() throws Exception{
        System.out.println("kernel build Container ...");
//        new KernelLogListener()
//        Container container = new Container();
        
//        this.dispatcher.addSubscriber(new KernelLogListener());
        
        String pName = this.getClass().getPackage().getName();
        String dName = "src" + File.separator + pName.replace('.', File.separatorChar);
        
        ContainerBuilder builder = new ContainerBuilder();
        QueuedFileLocator locator = new QueuedFileLocator(dName);
        ContainerLoader loader = new ContainerLoader(locator, builder);
        loader.load(this.getConfigPath());
        builder.build();
        this.container = builder.getProduct();
        
//        FileLocator locator = new FileLocator(Arrays.asList("/dir", dName));
//        FileLocator locator = new FileLocator(dName);
//        DefinitionXmlFileLoader loader = new DefinitionXmlFileLoader(locator);
//        loader.load(this.getConfigPath());
        
//        ContainerBuilder builder = new ContainerBuilder(new Container());

//        loader.getResult().forEach((Definition def) -> {
//            try {
//                builder.buildDefinition(def);
//            } catch (Exception ex) {
//                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        loader.load(this.getParameterPath());
//        this.container = builder.getProduct();
        
        // Inject the kernel as a service
        this.container.set("kernel", this);
        // Inject an event dispatcher
        container.set("event.dispatcher", this.dispatcher);
    }

    public boolean isBooted() {
        return this.booted;
    }
    
    public Container getContainer() {
        return this.container;
    }
    
}
