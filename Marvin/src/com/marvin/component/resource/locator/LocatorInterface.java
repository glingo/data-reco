/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

import com.marvin.component.resource.Resource;

/**
 *
 * @author Dr.Who
 */
public interface LocatorInterface {
    
    boolean supports(String name);
    Resource locate(String name);
//    T locate(String resource, String currentDir) throws Exception;
//    T locate(String resource, Class calling) throws Exception;
    
}
