package com.marvin.bundles.canvas.controller;

import com.marvin.dialog.Request;
import com.marvin.dialog.Response;
import com.marvin.routing.mapping.Action;

public class CanvasControleur {

	@Action(name="canvas.charger", path="/canvas/charger") //formulaire=Canvas.class
	public void charger(Request demande, Response reponse) {
		System.out.println("canvas.charger");
	}
}
