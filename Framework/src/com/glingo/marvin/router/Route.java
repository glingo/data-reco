package com.glingo.marvin.router;

import java.lang.reflect.Method;

public class Route implements Comparable<Route> {

	private String name;
	private String path;
	private String controller;
	private String action;
	
	private Object instance;
	private Method meth;
	
	public Route(String name, String path, String controller, String action) {
		super();
		this.name = name;
		this.path = path;
		this.controller = controller;
		this.action = action;
	}
	
	public Route(String name, String path, Object controller, Method action) {
		super();
		this.name = name;
		this.path = path;
		this.instance = controller;
		this.meth = action;
		
		if(controller != null)
			this.controller = controller.getClass().getName();

		if(action != null)
			this.action = action.getName();
		
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

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	@Override
	public int compareTo(Route r) {
		int result = 0;
		if(r == null) {
			result = 1;
		} else if(this.equals(r)) {
			result = 0;
		} else {
			
		}
		return result;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Method getMeth() {
		return meth;
	}

	public void setMeth(Method meth) {
		this.meth = meth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((instance == null) ? 0 : instance.hashCode());
		result = prime * result + ((controller == null) ? 0 : controller.hashCode());
		result = prime * result + ((meth == null) ? 0 : meth.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (instance == null) {
			if (other.instance != null)
				return false;
		} else if (!instance.equals(other.instance))
			return false;
		if (controller == null) {
			if (other.controller != null)
				return false;
		} else if (!controller.equals(other.controller))
			return false;
		if (meth == null) {
			if (other.meth != null)
				return false;
		} else if (!meth.equals(other.meth))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
