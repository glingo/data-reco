package com.glingo.marvin.server.response.templating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.glingo.marvin.server.response.templating.nodes.Node;
import com.glingo.marvin.util.ClassLoaderUtil;
import com.glingo.marvin.util.StringUtils;

public class Template {

	private String path;
	private String body;
	private boolean isLoaded;
	private List<Node> nodeList;

	public Template() {
		super();
	}
	
	public Template(String path) {
		super();
		this.path = path;
	}
	
	public void load(String path) throws IOException {

		if(this.isLoaded || path == null)
			return;
		
		// Attention securite acces fichier !!
		InputStream in = ClassLoaderUtil.getResourceAsStream(path);

		if (in == null)
			return;
		
		try {
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buffer = new BufferedReader(reader);

			String line = "";
			
			do {
				line = buffer.readLine();
				
				if(line != null)
					body += line;
				
				if(!body.endsWith(StringUtils.NEWLINE))
					body += StringUtils.NEWLINE;
				
			} while (line != null);
			
			this.isLoaded = true;
		} finally {
			if(in != null)
				in.close();
		}
		
	}
	
	public void load() throws IOException {
		
		if(body == null)
			body = "";
		
		load(path);
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean loaded) {
		isLoaded = loaded;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
}
