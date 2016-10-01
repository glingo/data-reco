package com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.abstracts;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.interfaces.IGonochorisme;

public abstract class Gonochoriste extends ReproductionSexuee implements IGonochorisme {

	@Override
	public void seReproduire(Vivant vivant) {
		super.seReproduire(vivant);
		System.out.print("Gonochoriste");
	}
}
