package com.glingo.bundles.canvas.controller;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;
import com.glingo.marvin.server.routing.mapping.Action;

public class CanvasController {

	@Action(name="canvas.charger", path="canvas/charger")
	public void charger(Request demande, Response reponse) {
		System.out.println("canvas.charger");
	}
}
