package com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.abstracts;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.interfaces.IGamete;

public abstract class Gamete implements IGamete {
	
	private IGenome genome;
	
	@Override
	public void fusionner(IGamete gamete) {
		System.out.println("Fusion de deux gametes");
	}

	public IGenome getGenome() {
		return genome;
	}

	public void setGenome(IGenome genome) {
		this.genome = genome;
	}
	
}
