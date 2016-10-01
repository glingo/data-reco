package com.glingo.marvin.server.response;

import java.util.ArrayList;
import java.util.HashMap;

public class Response {
	
	private String protocol;
	private String status;
	private String server;
	private String contentType;
	private String contentDisposition;
	private String forward;
	private String content;
	private int code;

	
	private HashMap<String, Object> ressources = new HashMap<String, Object>();
	private HashMap<String, ArrayList<String>> flash = new HashMap<String, ArrayList<String>>();
	
	public Response(String protocol, String status, String server, String contentType, int code) {
		super();
		this.protocol = protocol;
		this.status = status;
		this.server = server;
		this.contentType = contentType;
		this.code = code;
	}
	
	public void addFlash(String key, String value) {
		if(this.flash == null) {
			this.flash = new HashMap<String, ArrayList<String>>();
		}
		
		ArrayList<String> values = this.flash.get(key);
		
		if(values == null)
			values = new ArrayList<String>();
		
		values.add(value);
		
		this.flash.put(key, values);
	}

	public void addRessource(String key, Object value) {
		if(this.ressources == null) {
			this.ressources = new HashMap<String, Object>();
		}
		this.ressources.put(key, value);
	}

	public HashMap<String, Object> getRessources() {
		return ressources;
	}

	public void setRessources(HashMap<String, Object> ressources) {
		this.ressources = ressources;
	}
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public HashMap<String, ArrayList<String>> getFlash() {
		return flash;
	}

	public void setFlash(HashMap<String, ArrayList<String>> flash) {
		this.flash = flash;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

}
