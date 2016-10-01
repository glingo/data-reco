package com.glingo.hts.util.tests;

public class Controles {

	public static Boolean estNull(Object object) {
		return (object == null);
	}
	
	public static Boolean pasNull(Object object) {
		return !estNull(object);
	}
	
	public static Boolean estVide(Object object) throws Exception {
		return (object == object.getClass().newInstance());
	}
	
	public static Boolean pasVide(Object object) throws Exception {
		return !estVide(object);
	}
	
	public static Boolean pasVideNiNull(Object object) {
		Boolean wall = false;
		try {
			wall = pasNull(object) && pasVide(object);
		} catch(Exception e) {}
		return wall;
	}
	
	public static Boolean estInstance(Object objet, Class<?> classe){
		Boolean retour = false;
		if (pasNull(objet)) {
			retour = objet.getClass().equals(classe);
		}
		return retour;
	}
}
