package com.marvin.component.routing;

import java.lang.reflect.Method;

public class Route {

    private Class<?> bundle;
    private Class<?> controller;

    private Method action;

    private String name;

    private String path = "/";

    public Route() {
        super();
    }

    public Route(String name, String path, Class<?> bundle, Class<?> controller, Method action) {
        super();
        this.bundle = bundle;
        this.controller = controller;
        this.action = action;
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return getPath();
    }

    public Class<?> getController() {
        return controller;
    }

    public void setController(Class<?> controller) {
        this.controller = controller;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class<?> getBundle() {
        return bundle;
    }

    public void setBundle(Class<?> bundle) {
        this.bundle = bundle;
    }
}
