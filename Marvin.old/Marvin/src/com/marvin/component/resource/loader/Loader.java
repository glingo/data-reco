/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.loader;

import com.marvin.component.resource.Resource;

/**
 *
 * @author Dr.Who
 * @param <T>
 */
public abstract class Loader<T> implements LoaderInterface {
    
    protected T product;
    
    protected LoaderResolver resolver;
    
    public abstract void load(Resource ressource) throws Exception;
    
    @Override
    public void load(Resource ressource, String type) throws Exception {
        this.resolve(ressource, type).load(ressource);
    }
    
    public Loader resolve(Resource ressource, String type) throws Exception{
        
        if (this.supports(ressource, type)) {
            return this;
        }
        
        Loader loader = null;
        
        if (null != this.resolver) {
            loader = this.resolver.resolve(ressource, type);
        }
        
        if(null == loader) {
            throw new Exception("Loader should not be null in Lodaer.resolve");
        }
        
        return loader;
    }

    public T getProduct() {
        return product;
    }

    public void setProduct(T product) {
        this.product = product;
    }
    
    public LoaderResolver getResolver() {
        return this.resolver;
    }

    public void setResolver(LoaderResolver resolver) {
        this.resolver = resolver;
    }
    
    
}
