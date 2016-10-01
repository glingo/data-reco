package com.marvin.bundles.code.controller;

import com.marvin.dialog.Request;
import com.marvin.dialog.Response;
import com.marvin.routing.mapping.Action;

public class DefaultControleur {
	
	@Action(name="default.charger", path="/")//, reponse="ressources/view/nav.view.twig"
	public void charger(Request demande, Response reponse) {
		System.out.println("default.charger");
	}
}
