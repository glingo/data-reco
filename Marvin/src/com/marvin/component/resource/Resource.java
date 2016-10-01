/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource;

import java.io.File;

/**
 *
 * @author Dr.Who
 */
public class Resource {
    
    protected String name;
    protected File file;

    public Resource(String name) {
        this.name = name;
    }
    
    public Resource(File file) {
        this.file = file;
        this.name = file.getAbsolutePath();
    }
    
    public Resource(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
}
