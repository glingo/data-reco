package com.glingo.marvin.bundles.canvas.controller;

import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;
import com.glingo.marvin.router.mapping.Action;

public class CanvasController {

	@Action(name="canvas.charger", path="canvas/charger")
	public void charger(Request demande, Response reponse) {
		System.out.println("canvas.charger");
	}
}
