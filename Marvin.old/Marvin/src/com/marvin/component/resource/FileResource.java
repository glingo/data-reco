/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource;

/**
 *
 * @author Dr.Who
 */
public class FileResource extends Resource {
    
    protected String name;

    public FileResource(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
