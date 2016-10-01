/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.resource.locator;

/**
 *
 * @author Dr.Who
 * @param <T>
 */
public abstract class Locator<T> {
   
    public abstract T locate(String name) throws Exception;
    public abstract T locate(String name, String prefix) throws Exception;
}
