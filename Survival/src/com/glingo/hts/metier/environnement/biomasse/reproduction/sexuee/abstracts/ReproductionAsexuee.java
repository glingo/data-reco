package com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.abstracts;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.IAsexuee;

public abstract class ReproductionAsexuee implements IAsexuee {
	
	@Override
	public void seReproduire(Vivant vivant) {
		System.out.println("ReproductionAsexuee :: ");
	}
}
