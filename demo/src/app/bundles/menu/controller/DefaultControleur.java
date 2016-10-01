package app.bundles.menu.controller;

import app.bundles.menu.ressources.components.Nav;
import app.bundles.menu.ressources.components.NavItem;
import app.bundles.menu.ressources.components.NavListItem;
import com.marvin.component.dialog.Request;
import com.marvin.component.dialog.Response;
import com.marvin.component.routing.mapping.Route;

public class DefaultControleur {

    @Route(name = "default.charger", path = "/")//, reponse="ressources/view/nav.view.twig"
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
//        reponse.addRessource("menus", barre);
    }
}
