package com.glingo.bundles.administration.controller;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;

public class AdministrationController {

	public void indexAction(Request request, Response response) {
		response.addRessource("name", "Florian");
		response.addRessource("controller", this.getClass().getSimpleName());
		response.addRessource("user", "Florian");
		response.addRessource("request", request);
		response.setForward("com/glingo/bundles/administration/ressources/view/administration.html.marvin");
	}
	
	public void testAction(Request request, Response response) { }

	public void templateAction(Request request, Response response) { }
}

