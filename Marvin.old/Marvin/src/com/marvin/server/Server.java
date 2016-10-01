package com.marvin.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.marvin.component.eventDispatcher.Dispatcher;
import com.marvin.component.eventDispatcher.Event;
import com.marvin.component.eventDispatcher.EventDispatcher;
import com.marvin.component.kernel.Kernel;
import com.marvin.server.event.ServerEvent;
import com.marvin.server.event.ServerEvents;
import com.marvin.server.listener.ServerSubscriber;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class Server {
    
    protected int port = 8080;
    protected Kernel kernel;
    protected ServerSocket socket;

    protected Dispatcher<Event> dispatcher = new EventDispatcher();

    public Server(Kernel kernel) {
        this.kernel = kernel;
    }
    
    protected synchronized void init() throws Exception {
        dispatcher.addSubscriber(new ServerSubscriber());
//        this.kernel.load();
    }

    public void start() {

        try {
            dispatcher.dispatch(ServerEvents.START, new ServerEvent(this));
                
            init();
            
            open();

            accept();
            
            terminate();
        } catch (Exception e) {
            if (!isStopped()) {
                System.out.println("Erreur lors de l'accepation de la connection.");
                System.err.println(e.getMessage());
            }
            System.exit(-1);
        }
    }

    protected void accept() throws Exception {
        dispatcher.dispatch(ServerEvents.ACCEPT, new ServerEvent(this));
                
        while (!isStopped()) {
            System.out.println("Server waiting for connection ... ");
            
            try (Socket remote = socket.accept()) {
                
                System.err.println("Server get a connection ... ");
                
                InputStream in = remote.getInputStream();
                OutputStream out = remote.getOutputStream();
                
                System.err.println("handle");
                this.kernel.handle(in, out);
                
                remote.close();
            }
        }
    }
    
    private synchronized boolean isStopped() {
        return this.socket.isClosed();
    }

    public synchronized void terminate() throws IOException {

        if (this.socket == null || this.socket.isClosed()) {
            return;
        }
        
        this.socket.close();
        this.kernel.terminate();
    }

    private synchronized void open() {
        try {
            System.out.println("Server opening socket ... ");

            if (this.port == 0) {
                this.port = 8080;
            }

            this.socket = new ServerSocket(this.port, 8081, InetAddress.getLocalHost());

            System.out.println("Server socket opened ... ");
            
            System.out.println("Server listening ");
            System.out.println(this.socket.getInetAddress());
            System.out.println(this.socket.getLocalPort());
            System.out.println(this.socket.getLocalSocketAddress());
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'ouvrir le port : " + this.port, e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Kernel getKernel() {
        return kernel;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public Dispatcher<Event> getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher<Event> dispatcher) {
        this.dispatcher = dispatcher;
    }
}
