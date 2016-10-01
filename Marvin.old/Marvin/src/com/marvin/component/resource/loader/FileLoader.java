/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.loader;

import com.marvin.component.resource.FileResource;
import com.marvin.component.resource.Resource;
import com.marvin.component.resource.locator.FileLocator;
import java.io.File;

/**
 *
 * @author Dr.Who
 */
public abstract class FileLoader extends Loader {

    protected FileLocator locator;

    public FileLoader(FileLocator locator) {
        this.locator = locator;
    }
    
    protected File locate(Resource resource) throws Exception{
        return this.locator.locate(resource.toString());
    }
    
    @Override
    public boolean supports(Resource ressource, String type) {
        
        if(ressource instanceof FileResource){
            return true;
        }
        
        return "file".equals(type);
    }

}
