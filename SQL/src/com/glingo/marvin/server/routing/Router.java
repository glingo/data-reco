package com.glingo.marvin.server.routing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Router {

	protected List<Route> routes;
	
	public Router() {
		super();
		this.routes = new ArrayList<Route>();
	}

	public void route(String name, String path, String controller, String action) {
		if(path != null && !path.startsWith("/")) {
			path = "/" + path;
		}
		this.routes.add(new Route(name, path, controller, action));
	}
	
	public void route(String name, String prefix, String path, String controller, String action) {
		route(name, prefix + path, controller, action);
	}
	
	public Route getRoute(String path) {
		Route founded = null;
		for (Iterator<Route> iterator = this.routes.iterator(); iterator.hasNext();) {
			Route route = (Route) iterator.next();
			if(route.match(path))
				founded = route;
		}
		return founded;
	}

}
