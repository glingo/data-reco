package com.glingo.marvin.bundles.code.controller;

import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;
import com.glingo.marvin.router.mapping.Action;

public class DefaultController {
	
	@Action(name="default.charger", path="/")//, reponse="ressources/view/nav.view.twig"
	public void charger(Request demande, Response reponse) {
		System.out.println("default.charger");
	}
}
