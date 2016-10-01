/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service.loader;

import com.marvin.component.resource.loader.FileLoader;
import com.marvin.component.resource.loader.LoaderInterface;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.service.ContainerBuilder;

/**
 *
 * @author Dr.Who
 */
public abstract class ContainerLoader extends FileLoader implements LoaderInterface {

    protected ContainerBuilder builder;

    public ContainerLoader(ContainerBuilder builder, FileLocator locator) {
        super(locator);
        this.builder = builder;
    }
    
}
