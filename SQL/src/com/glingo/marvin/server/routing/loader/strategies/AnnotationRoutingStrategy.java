package com.glingo.marvin.server.routing.loader.strategies;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;

import com.glingo.marvin.server.routing.RoutingException;
import com.glingo.marvin.server.routing.loader.XMLRouteLoader;
import com.glingo.marvin.server.routing.mapping.Action;
import com.glingo.marvin.util.ObjectUtils;
import com.glingo.marvin.util.ClassLoaderUtil;

public class AnnotationRoutingStrategy extends RoutingStrategy {

	public AnnotationRoutingStrategy() { }

	@Override
	public void route(XMLRouteLoader loader, String prefix, Attributes attributes) throws RoutingException {
		String ctrl = attributes.getValue("controller");
		
		if(ctrl == null || "".equals(ctrl))
			throw new RoutingException("Vous devez indiquez un controller pour la route");

		Class<?> controller = ClassLoaderUtil.loadClass(ctrl);

		if(controller == null)
			throw new RoutingException("Le controller " + ctrl + " indiqué pour la route est introuvable");
		
		List<Method> actions = ObjectUtils.getMethodsAnnotated(Action.class, controller);
		
		for (Iterator<Method> iterator = actions.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			Action action = (Action) ObjectUtils.getAnnotation(Action.class, method);
			route(loader, action.name(), action.path(), controller , method.getName());
		}
	}

}
