/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service;

import java.util.HashMap;

/**
 *
 * @author Dr.Who
 */
public class Container {
    
    protected HashMap<String, String> aliases;
    protected HashMap<String, Object> services;
    
    /**
     * Returns true if the given service is defined.
     *
     * @param id
     * @return bool true if the service is defined, false otherwise
     */
    public boolean has(String id)
    {
        return "service_container".equals(id)
                || this.aliases.containsKey(id)
                || this.services.containsKey(id);
    }
    
    public void set(String id, Object service) throws Exception {
        id = id.toLowerCase();
        
        if(this.services.containsKey(id)) {
            this.services.remove(id);
        }
        
        this.services.put(id, service);
        
        if(null == service) {
            this.services.remove(id);
        }
    }
    
    public Object get(String id) throws Exception {
        
        if(this.aliases.containsKey(id)) {
            id = this.aliases.get(id);
        }
        
        if(this.services.containsKey(id)) {
            return this.services.get(id);
        }
        
        throw new Exception(String.format("Service %s not found in container.", id));
    }
    
    public void reset() {
        this.services = new HashMap<>();
    }
}
