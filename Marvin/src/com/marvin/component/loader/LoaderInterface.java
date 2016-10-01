/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.loader;

import com.marvin.component.resource.Resource;

/**
 *
 * @author Dr.Who
 */
public interface LoaderInterface {
    
    boolean supports(String type);
    void load(String ressource);
    void load(Resource resource);
}
