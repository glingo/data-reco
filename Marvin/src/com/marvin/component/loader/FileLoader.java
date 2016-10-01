/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.loader;

import com.marvin.component.resource.Resource;
import com.marvin.component.resource.locator.LocatorInterface;
import java.io.File;

/**
 *
 * @author Dr.Who
 */
public abstract class FileLoader extends Loader {

    public FileLoader(LocatorInterface locator) {
        super(locator);
    }

    @Override
    public void load(Resource resource) {
        this.load(resource.getFile());
    }

    protected abstract void load(File file);

}
