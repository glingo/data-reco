package com.glingo.hts.metier.environnement.biomasse.reproduction.production.implementation;

import java.util.ArrayList;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.abstracts.OrganeSexuel;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.abstracts.Gamete;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.implementations.Spermatozoide;

public class Testicule extends OrganeSexuel {

	@Override
	public ArrayList<Gamete> produireGamete(Vivant individu) {
		ArrayList<Gamete> gametesProduites = new ArrayList<Gamete>();
		gametesProduites.add(new Spermatozoide());
		gametesProduites.add(new Spermatozoide());
		gametesProduites.add(new Spermatozoide());
		gametesProduites.add(new Spermatozoide());
		return gametesProduites;
	}

}
