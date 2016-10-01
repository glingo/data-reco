package com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.abstracts;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.sexuee.interfaces.IParthenogenese;

public abstract class Parthenogeniste extends ReproductionSexuee implements IParthenogenese {
	
	@Override
	public void seReproduire(Vivant vivant) {
		super.seReproduire(vivant);
		System.out.print("Parthenogeniste");
	}
}
