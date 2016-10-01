/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.test.app.service;

/**
 *
 * @author Dr.Who
 */
public class TestServiceA {
    
    protected String string;
    protected Integer integer;

    public TestServiceA(String string, Integer integer) {
        this.string = string;
        this.integer = integer;
    }
    
    public void sayHello(){
        System.out.println("Bonjour je suis le service A");
        System.out.println(this.string);
        System.out.println(this.integer);
    }
    
}
