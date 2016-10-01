package com.glingo.marvin.server.response.templating.nodes;

import java.util.HashMap;

import com.glingo.marvin.util.ObjectUtils;
import com.glingo.marvin.util.StringUtils;

public class Node {

	private int start;
	private int end;
	private int lineno;
	private String inner;
	
	public Node(int start, int end, int lineno, String inner) {
		super();
		this.start = start;
		this.end = end;
		this.lineno = lineno;
		this.inner = inner;
	}
	
	public Object evaluateProperty(String property, Object bean) {
		
		if(property == null || "".equals(property))
			return StringUtils.toString(bean);

		try {
			return ObjectUtils.get(property, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Object evaluate(String key, HashMap<String, Object> model) {
		
		if(key == null || "".equals(key))
			return null;
		
		int dot = key.indexOf(".");
//		int map = this.inner.indexOf("(");
		
		if(dot != -1) {
			// xxx.yyy
			String[] splitted = this.inner.split(".");
			
			if(splitted != null) {
				String name = splitted[0];
				String property = splitted[1];
				
				Object bean = model.get(name);
				
				return evaluateProperty(property, bean);
			}
		}
		
		return model.get(key);
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public String getInner() {
		return inner;
	}
	public void setInner(String inner) {
		this.inner = inner;
	}
	
}
