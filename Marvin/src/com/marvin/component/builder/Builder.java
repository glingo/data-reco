/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.builder;

import com.marvin.component.dependency.Definition;
import com.marvin.component.loader.LoaderInterface;

/**
 * A builder abstract class, 
 * it will provide some methods for building an object instanceof ?.
 * 
 * @author Dr.Who
 * @param <T>
 */
public abstract class Builder<T> implements BuilderInterface<T> {
    
    /* The product we build. */
    protected T product;
    
    /* The resource loader. */
    protected LoaderInterface loader;

    /* Constructor without any arguments. */
    public Builder() {}
    
    /* Provide a Constructor with the product. */
    public Builder(T product) {
        this.product = product;
    }
    
    public Builder(T product, LoaderInterface loader) {
        this.product = product;
        this.loader = loader;
    }
    
    @Override
    public void build() {
        if(this.product == null){
            this.product = this.create();
        }
    }

//    @Override
    public T getProduct() {
        return product;
    }

}
