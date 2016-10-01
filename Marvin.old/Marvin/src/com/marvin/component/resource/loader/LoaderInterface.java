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
 */
public interface LoaderInterface {
    
    boolean supports(Resource ressource, String type);
    void load(Resource ressource) throws Exception;
    void load(Resource ressource, String type) throws Exception;
 
}
