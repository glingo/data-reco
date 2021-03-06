package com.marvin.bundles.menu.controller;

import com.marvin.bundles.menu.ressources.components.Nav;
import com.marvin.bundles.menu.ressources.components.NavItem;
import com.marvin.bundles.menu.ressources.components.NavListItem;
import com.marvin.dialog.Request;
import com.marvin.dialog.Response;
import com.marvin.routing.mapping.Action;

public class DefaultControleur {

    @Action(name = "default.charger", path = "/")//, reponse="ressources/view/nav.view.twig"
    public void charger(Request demande, Response reponse) {
        System.out.println("default.charger");
        Nav barre = new Nav();

        for (int i = 0; i < 4; i++) {
            NavListItem menu = new NavListItem("Menu-" + i);

            for (int j = 0; j < 3; j++) {
                menu.add(new NavItem("Sous-menu" + j, "Sous-menu" + j, "menu"));
            }

            barre.add(menu);
        }
        reponse.addRessource("menus", barre);
    }
}
