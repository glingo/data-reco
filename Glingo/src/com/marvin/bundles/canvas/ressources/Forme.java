package com.marvin.bundles.canvas.ressources;

import java.util.List;

import com.marvin.model.sciences.mathematiques.Vecteur;

public class Forme {

	private List<Vecteur<?>> points;
	
	public Forme(List<Vecteur<?>> points) {
		super();
		this.points = points;
	}

	/**
	 * @return the points
	 */
	public List<Vecteur<?>> getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(List<Vecteur<?>> points) {
		this.points = points;
	}
	
}
