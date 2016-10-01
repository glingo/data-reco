package com.glingo.marvin;

import java.util.ArrayList;
import java.util.List;

import com.glingo.bundles.administration.AdministrationBundle;
import com.glingo.bundles.canvas.CanvasBundle;
import com.glingo.bundles.console.ConsoleBundle;
import com.glingo.bundles.exemple.ExempleBundle;
import com.glingo.bundles.fichier.FichierBundle;
import com.glingo.marvin.server.Server;

public class Marvin extends Server {

	public Marvin() {
		super(8080, true);
	}

	@Override
	protected List<Class<?>> getBundles() {
		List<Class<?>> bundles = new ArrayList<Class<?>>();
		bundles.add(ExempleBundle.class);
		bundles.add(AdministrationBundle.class);
		bundles.add(CanvasBundle.class);
		bundles.add(ConsoleBundle.class);
		bundles.add(ExempleBundle.class);
		bundles.add(FichierBundle.class);
		return bundles;
	}

	public static void main(String[] args) {
//		new FramePrincipale("Vsdfsfd", 600, 600);
//		System.out.println(Runtime.getRuntime().availableProcessors());
//		System.out.println(Runtime.getRuntime().freeMemory());
//		System.out.println(Runtime.getRuntime().totalMemory());
//		System.out.println(Runtime.getRuntime().maxMemory());
		
		new Marvin().run();
		
//		Marvin marvin = new Marvin();
//		marvin.start(new MonApplication(), 8080);
//		marvin.start(new MonApplication(), 4444);

//		System.out.println(Runtime.getRuntime().freeMemory());
//		System.out.println(Runtime.getRuntime().totalMemory());
//		System.out.println(Runtime.getRuntime().maxMemory());
		
//		Runtime.getRuntime().gc();

//		System.out.println(Runtime.getRuntime().freeMemory());
//		System.out.println(Runtime.getRuntime().totalMemory());
//		System.out.println(Runtime.getRuntime().maxMemory());
	}

}
