package com.glingo.hts.metier.environnement.biomasse.abstracts;

import com.glingo.hts.metier.environnement.biomasse.interfaces.IMammifere;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;

public abstract class Mammifere extends Vivant implements IMammifere {
	
	public Mammifere(IGenome genome) {
		super(genome);
	}

	@Override
	public void seNourir() {
		System.out.println("Je suis un Mammifere et je me nouris.");
	}

	@Override
	public void mourir() {
		System.out.println("Je suis un Mammifere et je me meurs.");
	}

	@Override
	public void seReproduire() {
		getModeDeReproduction().seReproduire(this);
		System.out.println("Je suis un Mammifere et je me reproduis.");
	}
	
}
