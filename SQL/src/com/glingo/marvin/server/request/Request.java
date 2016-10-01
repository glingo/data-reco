package com.glingo.marvin.server.request;

import java.util.HashMap;

public class Request {
	
	private HashMap<String, String> headers;

	private String type;
	private String method;
	private String uri;
	private String protocol;
	private HashMap<String, Object> ressources;

	public Request() {
		super();
	}
	
	public void forceRessource(String key, Object ressource) {
		addRessource(key, ressource, true);
	}
	
	public void addRessource(String key, Object ressource, boolean override) {
		if(getRessources() == null)
			setRessources(new HashMap<String, Object>());
		
		if(!override && !getRessources().containsKey(key))
			getRessources().put(key, ressource);
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public HashMap<String, Object> getRessources() {
		return ressources;
	}

	public void setRessources(HashMap<String, Object> ressources) {
		this.ressources = ressources;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String name, String header) {
		if(getHeaders() == null) 
			setHeaders(new HashMap<String, String>());
		
		getHeaders().put(name, header);
	}

}
