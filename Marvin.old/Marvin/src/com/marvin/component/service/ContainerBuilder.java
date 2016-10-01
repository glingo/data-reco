/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service;

import com.marvin.component.resource.FileResource;
import com.marvin.component.resource.ResourceInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public class ContainerBuilder {
    
    protected List<Definition> definitions;
    protected List<ResourceInterface> ressources;

    public ContainerBuilder() {
        this.definitions = new ArrayList<>();
        this.ressources = new ArrayList<>();
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<ResourceInterface> getRessources() {
        return ressources;
    }
    
    public void addRessource(ResourceInterface ressource) {
        this.ressources.add(ressource);
    }
    
    public void addObjectRessource(Object ressource) {
        this.addClassRessource(ressource.getClass());
    }
    
    public void addClassRessource(Class ressource) {
        do {
            this.addRessource(new FileResource(ressource.getCanonicalName()));
            ressource = ressource.getSuperclass();
        } while (ressource != null);
    }

    public void setRessources(List<ResourceInterface> ressources) {
        this.ressources = ressources;
    }
    
}
