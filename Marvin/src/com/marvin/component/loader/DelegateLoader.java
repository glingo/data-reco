/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.loader;

import com.marvin.component.resource.Resource;
import com.marvin.component.resource.locator.LocatorInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public abstract class DelegateLoader extends Loader {

    protected List<LoaderInterface> loaders;
    
    public DelegateLoader(LocatorInterface locator) {
        super(locator);
        this.loaders = new ArrayList();
    }

    @Override
    public boolean supports(String name) {
        return this.loaders.stream().anyMatch((LoaderInterface loader) -> {
            return loader.supports(name);
        });
    }

    @Override
    public void load(Resource resource) {
        if(resource == null){
            return;
        }
        
        LoaderInterface delegate = this.filter(resource);
        if(delegate != null) {
            delegate.load(resource);
        }
    }
    
    public void delegate(LoaderInterface loader){
        this.loaders.add(loader);
    }

    protected LoaderInterface filter(Resource resource) {
        return this.loaders.stream().filter((LoaderInterface loader) -> {
            return loader.supports(resource.toString());
        }).findFirst().get();
    }

}
