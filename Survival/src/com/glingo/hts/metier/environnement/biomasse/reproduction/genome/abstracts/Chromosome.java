package com.glingo.hts.metier.environnement.biomasse.reproduction.genome.abstracts;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IADN;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IChromosome;

public abstract class Chromosome implements IChromosome {

	private IADN adn;

	public IADN getAdn() {
		return adn;
	}

	public void setAdn(IADN adn) {
		this.adn = adn;
	}
}
