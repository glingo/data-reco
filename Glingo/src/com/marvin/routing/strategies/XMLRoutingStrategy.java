package com.marvin.routing.strategies;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;

import com.marvin.routing.Route;
import com.marvin.routing.Routeur;
import com.marvin.util.classloader.ClassLoaderUtil;
import com.marvin.util.introspection.AnnotationUtils;

public class XMLRoutingStrategy extends RoutingStrategy {

    public XMLRoutingStrategy(Routeur routeur) {
        super(routeur);
    }

    @Override
    public void register(Class<?> bundle, Attributes attributes) {
        String ctrl = attributes.getValue("controleur");
        String path = attributes.getValue("path");
        String name = attributes.getValue("name");
        String action = attributes.getValue("action");

        Class<?> controller = ClassLoaderUtil.loadClass(ctrl);
        
        if (controller != null) {
            Method method = AnnotationUtils.getMethodNamed(action, controller);
            routeur.addRoute(name, new Route(name, path, bundle, controller, method));
        }

    }

}
