package com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.abstracts;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.ISexuee;

public abstract class ReproductionSexuee implements ISexuee {
	
	@Override
	public void seReproduire(Vivant vivant) {
		System.out.println("ReproductionSexuee :: ");
	}
}
