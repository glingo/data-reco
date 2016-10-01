package com.glingo.hts.metier.environnement.biomasse.reproduction;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;

/**
 * <h1> La Reproduction. </h1>
 * 
 * <p>
 * 		La reproduction est l'ensemble des processus par lesquels une espece se perpetue, en sucitant la naissance de nouveaux organismes.<br>
 * 		C'est une des activités fondamentales avec la nutrition et la croissance, partagées par toute les especes vivantes. <br>
 * 		En effet, toute espece doit posseder un systeme de reproduction efficace, sans quoi elle est menacée d'extinction. <br>
 * 		La reproduction est donc necessaire à la perpétuation des especes dans le temps.
 * </p>
 * @author f.cailly
 *
 */
public interface IReproduction {
	
	public void seReproduire(Vivant vivant);
	
}
