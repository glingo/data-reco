package com.glingo.hts.metier.environnement.biomasse;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;

public abstract class Vegetal extends Vivant {

	public Vegetal(IGenome genome) {
		super(genome);
	}
	
}
