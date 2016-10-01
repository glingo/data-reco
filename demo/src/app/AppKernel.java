/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

//import com.marvin.application.Application;
import com.marvin.component.kernel.Kernel;
import com.marvin.component.kernel.bundle.Bundle;

/**
 *
 * @author Dr.Who
 */
public class AppKernel extends Kernel {

    @Override
    protected Bundle[] registerBundles() {
        return new Bundle[]{
            new app.bundles.demo.DemoBundle(),
            new app.bundles.menu.MenuBundle(),
            new app.bundles.canvas.CanvasBundle()
        };
    }
    
}
