/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.routing.loader;

import com.marvin.component.resource.loader.FileLoader;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.routing.RouteCollection;

/**
 *
 * @author Dr.Who
 */
public abstract class RouteLoader extends FileLoader {
    
    protected RouteCollection routes;

    public RouteLoader(RouteCollection routes, FileLocator locator) {
        super(locator);
        this.routes = routes;
    }
    
}
