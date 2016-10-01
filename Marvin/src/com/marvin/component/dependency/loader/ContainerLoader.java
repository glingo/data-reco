/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.dependency.loader;

import com.marvin.component.dependency.ContainerBuilder;
import com.marvin.component.loader.DelegateLoader;
import com.marvin.component.resource.locator.LocatorInterface;

/**
 *
 * @author Dr.Who
 */
public class ContainerLoader extends DelegateLoader {
    
    public ContainerLoader(LocatorInterface locator, ContainerBuilder builder) {
        super(locator);
        this.delegate(new XmlFileLoader(locator, builder));
    }
}
