/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service.parameterBag;

import com.marvin.util.ArrayUtils;
import java.lang.reflect.Array;
import java.util.HashMap;
import javax.lang.model.type.ArrayType;

/**
 *
 * @author Dr.Who
 */
public class ParameterBag {
    
    protected HashMap<String, Object> parameters = new HashMap<>();
    protected boolean resolved = false;

    public ParameterBag() {
    }
    
    public void resolve(){
        
        if(!this.resolved) {
            return;
        }
        
        HashMap<String, Object> parameters = new HashMap();
        
        this.parameters.forEach((key, value) -> {
            value = this.resolveValue(value);
            
        });
    }
    
    public Object resolveValue(Object value){
        return null;
    }
    
    public void clear(){
        this.parameters = new HashMap<>();
    }
    
    public void add(HashMap<String, Object> parameters) {
        this.parameters.putAll(parameters);
    }
    
    public HashMap<String, Object> all(){
        return this.parameters;
    }
    
    public Object get(String name) throws Exception{
        name = name.toLowerCase();
        
        if(!this.parameters.containsKey(name)) {
            throw new Exception(String.format("Parameter '%s' not found", name));
        }
        
        return this.parameters.get(name);
    }
    
    public void set(String name, Object value){
        this.parameters.put(name, value);
    }
    
    public boolean has(String name){
        return this.parameters.containsKey(name);
    }
    
    public void remove(String name){
        this.parameters.remove(name);
    }
}
