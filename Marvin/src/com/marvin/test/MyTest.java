/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.test;

import com.marvin.component.kernel.Kernel;
import com.marvin.bundle.framework.server.Server;
import com.marvin.bundle.framework.console.Console;
import com.marvin.test.app.AppKernel;

/**
 *
 * @author Dr.Who
 */
public class MyTest {

    public MyTest() {
        
        Kernel kernel = new AppKernel();
        Console console = new Console(kernel);
        console.start();
        
        Server server = new Server(kernel);
        server.start();
        
//        try {
//            Container container = kernel.getContainer();
//            System.out.println("test de recuperation de service");
//            TestServiceA a = (TestServiceA) container.get("test.service.a");
//            TestServiceB b = (TestServiceB) container.get("test.service.b");
//            TestServiceC c = (TestServiceC) container.get("test.service.c");
//            
//            System.out.println(a);
//            a.sayHello();
//            System.out.println(b);
//            b.sayHello();
//            System.out.println(c);
//            c.sayHello();
//        } catch (Exception ex) {
//            Logger.getLogger(MyTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
}
