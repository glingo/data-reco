package com.glingo.hts.metier.environnement.biomasse;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.interfaces.IOrganeSexuel;

public class Animal extends Vivant {

	private IOrganeSexuel sexe;
	
	public Animal(IGenome genome) {
		super(genome);
	}
	
	@Override
	public void seNourir() {
		System.out.println("Je suis un animal et je me nouris.");
	}

	@Override
	public void mourir() {
		System.out.println("Je suis un animal et je me meurs.");
	}

	@Override
	public void seReproduire() {
		getModeDeReproduction().seReproduire(this);
		System.out.println("Bonjour je suis un animal et je me reproduis.");
	}
	
	public IOrganeSexuel getSexe() {
		return sexe;
	}

	public void setSexe(IOrganeSexuel sexe) {
		this.sexe = sexe;
	}
}
