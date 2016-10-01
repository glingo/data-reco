package com.marvin.component.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteCollection {

    protected List<Route> routes;

    public RouteCollection() {
        super();
        this.routes = new ArrayList<>();
    }

    public void add(Route route) {
        routes.add(route);
    }

//    public Route find(String path) {
//        Route founded = null;
//        
//        for (Route route : routes.values()) {
//            if (route.getPath().matches(path)) {
//                founded = route;
//            }
//        }
//        
//        return founded;
//    }
}
