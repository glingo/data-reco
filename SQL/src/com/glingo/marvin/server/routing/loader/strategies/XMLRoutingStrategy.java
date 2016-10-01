package com.glingo.marvin.server.routing.loader.strategies;

import org.xml.sax.Attributes;

import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.XMLRouteLoader;
import com.glingo.marvin.util.ClassLoaderUtil;

public class XMLRoutingStrategy extends RoutingStrategy {

	public XMLRoutingStrategy() { }

	@Override
	public void route(XMLRouteLoader loader, String prefix, Attributes attributes) throws RoutingException {
		
		
		String ctrl = loader.resolve("controller", attributes, true);
		String path = loader.resolve("path", attributes, true);
		String name = loader.resolve("name", attributes, true);
		String action = loader.resolve("action", attributes, true);

		route(loader, name, path, ClassLoaderUtil.loadClass(ctrl), action);

	}

}
