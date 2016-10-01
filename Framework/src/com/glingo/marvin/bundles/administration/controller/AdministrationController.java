package com.glingo.marvin.bundles.administration.controller;


import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;

public class AdministrationController {

	public void indexAction(Request request, Response response) {
		
		response.renderFromString("<link rel=\"stylesheet\" href=\"com/glingo/marvin/bundles/fichier/ressources/view/bootstrap.css\" type=\"text/css\"/>");
		response.renderFromString("<link rel=\"stylesheet\" href=\"com/glingo/marvin/bundles/fichier/ressources/view/bootstrap-theme.css\" type=\"text/css\"/>");
		
		response.renderFromString("<h1> Bienvenue dans le bundle d'administration </h1>");
		response.renderFromString("<div> {{ name }} {{ router }} </div>");
		
	}
	
	public void testAction(Request request, Response response) {
		response.renderFromString("<h1> Bienvenue dans le bundle d'administration </h1>");
		response.renderFromString("<h2> Methode test </h2>");
	}

	public void templateAction(Request request, Response response) {
		response.render("com\\glingo\\marvin\\bundles\\administration\\ressources\\view\\administration.html.marvin");
	}
}

