package com.glingo.marvin.server.routing.loader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.glingo.marvin.server.routing.Router;
import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.strategies.AnnotationRoutingStrategy;
import com.glingo.marvin.server.routing.loader.strategies.IRoutingStrategy;
import com.glingo.marvin.server.routing.loader.strategies.XMLRoutingStrategy;
import com.glingo.marvin.util.ClassLoaderUtil;

public class XMLRouteLoader extends DefaultHandler {

	private String prefix;
	private Router router;
	
	public XMLRouteLoader() {
		super();
		this.router = new Router();
	}
	
	public String resolve(String name, Attributes attributes, boolean required) throws RoutingException {
		
		if(attributes == null)
			throw new RoutingException("Pas d'attributs trouvé");
		
		String value = attributes.getValue(name);
		
		if(required && ( value == null || "".equals(value)))
			throw new RoutingException("Vous devez indiquez %s ", name);
		
		return value;
	}
	
	private void route(Attributes attributes) throws RoutingException {
		IRoutingStrategy strat = getStrategy(resolve("type", attributes, false));
		strat.route(this, prefix, attributes);
	}
	
	public void route(Class<?> bundle) throws RoutingException {
		
		InputStream routing = ClassLoaderUtil.getResourceAsStream("ressources/config/routing.xml", bundle);
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(routing, this);
		} catch (Exception e) {
			throw new RoutingException("Impossible de charger le bundle ", bundle.getName());
		}
	}
	
	public Router route(List<Class<?>> bundles) throws RoutingException {
		
		if(bundles != null  && !bundles.isEmpty()) {
			for (Iterator<Class<?>> iterator = bundles.iterator(); iterator.hasNext();) {
				Class<?> bundle = (Class<?>) iterator.next();
				route(bundle);
			}
		}
		
		return router;
	}
	
	@Override
	public void startElement(String ns, String arg1, String balise, Attributes attr) throws SAXException {
		super.startElement(ns, arg1, balise, attr);	
		try {
			switch (balise) {
				case "route":
					route(attr);
					break;
	
				case "routing":
					this.setPrefix(attr.getValue("prefix"));
					break;
	
				default:
					break;
			}				
		} catch (RoutingException e) {
			e.printStackTrace();
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
	
	private IRoutingStrategy getStrategy(String type) {
		
		if(type == null)
			return new XMLRoutingStrategy();
		
		switch (type) {
			case "annotation":
				return new AnnotationRoutingStrategy();
	
			default:
				return new XMLRoutingStrategy();
		}
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

}
