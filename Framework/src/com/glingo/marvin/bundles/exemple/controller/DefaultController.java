package com.glingo.marvin.bundles.exemple.controller;

import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;

public class DefaultController {

	public void test(Request request, Response reponse) {
		reponse.renderFromString("Salut c'est le bundle d'exemple");
	}
}
