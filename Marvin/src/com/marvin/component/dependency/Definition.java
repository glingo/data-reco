/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.dependency;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public class Definition {
    
    protected String id;
    protected Class definition;
    protected Class[] types;
    protected Object[] arguments;
    protected boolean deprecated = false;

    public Definition(String id, Class definition) {
        this.id = id;
        this.definition = definition;
    }
    
    public Definition(String id, Class definition, Class[] types, Object[] arguments) {
        this.id = id;
        this.definition = definition;
        this.types = types;
        this.arguments = arguments;
    }
    
    public void replaceArgument(int index, Object arg) {
        Arrays.asList(this.arguments).set(index, arg);
    }

    public Object getArgument(int index) throws Exception
    {
        if (index < 0 || index > this.arguments.length) {
            throw new Exception(String.format("The index '%d' is not in the range [0, %d].", index, this.arguments.length));
        }

        return Arrays.asList(this.arguments).get(index);
    }
        
    public Object[] getArguments() {
        return this.arguments;
    }
    
    public List<Object> getArgumentsAsList() {
        return Arrays.asList(this.arguments);
    }
    
    public void setTypes(Class[] types) {
        this.types = types;
    }

    public Class[] getTypes() {
        return this.types;
    }
    
    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Class getDefinition() {
        return definition;
    }

    public void setDefinition(Class definition) {
        this.definition = definition;
    }
    
}
