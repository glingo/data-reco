package com.marvin.component.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RouteCollection {

    protected List<Route> routes;

    public RouteCollection() {
        super();
        this.routes = new ArrayList<>();
    }

    public void add(Route route) {
        routes.add(route);
    }

    public Route find(String path) {
        Optional<Route> founded = routes.stream().filter((Route route) -> {
            return route.getPath().matches(path);
        }).findFirst();
        return founded.get();
    }
}
