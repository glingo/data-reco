package com.glingo.marvin.router.strategies;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;

import com.glingo.marvin.commons.util.AnnotationUtils;
import com.glingo.marvin.commons.util.ClassLoaderUtil;
import com.glingo.marvin.router.Router;
import com.glingo.marvin.router.RoutingException;

public class XMLRoutingStrategy extends RoutingStrategy {

	public XMLRoutingStrategy() { }

	@Override
	public void register(Router routeur, String prefix, Attributes attributes) throws RoutingException {
		String ctrl = attributes.getValue("controller");
		String path = attributes.getValue("path");
		String name = attributes.getValue("name");
		String action = attributes.getValue("action");
		
		if(ctrl == null || "".equals(ctrl))
			throw new RoutingException("Vous devez indiquez un controller pour la route");

		if(path == null || "".equals(path))
			throw new RoutingException("Vous devez indiquez un path pour la route");

		if(name == null || "".equals(name))
			throw new RoutingException("Vous devez indiquez un name pour la route");

		if(action == null || "".equals(action))
			throw new RoutingException("Vous devez indiquez une action pour la route");

		Object controller = ClassLoaderUtil.getInstanceOf(ctrl);

		if(controller == null)
			throw new RoutingException("Le controller " + ctrl + " indiqué pour la route est introuvable");
		
		Method method = AnnotationUtils.getMethodNamed(action, controller);
		routeur.route(name, prefix, path, controller, method);

	}

}
