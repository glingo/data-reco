package com.glingo.hts.metier.environnement.monde.positionnement;

public class Coordonee implements Comparable<Coordonee>{
	
	private Integer longitude;
	
	private Integer latitude;

	@Override
	public int compareTo(Coordonee obj) {
		return latitude;
//		Integer a = Integer.compare(this.longitude, obj.longitude);
//		Integer b = Integer.compare(this.latitude, obj.latitude);
//		return Integer.compare(a, b);
	}
}
