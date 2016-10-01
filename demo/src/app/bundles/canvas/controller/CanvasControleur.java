package app.bundles.canvas.controller;

import com.marvin.component.dialog.Request;
import com.marvin.component.dialog.Response;
import com.marvin.component.routing.mapping.Route;

public class CanvasControleur {

    @Route(name = "canvas.charger", path = "/canvas/charger") //formulaire=Canvas.class
    public void charger(Request demande, Response reponse) {
        System.out.println("canvas.charger");
    }
}
