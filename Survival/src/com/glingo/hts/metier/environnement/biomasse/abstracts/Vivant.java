package com.glingo.hts.metier.environnement.biomasse.abstracts;

import com.glingo.hts.metier.environnement.biomasse.interfaces.IVivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.IReproduction;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;

public abstract class Vivant implements IVivant {
	
	/** Le mode de reproduction. */
	private IReproduction modeDeReproduction;
	/** Le genome de l'etre vivant. */
	private IGenome genome;
	
	public Vivant(IGenome genome) {
		super();
		this.setGenome(genome);
	}
	
	public IReproduction getModeDeReproduction() {
		return modeDeReproduction;
	}

	public void setModeDeReproduction(IReproduction modeDeReproduction) {
		this.modeDeReproduction = modeDeReproduction;
	}

	public IGenome getGenome() {
		return genome;
	}

	public void setGenome(IGenome genome) {
		this.genome = genome;
	}
}
