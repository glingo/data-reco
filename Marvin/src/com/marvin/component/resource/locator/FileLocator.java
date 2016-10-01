/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

import com.marvin.component.resource.Resource;
import java.io.File;

/**
 *
 * @author Dr.Who
 */
public class FileLocator implements LocatorInterface {
    
    public FileLocator() {}

    @Override
    public boolean supports(String name) {
//        A null resource is not valid to be located.
//        An empty file name is not valid to be located.
        return null != name && !"".equals(name);
    }
    
    @Override
    public Resource locate(String name) {
        File file = new File(name);
        
        if(!file.exists()) {
            return null;
        }
        
        return new Resource(file);
    }
    
}
