package com.glingo;

import com.glingo.marvin.kernel.Kernel;
import com.glingo.marvin.server.Server;

public class Home {
	

	public static void main(String[] args) {
		
		Server server = new Server();
		Kernel kernel = new Kernel();
		
		kernel.registerBundles(
			"com.glingo.marvin.bundles.exemple.ExempleBundle",
			"com.glingo.marvin.bundles.administration.AdministrationBundle",
			"com.glingo.marvin.bundles.fichier.FichierBundle",
			"com.glingo.marvin.bundles.canvas.CanvasBundle",
			"com.glingo.marvin.bundles.console.ConsoleBundle");
		
		server.setKernel(kernel);
		
		server.start();

	}
}
