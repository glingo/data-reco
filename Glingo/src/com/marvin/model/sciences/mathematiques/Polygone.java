package com.marvin.model.sciences.mathematiques;

import java.util.ArrayList;

import com.marvin.model.sciences.mathematiques.geometrie.implementation.FloatVecteur;
import com.marvin.model.sciences.mathematiques.geometrie.implementation.Polyedre;

public abstract class Polygone extends Polyedre {
	
	public Polygone(FloatVecteur o, FloatVecteur... v) {
		super(o, v);
	}
	
	public Polygone(FloatVecteur o, ArrayList<FloatVecteur> v) {
		super(o, (FloatVecteur[]) v.toArray());
	}
	
}
