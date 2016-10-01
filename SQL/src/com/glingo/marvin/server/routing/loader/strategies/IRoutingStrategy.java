package com.glingo.marvin.server.routing.loader.strategies;

import org.xml.sax.Attributes;

import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.XMLRouteLoader;

public interface IRoutingStrategy {

	void route(XMLRouteLoader loader, String prefix, Attributes attributes) throws RoutingException;

}
