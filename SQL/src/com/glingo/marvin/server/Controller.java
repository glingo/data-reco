package com.glingo.marvin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.request.reader.RequestReader;
import com.glingo.marvin.server.response.Response;
import com.glingo.marvin.server.response.templating.Renderer;
import com.glingo.marvin.server.routing.Route;
import com.glingo.marvin.util.ClassLoaderUtil;
import com.glingo.marvin.util.ObjectUtils;

public class Controller extends Renderer implements Runnable {
	
	private PrintWriter writer;
	private BufferedReader reader;
	
	private InetAddress client;
	
	private Server server;
	private Socket remote;

	public Controller(Server server, Socket remote) throws IOException {
		super();
		this.server = server;
		this.remote = remote;
	}

	@Override
	public synchronized void run() {
		
		try {

			this.writer = new PrintWriter(remote.getOutputStream());
			
			this.reader = new BufferedReader(new InputStreamReader(remote.getInputStream()));
			
			this.client = remote.getInetAddress();
			
			Request request = RequestReader.read(this.reader);
			
			Response response = new Response("HTTP/1.1", "OK", "MARVIN", "text/html", 200);
			
			Route route = this.server.getRouter().getRoute(request.getUri());

			if(route != null) {
				Object controller = ClassLoaderUtil.getInstanceOf(route.getController());
				Method action	  = ObjectUtils.getMethodNamed(route.getAction(), controller);
				
				action.setAccessible(true);
				action.invoke(controller, request, response);
				action.setAccessible(false);
			}
			
			terminate(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				remote.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void terminate(Request request, Response reponse) throws IOException {
		
		try {
			writer.println(reponse.getProtocol() + " " + reponse.getCode() + " " + reponse.getStatus());
			writer.println("Content-Type : " + reponse.getContentType());

			if(reponse.getContent() != null)
				writer.println("Content-Length : " + reponse.getContent().getBytes().length);
			
			if(reponse.getContentDisposition() != null && !"".equals(reponse.getContentDisposition()))
				writer.println("Content-Disposition : " + reponse.getContentDisposition());

			if(reponse.getServer() != null && !"".equals(reponse.getServer()))
				writer.println("Server : " + reponse.getServer());
			
			writer.println();
			
			render(writer, reponse, request);
		} finally {
			writer.flush();
			writer.close();
			System.out.println("data sent.");
		}
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public InetAddress getClient() {
		return client;
	}

	public void setClient(InetAddress client) {
		this.client = client;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Socket getRemote() {
		return remote;
	}

	public void setRemote(Socket remote) {
		this.remote = remote;
	}

}
