package com.glingo.marvin.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.glingo.bundles.administration.AdministrationBundle;
import com.glingo.bundles.canvas.CanvasBundle;
import com.glingo.bundles.console.ConsoleBundle;
import com.glingo.bundles.exemple.ExempleBundle;
import com.glingo.bundles.fichier.FichierBundle;
import com.glingo.marvin.dispatching.EventDispatcher;
import com.glingo.marvin.server.event.ServerEvent;
import com.glingo.marvin.server.event.ServerLogger;
import com.glingo.marvin.server.routing.Router;
import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.XMLRouteLoader;
import com.glingo.marvin.util.ClassLoaderUtil;

public class Server extends EventDispatcher<ServerEvent> implements Runnable {

	protected ExecutorService pool; 
	
	private boolean debug;
	private boolean started;
	private long 	startTime;
	private int 	port;

	private ServerSocket socket;
	private List<Socket> clients;
	
	private List<Class<?>> 	bundles;
	private Router 			router;
	
	public Server(int port, boolean debug) {
		this.debug 	 = debug;
		this.port 	 = port;
	 	this.clients = new ArrayList<Socket>();
	}

	protected List<Class<?>> getBundles() {
		List<Class<?>> bundles = new ArrayList<Class<?>>();
		bundles.add(ExempleBundle.class);
		bundles.add(AdministrationBundle.class);
		bundles.add(CanvasBundle.class);
		bundles.add(ConsoleBundle.class);
		bundles.add(ExempleBundle.class);
		bundles.add(FichierBundle.class);
		return bundles;
	}

	@Override
	public synchronized void run() {
		
		try {
			
			System.out.println("Starting Kernel");
			
			if(debug)
				this.addSubscriber(new ServerLogger());
			
			dispatch(new ServerEvent(ServerEvent.START, this));
			
			this.startTime = new Date().getTime();
			
			routeBundles();

			this.socket = new ServerSocket(1234);
			
			this.started = true;
			
			waitConnection();
			
		} catch (RoutingException re) {
			re.printStackTrace();
			System.err.println(re.getMessage());
		} catch(IOException io) {
			io.printStackTrace();
			System.err.println(io.getMessage());
		}

	}
	
	public synchronized void waitConnection() throws IOException {

		if(this.pool == null)
			this.pool = Executors.newFixedThreadPool(10);

		while (isStarted()) {

			System.out.println("Kernel waiting for connection");

			Socket remote = this.socket.accept();
			
			System.out.println("Un nouveau client !");
			
			this.clients.add(remote);

			accept(remote);
		}
	}
	
	public synchronized void accept(Socket remote) throws IOException {
		if(this.pool == null || this.pool.isShutdown() || this.pool.isTerminated())
			return;
		
		this.pool.execute(new Controller(this, remote));
		
	}
	
	public void stop() {
		this.bundles = null;
	}
	
	public void loadBundles(String... classpaths) {
		for (int i = 0; i < classpaths.length; i++) {
			loadBundle(classpaths[i]);
		}
	}
	
	public void loadBundle(String classpath) {
		if(classpath == null || "".equals(classpath))
			return;
		
		if(this.bundles == null && getBundles() == null)
			this.bundles = new ArrayList<Class<?>>();
		
		Class<?> bundle = ClassLoaderUtil.loadClass(classpath);

		if(bundle == null)
			return;
		
		this.bundles.add(bundle);
	}
	
	protected void routeBundles() throws RoutingException {

		this.bundles = getBundles();
		
		if(this.bundles == null)
			return;
		
		this.setRouter(new XMLRouteLoader().route(this.bundles));
		
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setBundles(List<Class<?>> bundles) {
		this.bundles = bundles;
	}

	public void toggleStarted() {
		this.started = !started;
	}

	public boolean isStarted() {
		return started;
	}

	public ServerSocket getSocket() {
		return socket;
	}

	public void setSocket(ServerSocket socket) {
		this.socket = socket;
	}

	public List<Socket> getClients() {
		return clients;
	}

	public void setClients(List<Socket> clients) {
		this.clients = clients;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
