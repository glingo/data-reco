/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.loader;

import com.marvin.component.resource.Resource;
import com.marvin.component.resource.locator.LocatorInterface;

/**
 *
 * @author Dr.Who
 */
public abstract class Loader implements LoaderInterface {
    
    protected LocatorInterface locator;
//    protected List<Resource> resources;
    
    public Loader(LocatorInterface locator) {
        this.locator = locator;
    }

    @Override
    public void load(String name) {
        Resource resource = this.locator.locate(name);
        load(resource);
    }
    
}
