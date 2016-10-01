package com.glingo.hts.metier.environnement.biomasse.reproduction.production.abstracts;

import com.glingo.hts.metier.environnement.biomasse.reproduction.production.interfaces.IOrganeSexuel;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.interfaces.IGamete;

public abstract class OrganeSexuel implements IOrganeSexuel {
	/** Le type de gamete produite par l'organe.*/
	private IGamete typeDeGamete;
	
	/** L'interval entre deux production de gamete. */
	private int interval;

	public IGamete getTypeDeGamete() {
		return typeDeGamete;
	}

	public void setTypeDeGamete(IGamete typeDeGamete) {
		this.typeDeGamete = typeDeGamete;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
}
