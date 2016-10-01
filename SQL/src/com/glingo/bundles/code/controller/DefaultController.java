package com.glingo.bundles.code.controller;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;
import com.glingo.marvin.server.routing.mapping.Action;

public class DefaultController {
	
	@Action(name="default.charger", path="/")//, reponse="ressources/view/nav.view.twig"
	public void charger(Request demande, Response reponse) {
		System.out.println("default.charger");
	}
}
