package com.marvin.model.sciences.biologie;

/**
 * 
 * 	On peut donner quelques caractéristiques du vivant :
 * 	<ul>
 * 		<li>
 * 			La capacité de se maintenir en vie en puisant dans l'environnement l'énergie et les composants nécessaires. 
 * 			Cette capacité s'appuie sur le métabolisme qui inclut diverses fonctions, telles que la nutrition, la respiration, la photosynthèse...
 * 		</li>
 * 		<li> 
 * 			La capacité de se développer selon une certaine organisation (croissance, morphologie, division cellulaire, développement)
 * 		</li>
 * 		<li> 
 * 			La capacité de se reproduire et de donner naissance à d'autres organismes vivants (reproduction végétative ou sexuée)
 * 		</li>
 * 		<li> 
 * 			La nécessité d'un environnement favorable pour survivre (température, pression, oxygène, eau...).
 * 		</li>
 * 		<li> 
 * 			La matière vivante est fondée sur la chimie organique avec comme base le carbone.
 * 		</li>
 *	</ul>
 * @author Dr.Who
 *
 */
public interface Vivant {
	
	public void seDevelopper();
	
	public void seReproduire();
	
	public void seNourir();
	
	public void mourir();
	
	public void naitre();

}
