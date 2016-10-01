package com.glingo.hts.metier.environnement.biomasse.reproduction.genome.abstracts;

import java.util.ArrayList;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IChromosome;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;

public abstract class Genome extends ArrayList<IChromosome> implements IGenome{
	
	/** Serial. */
	private static final long serialVersionUID = -1669063984634947394L;
	/** Le nombre de paire de base. */
	private Long taille;

	public Long getTaille() {
		return taille;
	}

	public void setTaille(Long taille) {
		this.taille = taille;
	}
}
