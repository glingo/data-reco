package app.bundles.menu.controller;

import app.bundles.menu.ressources.components.Nav;
import app.bundles.menu.ressources.components.NavItem;
import app.bundles.menu.ressources.components.NavListItem;
import com.marvin.component.dialog.Request;
import com.marvin.component.dialog.Response;

public class MenuControleur {

    public void afficher(Request demande, Response reponse) {
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

    public void test(Request demande, Response reponse) {
        System.out.println("test");
    }
}
