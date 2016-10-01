package com.glingo.hts.data;

import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.implementations.Gene;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.implementations.GenomeImp;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGene;
import com.glingo.hts.metier.environnement.biomasse.reproduction.genome.interfaces.IGenome;

public abstract class EnvironnementPool {

	public static IGene gonochoriste = new Gene("GONO");
	
	public static IGene hermaphrodite = new Gene("HERM");
	
	public static IGene parthenogeniste = new Gene("PART");
	
	public static IGenome genome1 = new GenomeImp(); 
	
}
