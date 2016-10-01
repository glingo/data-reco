/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

import com.marvin.component.resource.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public abstract class DelgateLocator implements LocatorInterface {
    
    protected List<LocatorInterface> locators;
    
    public DelgateLocator() {
        this.locators = new ArrayList();
    }
    
    public DelgateLocator(LocatorInterface[] locators) {
        this.locators = Arrays.asList(locators);
    }
    
    public DelgateLocator(List<LocatorInterface> locators) {
        this.locators = locators;
    }

    public void delegate(LocatorInterface locator){
        this.locators.add(locator);
    }

    protected LocatorInterface filter(String name) {
        return this.locators.stream().filter((LocatorInterface locator) -> {
            return locator.supports(name);
        }).findFirst().get();
    }

    @Override
    public boolean supports(String name) {
        return this.locators.stream().anyMatch((LocatorInterface locator) -> {
            return locator.supports(name);
        });
    }
    
    @Override
    public Resource locate(String name) {
        LocatorInterface delegate = this.filter(name);
        
        if(delegate == null) {
            return null;
        }
        
        return delegate.locate(name);
        
    }
}
