/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.routing.loader;

import com.marvin.component.builder.BuilderInterface;
import com.marvin.component.loader.FileLoader;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.routing.RouteCollection;
import com.marvin.component.routing.mapping.Route;
import com.marvin.component.util.classloader.ClassLoaderUtil;
import com.marvin.component.util.introspection.AnnotationUtils;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public abstract class RouteLoader extends FileLoader {
    
    protected RouteCollection routes;

    public RouteLoader(FileLocator locator, BuilderInterface builder) {
        super(locator, builder);
    }

    @Override
    protected void parseFile(File file) throws Exception {
        RouteCollection collection = new RouteCollection();
        
        Class<?> controller = ClassLoaderUtil.loadClass(file.getName());
        List<Method> actions = AnnotationUtils.getMethodsAnnotated(Route.class, controller);
            
        routes.add(null);
        System.out.println("RouteAnnotationLoader");
    }

    @Override
    public boolean supports(String type) {
        return "annotation".equals(type);
    }
    
}
