/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.parser;

/**
 *
 * @author Dr.Who
 * @param <T>
 * @param <Y>
 */
public abstract class Parser<T, Y> {
    
    
    public abstract Y parse(T parsable) throws Exception;
}
