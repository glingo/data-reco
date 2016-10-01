package com.marvin.test.app.bundles.test.controller;

import com.marvin.component.routing.mapping.Route;

public class TestControleur {

    @Route(name = "test.charger", path = "/")//, reponse="ressources/view/nav.view.twig"
    public void charger() {
        System.out.println("default.charger");
    }
}
