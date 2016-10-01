/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Dr.Who
 */
public class FileLocator extends Locator<File> {
    
    protected Queue<String> queue = new LinkedBlockingDeque<>();
    protected List<String> paths = new ArrayList<>();

    public FileLocator(String path) {
        this.paths.add(path);
    }
    
    public FileLocator(String[] paths) {
        this.paths.addAll(Arrays.asList(paths));
    }
    
    @Override
    public File locate(String name) throws Exception {
        
        if("".equals(name)) {
            throw new Exception("An empty file name is not valid to be located.");
        }
        
        this.queue.addAll(this.paths);
        
        File file;
        String path;
        
        do{
            path = queue.peek();
            file = new File(path + File.pathSeparator + name);
        }while(!file.exists() && path != null);
        
        this.queue.clear();
        return file;
    }
    
    @Override
    public File locate(String name, String currentDir) throws Exception {
        
        if(null == name) {
            return null;
        }
        
        if(null != currentDir){
            queue.add(currentDir);
        }
        
        return this.locate(name);
    }
    
}
