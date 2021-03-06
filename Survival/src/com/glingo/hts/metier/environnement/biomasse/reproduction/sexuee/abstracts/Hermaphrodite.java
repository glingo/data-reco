package com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.abstracts;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.interfaces.IHermaphrodisme;

public abstract class Hermaphrodite extends ReproductionSexuee implements IHermaphrodisme {
	
	@Override
	public void seReproduire(Vivant vivant) {
		super.seReproduire(vivant);
		System.out.print("Hermaphrodite");
	}
}
