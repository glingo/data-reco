package com.glingo.hts.metier.environnement.biomasse.reproduction.genome.abstracts;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGene;

public abstract class Gene implements IGene {

	/** La sequence du gene. */
	private String sequence;
	
	public Gene(String sequence) {
		super();
		this.setSequence(sequence);
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
}
