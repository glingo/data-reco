/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.test.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Dr.Who
 */
public class TestServiceC {
    
    protected String string;
    protected Integer integer;
    protected TestServiceA reference;
    protected ArrayList collection;

    public TestServiceC(TestServiceA reference, String string, Integer integer, ArrayList collection) {
        this.string = string;
        this.integer = integer;
        this.reference = reference;
        this.collection = collection;
    }
    
    public void sayHello(){
        System.out.println("Bonjour je suis le service C");
        System.out.println(this.string);
        System.out.println(this.integer);
        System.out.println(this.collection);
        System.out.println("Je peux appeler le service A :");
        this.reference.sayHello();
    }
}
