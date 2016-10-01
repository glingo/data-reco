package com.glingo.marvin.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.glingo.marvin.eventDispatcher.Dispatcher;
import com.glingo.marvin.eventDispatcher.Event;
import com.glingo.marvin.eventDispatcher.EventDispatcher;
import com.glingo.marvin.kernel.Controller;
import com.glingo.marvin.kernel.Kernel;
import com.glingo.marvin.server.event.ConnectionEvent;
import com.glingo.marvin.server.listener.ConnectionListener;

public class Server {
	
	protected int 		 		port 	= 8080;
	protected int 		 		thread 	= 10;
	
	protected Kernel			kernel;
	
	protected Thread 			running;
	protected ExecutorService 	pool;
	
	protected ServerSocket 		socket;
	protected Socket 			remote;
	
	protected Dispatcher<Event> dispatcher = new EventDispatcher();

	protected synchronized void init() {
		
		this.running = Thread.currentThread();
		
		if(this.pool == null)
			this.pool = Executors.newFixedThreadPool(thread);
		
		if(this.kernel != null)
			this.kernel.getRouter().update();
		
		dispatcher.addSubscriber(new ConnectionListener());
		
		open();
	}
	
	public void start() {
	
		init();
		
		accept();

		this.pool.shutdown();
	}
	
	protected void accept() {
		while (!isStopped()) {
			try {

				System.out.println("Server waiting for connection ... ");
				
				this.remote = socket.accept();
				
				dispatcher.dispatch("connection", new ConnectionEvent(this.remote));
				this.pool.execute(new Controller(getKernel(), getRemote()));
				
			} catch (IOException e) {
				
				if(isStopped())
					System.out.println("Server stopped");

				throw new RuntimeException("Erreur lors de l'accepation de la connection.");
			}
		}
	}
	
	private synchronized boolean isStopped() {
		return this.socket.isClosed();
	}
	
	public synchronized void stop() {

		try {
			
			if(this.socket== null || this.socket.isClosed())
				return;
			
			this.socket.close();
		} catch (IOException e) {
			throw new RuntimeException("Erreur lors de la fermeture du serveur", e);
		}
	}
	
	private synchronized void open() {
		try {
			System.out.println("Server opening socket ... ");
			
			if(this.port == 0)
				this.port = 8080;
			
			this.socket = new ServerSocket(this.port);
			
			System.out.println("Server socket opened ... ");
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'ouvrir le port : " + this.port, e);
		}
	}

	public Kernel getKernel() {
		return kernel;
	}

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public Socket getRemote() {
		return remote;
	}

	public void setRemote(Socket remote) {
		this.remote = remote;
	}
}
