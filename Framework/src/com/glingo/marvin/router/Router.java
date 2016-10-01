package com.glingo.marvin.router;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.glingo.marvin.commons.util.ClassLoaderUtil;
import com.glingo.marvin.commons.util.StringUtils;
import com.glingo.marvin.router.strategies.AnnotationRoutingStrategy;
import com.glingo.marvin.router.strategies.IRoutingStrategy;
import com.glingo.marvin.router.strategies.XMLRoutingStrategy;

public class Router extends DefaultHandler {

	private List<Class<?>> bundles;
	private List<Route> routes;
	private String prefix = "";
	
	public Router() {
		super();
		this.setRoutes(new ArrayList<Route>());
		this.setBundles(new ArrayList<Class<?>>());
	}

	@Override
	public void startElement(String ns, String arg1, String balise, Attributes attr) throws SAXException {
		super.startElement(ns, arg1, balise, attr);
		switch (balise) {
			case "route":
				IRoutingStrategy strategy = getStrategy(StringUtils.toString(attr.getValue("type")));
				try {
					strategy.register(this, prefix, attr);
				} catch (RoutingException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "routing":
				this.setPrefix(attr.getValue("prefix"));
				break;

			default:
				break;
		}
	}
	
	@Override
	public void endElement(String ns, String arg1, String balise) throws SAXException {
		super.endElement(ns, arg1, balise);
		switch (balise) {
			case "routing":
				this.setPrefix("");
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		this.prefix = "";
	}
	
	public void update() {
		System.out.println("Update router !");
		
		try {
			SAXParserFactory parser = SAXParserFactory.newInstance();
			for (Iterator<Class<?>> iterator = getBundles().iterator(); iterator.hasNext();) {
				Class<?> bundle = (Class<?>) iterator.next();
				InputStream routing = ClassLoaderUtil.getResourceAsStream("ressources/config/routing.xml", bundle);
				parser.newSAXParser().parse(routing, this);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de l'etude du routing", e);
		}
		
		System.out.println("Router updated !");
	}

	public void registerBundle(Class<?> bundle) {
		if(bundle != null)
			getBundles().add(bundle);
	}
	
	public void route(String name, String path, String controller, String action) {
		if(path != null && !path.startsWith("/")) {
			path = "/" + path;
		}
		getRoutes().add(new Route(name, path, controller, action));
	}
	
	public void route(String name, String prefix, String path, String controller, String action) {
		route(name, prefix + path, controller, action);
	}
	
	public void route(String name, String path, Object controller, Method action) {
		if(path != null && !path.startsWith("/")) {
			path = "/" + path;
		}
		getRoutes().add(new Route(name, path, controller, action));
	}
	
	public void route(String name, String prefix, String path, Object controller, Method action) {
		if(prefix != null)
			route(name, prefix + path, controller, action);
		else
			route(name, path, controller, action);
	}
	
	private IRoutingStrategy getStrategy(String type) {
		IRoutingStrategy strategy = null;
		switch (type) {
			case "annotation":
				strategy = new AnnotationRoutingStrategy();
				break;
	
			default:
				strategy = new XMLRoutingStrategy();
				break;
		}
		return strategy;
	}
	
	public Route find(String path) {
		
		System.out.println("Trying to resole : " + path);
		Route founded = null;
		
		for (Iterator<Route> iterator = getRoutes().iterator(); iterator.hasNext();) {
			Route route = (Route) iterator.next();
			Matcher matcher = Pattern.compile(route.getPath()).matcher(path);
			if(matcher.matches()) {
				founded = route;
			}
		}
		
		if(founded != null)
			System.out.println("Router route matched : " + founded.getName());
		
		return founded;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<Class<?>> getBundles() {
		return bundles;
	}

	public void setBundles(List<Class<?>> bundles) {
		this.bundles = bundles;
	}

}
