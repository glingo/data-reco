package com.marvin.routing.strategies;

import java.lang.reflect.Method;
import java.util.List;

import org.xml.sax.Attributes;

import com.marvin.routing.Route;
import com.marvin.routing.Routeur;
import com.marvin.routing.mapping.Action;
import com.marvin.util.classloader.ClassLoaderUtil;
import com.marvin.util.introspection.AnnotationUtils;

public class AnnotationRoutingStrategy extends RoutingStrategy {

    public AnnotationRoutingStrategy(Routeur routeur) {
        super(routeur);
    }

    @Override
    public void register(Class<?> bundle, Attributes attributes) {
        String ctrl = attributes.getValue("controleur");
        String name = attributes.getValue("name");
        Class<?> controller = ClassLoaderUtil.loadClass(ctrl);
        List<Method> actions = AnnotationUtils.getMethodsAnnotated(Action.class, controller);

        actions.stream().forEach((method) -> {
            Action action = (Action) AnnotationUtils.getAnnotation(Action.class, method);
            routeur.addRoute(name, new Route(name, action.path(), bundle, controller, method));
        });
    }

}
