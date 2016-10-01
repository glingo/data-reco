package com.marvin.component.routing.loader;

import com.marvin.component.resource.loader.Loader;
import com.marvin.component.resource.Resource;
import com.marvin.component.routing.RouteCollection;
import com.marvin.component.routing.mapping.Route;
import com.marvin.util.classloader.ClassLoaderUtil;
import com.marvin.util.introspection.AnnotationUtils;
import java.lang.reflect.Method;
import java.util.List;

public class AnnotationLoader extends Loader {

    @Override
    public void load(Resource ressource) {
        RouteCollection collection = new RouteCollection();
        
        Class<?> controller = ClassLoaderUtil.loadClass(ressource.toString());
        List<Method> actions = AnnotationUtils.getMethodsAnnotated(Route.class, controller);
            
        System.out.println("RouteAnnotationLoader");
    }

    @Override
    public boolean supports(Resource ressource, String type) {
        return "annotation".equals(type);
    }
}
