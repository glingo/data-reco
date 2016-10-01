/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.dependency;

import com.marvin.component.builder.Builder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dr.Who
 */
public class ContainerBuilder extends Builder<Container> {
    
    protected List<Definition> definitions = new ArrayList();

    public ContainerBuilder() {
        super();
    }
    
    @Override
    public Container create() {
        return new Container();
    }
    
    public Definition resolveDefinition(String reference) {
        return this.definitions.stream().filter((Definition definition) -> {
            return definition.id.equals(reference);
        }).limit(1).findFirst().get();
    }
    
    public Class resolveType(String reference) throws Exception {
        return this.resolveDefinition(reference).getDefinition();
    }
    
    public Object resolve(String reference) throws Exception{
        return this.product.get(reference);
    }
    
    public void addDefinition(Definition definition) {
        List<Class> types = new ArrayList();
        definition.getArgumentsAsList().forEach((Object arg) -> {
            if(arg instanceof Reference) {
                
            }
            try {
                types.add(this.resolveType(((Reference) arg).getId()));
            } catch (Exception ex) {
                types.add(arg.getClass());
            }
        });
        
        this.definitions.add(definition);
    }

    @Override
    public void build() {
        if(this.product == null){
            this.product = this.create();
        }
        
        this.definitions.forEach((Definition definition) -> {
            try {
                Class c = definition.getDefinition();
                Constructor cons = c.getConstructor(definition.getTypes());
                Logger.getLogger("definitions").log(Level.SEVERE, String.format("%s", Arrays.toString(definition.getTypes())));
                Logger.getLogger("definitions").log(Level.SEVERE, String.format("%s", Arrays.toString(definition.getArguments())));
                Object instance = cons.newInstance(definition.getArguments());
                Logger.getLogger("definitions").log(Level.SEVERE, String.format("%s", instance));
                this.product.set(definition.id, instance);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ContainerBuilder.class.getName()).log(Level.SEVERE, String.format("Impossible d'instancier le service  %s", definition.getDefinition()), ex);
            }
        });
    }
}
