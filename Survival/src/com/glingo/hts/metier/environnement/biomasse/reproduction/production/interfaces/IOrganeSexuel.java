package com.glingo.hts.metier.environnement.biomasse.reproduction.production.interfaces;

import java.util.ArrayList;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;
import com.glingo.hts.metier.environnement.biomasse.reproduction.production.produits.abstracts.Gamete;

/**
 * 
 * @author f.cailly
 *
 */
public interface IOrganeSexuel {
	
	public ArrayList<Gamete> produireGamete(Vivant individu);
	
}
