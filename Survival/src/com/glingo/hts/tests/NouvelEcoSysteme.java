package com.glingo.hts.tests;

import com.glingo.hts.metier.environnement.biomasse.factory.MammifereFactory;
import com.glingo.hts.util.factory.IFactory;

public class NouvelEcoSysteme {
	
	public static void main(String[] args) {
		System.out.println("Creation d'un nouvel ecosyteme : \n");
		IFactory vivantFactory = new MammifereFactory();
	}
}
