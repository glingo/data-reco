/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.loader;

import com.marvin.component.resource.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Dr.Who
 */
public class LoaderResolver {
    
    protected List<Loader> loaders;

    public LoaderResolver(List<Loader> loaders) {
        this.loaders = new ArrayList<>();
        
        loaders.forEach((loader) -> {
            this.addLoader(loader);
        });
    }
    
    public Loader resolve(Resource ressource, String type){
        List<Loader> collected = this.collect(ressource, type);
        return collected.stream().findFirst().get();
    }
    
    private List<Loader> collect(Resource ressource, String type){
        return this.loaders.stream().filter(l -> l.supports(ressource, type)).collect(Collectors.toList());
    }
    
    public void addLoader(Loader loader)
    {
        this.loaders.add(loader);
        loader.setResolver(this);
    }

    public List<Loader> getLoaders()
    {
        return this.loaders;
    }
}
