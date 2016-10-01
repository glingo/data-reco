package com.glingo.marvin.router.strategies;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;

import com.glingo.marvin.commons.util.AnnotationUtils;
import com.glingo.marvin.commons.util.ClassLoaderUtil;
import com.glingo.marvin.router.Router;
import com.glingo.marvin.router.RoutingException;
import com.glingo.marvin.router.mapping.Action;

public class AnnotationRoutingStrategy extends RoutingStrategy {

	public AnnotationRoutingStrategy() { }

	@Override
	public void register(Router routeur, String prefix, Attributes attributes) throws RoutingException {
		String ctrl = attributes.getValue("controller");
		
		if(ctrl == null || "".equals(ctrl))
			throw new RoutingException("Vous devez indiquez un controller pour la route");

		Object controller = ClassLoaderUtil.getInstanceOf(ctrl);

		if(controller == null)
			throw new RoutingException("Le controller " + ctrl + " indiqué pour la route est introuvable");
		
		List<Method> actions = AnnotationUtils.getMethodsAnnotated(Action.class, controller);
		
		for (Iterator<Method> iterator = actions.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			Action action = (Action) AnnotationUtils.getAnnotation(Action.class, method);
			routeur.route(action.name(), prefix, action.path(), controller, method);
		}
	}

}
