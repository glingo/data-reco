/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public class Definition {
    
    protected Class definition;
    protected List<Object> arguments;
    protected boolean deprecated = false;

    public Definition() {
    }
    
    public Definition(Class definition, List<Object> arguments) {
        this.definition = definition;
        this.arguments = arguments;
    }

    public Class getDefinition() {
        return definition;
    }

    public void setDefinition(Class definition) {
        this.definition = definition;
    }
    
    public void addArgument(Object argument)
    {
        if(this.arguments == null) {
            this.arguments = new ArrayList<>();
        }
        
        this.arguments.add(argument);
    }
    
    public Object getArgument(int index) throws Exception
    {
        if (index < 0 || index > this.arguments.size() - 1) {
            throw new Exception(String.format("The index '%d' is not in the range [0, %d].", index, this.arguments.size() - 1));
        }

        return this.arguments.get(index);
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }
    
}
