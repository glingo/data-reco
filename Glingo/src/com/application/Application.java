package com.application;

import com.marvin.Marvin;


public class Application {
	
	public static void main(String[] args) {
		Marvin marvin = new Marvin(1234,
			"com.marvin.bundles.menu.MenuBundle", 
			"com.marvin.bundles.canvas.CanvasBundle"
		);
		marvin.start();
	}
}
