package com.glingo.hts.metier.environnement.biomasse.reproduction.genome.abstracts;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IADN;

public abstract class ADN implements IADN {

	private String[] branches = {null,null};

	public String getBranche(int n) {
		return branches[n];
	}

	public void setBranche(String branche, int n) {
		this.branches[n] = branche;
	}
}
