package com.marvin.routing;

import java.util.HashMap;

public class Routeur {

    private HashMap<String, Route> routes;

    public Routeur() {
        super();
    }

    public void addRoute(String name, Route route) {
        if(this.routes == null) {
            this.setRoutes(new HashMap<>());
        }
        
        routes.put(name, route);
    }

    public Route find(String path) {
        Route founded = null;
        
        for (Route route : routes.values()) {
            if (route.getPath().matches(path)) {
                founded = route;
            }
        }
        
        return founded;
    }

    public HashMap<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(HashMap<String, Route> routes) {
        this.routes = routes;
    }
}
