package model.sciences.biologie;

/**
 * 
 * 	On peut donner quelques caract�ristiques du vivant :
 * 	<ul>
 * 		<li>
 * 			La capacit� de se maintenir en vie en puisant dans l'environnement l'�nergie et les composants n�cessaires. 
 * 			Cette capacit� s'appuie sur le m�tabolisme qui inclut diverses fonctions, telles que la nutrition, la respiration, la photosynth�se...
 * 		</li>
 * 		<li> 
 * 			La capacit� de se d�velopper selon une certaine organisation (croissance, morphologie, division cellulaire, d�veloppement)
 * 		</li>
 * 		<li> 
 * 			La capacit� de se reproduire et de donner naissance � d'autres organismes vivants (reproduction v�g�tative ou sexu�e)
 * 		</li>
 * 		<li> 
 * 			La n�cessit� d'un environnement favorable pour survivre (temp�rature, pression, oxyg�ne, eau...).
 * 		</li>
 * 		<li> 
 * 			La mati�re vivante est fond�e sur la chimie organique avec comme base le carbone.
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
