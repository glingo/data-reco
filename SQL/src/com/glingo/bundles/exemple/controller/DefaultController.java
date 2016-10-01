package com.glingo.bundles.exemple.controller;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;
import com.glingo.marvin.server.routing.mapping.Action;

public class DefaultController {

	@Action(name="exemple.charger", path="/exemple/charger")//, reponse="ressources/view/nav.view.twig"
	public void charger(Request request, Response reponse) {
		System.out.println("default.charger");
	}
	
	public void test(Request request, Response reponse) { }
}
