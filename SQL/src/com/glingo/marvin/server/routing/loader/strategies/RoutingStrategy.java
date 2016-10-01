package com.glingo.marvin.server.routing.loader.strategies;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;

import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.XMLRouteLoader;
import com.glingo.marvin.util.ObjectUtils;

public abstract class RoutingStrategy implements IRoutingStrategy {
	
	public RoutingStrategy() { }

	@Override
	public abstract void route(XMLRouteLoader loader, String prefix, Attributes attributes) throws RoutingException;
	
	protected void route(XMLRouteLoader loader, String name, String path, Class<?> controller, String action) throws RoutingException {

		if(controller == null)
			throw new RoutingException("Le controller indiqué pour la route " + name + " est introuvable");

		if(action == null)
			throw new RoutingException("L'action indiqué pour la route " + name + " est introuvable");
		
		Method method = ObjectUtils.getMethodNamed(action, controller);
		
		if(method == null)
			throw new RoutingException("L'action " + action + " indiqué pour la route est introuvable");
		
		loader.getRouter().route(name, path, controller.getName(), action);
	}
	
}
