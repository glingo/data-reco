package com.glingo.marvin.kernel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;

import com.glingo.marvin.router.Route;

public class Controller implements Runnable {

	private long id;
	
	private Kernel kernel;
	
	private Request request;
	private Response response;
	private Socket remote;
	
	public Controller(Kernel kernel, Socket remote) {
		super();
		this.setId(new Date().getTime() + Math.round(Math.random() * 10));
		this.setKernel(kernel);
		this.setRemote(remote);
	}

	@Override
	public void run() {
		
		synchronized (this) {
			
			try {

				long start = new Date().getTime();
				
				Socket remote = getRemote();
				
				if(remote != null) {
					request = Request.fromStream(remote.getInputStream());
					response = Response.HTTPResponse();
					
					response.addRessource("app.router", kernel.getRouter());
					
					process(request, response);
					
					response.renderFromString("<div class='container'>");
					
					response.renderFromString("<div class='row'>");
					
					if(kernel.getRouter() != null) {
						response.renderFromString("<div class='col-lg-4'>");
						response.renderFromString("<ul class='list-group'>");
						for (Iterator<Route> iterator = kernel.getRouter().getRoutes().iterator(); iterator.hasNext();) {
							Route iterated = (Route) iterator.next();
							response.renderFromString("<li class='list-group-item'> <span>" + iterated.getName() + "</span> :: <span>" + iterated.getPath() + "</span> :: <span>" + iterated.getAction() + "</span> </li>");
						}
						response.renderFromString("</ul>");
						response.renderFromString("</div>");
					}
					
					response.renderFromString("<div class='col-lg-4'>");
					response.renderFromString("\t<ul class='list-group'>");
					response.renderFromString("\t\t<li class='list-group-item alert alert-danger'> Controller " + this.getId() + ". </li>");
					response.renderFromString("\t\t<li class='list-group-item alert alert-danger'> Kernel " + kernel.getId() + ". </li>");
					response.renderFromString("\t\t<li class='list-group-item alert alert-success'> Execution en : " + (new Date().getTime() - start) + " ms. </li>");
					response.renderFromString("\t</ul>");
					response.renderFromString("</div>");

					response.renderFromString("</div>");
					response.renderFromString("</div>");
					
					response.flush(remote.getOutputStream());
					
					remote.close();
					
					System.out.println("Data sent.");
				}
				
			} catch (IOException e) {
				System.exit(-1);
			}
		}
		
	}
	
	public void process(Request request, Response reponse) {
		
		synchronized (this) {
			
			if(getKernel() == null || getKernel().getRouter() == null)
				return;
			
			Route route = getKernel().getRouter().find(request.getUri());

			if(route == null || route.getInstance() == null || route.getMeth() == null)
				return;
			
			Object controller = route.getInstance();
			Method action = route.getMeth();
			
			try {
				action.setAccessible(true);
				action.invoke(controller, request, reponse);
				action.setAccessible(false);
			} catch(Exception e) {
				response.renderFromString("<span class='alert alert-danger'> " + e.getMessage() + " </span><br>");
			}
		}
			
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Kernel getKernel() {
		return kernel;
	}

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Socket getRemote() {
		return remote;
	}

	public void setRemote(Socket remote) {
		this.remote = remote;
	}
	
}
