/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

import com.marvin.component.resource.Resource;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public class QueuedFileLocator extends FileLocator {
    
    protected Deque<String> queue = new ArrayDeque<>();
    protected List<String> paths;

    public QueuedFileLocator(String path) {
        this.paths = Arrays.asList(path);
    }
    
    public QueuedFileLocator(List<String> paths) {
        this.paths = paths;
    }
    
    public QueuedFileLocator(String[] paths) {
        this.paths = Arrays.asList(paths);
    }

    @Override
    public Resource locate(String name) {
        Resource resource = super.locate(name);
        
        this.queue.addAll(this.paths);
        
        while (resource == null && !queue.isEmpty()) {
            resource = super.locate(queue.poll() + File.separator + name);
        }
        
        return resource;
    }

}
