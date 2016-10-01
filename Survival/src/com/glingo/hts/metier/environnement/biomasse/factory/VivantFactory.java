package com.glingo.hts.metier.environnement.biomasse.factory;

import java.security.InvalidParameterException;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;
import com.glingo.hts.metier.environnement.biomasse.interfaces.IVivant;
import com.glingo.hts.util.factory.Factory;
import com.glingo.hts.util.tests.Controles;

public abstract class VivantFactory extends Factory {
	
	@Override
	@SuppressWarnings({ "hiding", "unchecked" })
	public <IVivant> IVivant creer(Object param) {
		if (!Controles.pasVideNiNull(param)) {
			throw new InvalidParameterException("Au moins un parametre est requis pour la factory");
		}
		if (!Controles.estInstance(param, IGenome.class)){
			throw new InvalidParameterException("Le paramaetre doit etre de type IGenome");
		}
		return (IVivant) creerDepuisUnGenome((IGenome) param);
	}
	
	protected abstract IVivant creerDepuisUnGenome(IGenome genome);
}
