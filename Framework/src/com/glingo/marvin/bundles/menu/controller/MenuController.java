package com.glingo.marvin.bundles.menu.controller;

import com.glingo.marvin.bundles.menu.ressources.components.Nav;
import com.glingo.marvin.bundles.menu.ressources.components.NavItem;
import com.glingo.marvin.bundles.menu.ressources.components.NavListItem;
import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;

public class MenuController {

	public void afficher(Request demande, Response reponse) {
		Nav barre = new Nav();
		
		for (int i = 0; i < 4; i++) {
			NavListItem menu = new NavListItem("Menu-" + i);
			
			for (int j = 0; j < 3; j++) {
				menu.add(new NavItem("Sous-menu" + j, "Sous-menu" + j, "menu"));
			}
			
			barre.add(menu);
		}
		demande.addRessource("menus", barre);
	}
	
	public void test(Request demande, Response reponse) {
		System.out.println("test");
	}
}
