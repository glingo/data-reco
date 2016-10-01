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
public class TestServiceB {
    
    protected String string;
    protected Integer integer;
    protected TestServiceA reference;

    public TestServiceB(TestServiceA reference, String string, Integer integer) {
        this.string = string;
        this.integer = integer;
        this.reference = reference;
    }
    
    public void sayHello(){
        System.out.println("Bonjour je suis le service B");
        System.out.println(this.string);
        System.out.println(this.integer);
        System.out.println("Je peux appeler le service A :");
        this.reference.sayHello();
    }
    
}
