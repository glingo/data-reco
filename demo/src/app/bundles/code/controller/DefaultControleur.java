package app.bundles.code.controller;

import com.marvin.component.dialog.Request;
import com.marvin.component.dialog.Response;
import com.marvin.component.routing.mapping.Route;

public class DefaultControleur {

    @Route(name = "default.charger", path = "/")//, reponse="ressources/view/nav.view.twig"
    public void charger(Request demande, Response reponse) {
        System.out.println("default.charger");
    }
}
