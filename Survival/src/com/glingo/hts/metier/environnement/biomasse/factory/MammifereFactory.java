package com.glingo.hts.metier.environnement.biomasse.factory;

import com.glingo.hts.metier.environnement.biomasse.interfaces.IVivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;
import com.glingo.hts.util.factory.IFactory;

public class MammifereFactory extends VivantFactory {

	@Override
	protected IVivant creerDepuisUnGenome(IGenome genome) {
		return null;
	}

	@Override
	public IFactory getInstance() {
		if (this.instance == null) {
			this.instance = new MammifereFactory();
		}
		return this.instance;
	}

}
