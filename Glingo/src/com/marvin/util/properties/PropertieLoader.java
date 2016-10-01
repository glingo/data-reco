package com.marvin.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.marvin.util.properties.strategy.LoaderStrategy;

public class PropertieLoader implements IPropertieLoader {

	private Properties properties;

	public PropertieLoader() {
		super();
		this.properties = new Properties();
	}
	
	public PropertieLoader(Properties properties) {
		super();
		this.properties = properties;
	}
	
	public static PropertieLoader load(LoaderStrategy strategy, String... paths) {
		PropertieLoader loader = new PropertieLoader();	
		for (int i = 0; i < paths.length; i++) {
			strategy.load(paths[i], Thread.currentThread().getContextClassLoader(), loader);
		}
		return loader;
	}

	@Override
	public void load(String name, ClassLoader loader, LoaderStrategy strategy) {
		strategy.load(name, loader, this);
	}
	
	@Override
	public void load(String name, LoaderStrategy strategy) {
		load(name, Thread.currentThread().getContextClassLoader(), strategy);
	}
	
	public void load(InputStream stream) throws IOException{
		if(properties == null){
			this.properties = new Properties();
		}
		properties.load(stream);
	}
	
	public void loadFromXml(InputStream stream) throws IOException{
		if(properties == null){
			this.properties = new Properties();
		}
		properties.loadFromXML(stream);
	}
	
	public int countProperties() {
		return getProperties().size();
	}
	
	public String getPropertie(String name) {
		String prop = null;
		if(getProperties().containsKey(name)){
			prop = getProperties().getProperty(name);
		}
		return prop;
	}
	
	public Map<String, String> getPropertiesStartingWith(String start) {
		Map<String, String> prop = new HashMap<String,String>();
		Set<String> names = getProperties().stringPropertyNames();
		for (Iterator<String> iterator = names.iterator(); iterator.hasNext();) {
			String property = (String) iterator.next();
			if(property.startsWith(start)){
				prop.put(property, getProperties().getProperty(property));
			}
		}
		return prop;
	}
	
	@Override
	public String toString() {
		return "Builder PropertiesContainer \n\t" + getProperties().toString();
	}

	@Override
	public void clearProperties() {
		this.properties = new Properties();
	}

	public Properties getProperties() {
		if(properties == null){
			this.properties = new Properties();
		}
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
