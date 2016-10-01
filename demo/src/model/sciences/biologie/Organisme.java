package model.sciences.biologie;

import model.sciences.biologie.alimentation.Alimentation;
import model.sciences.biologie.classification.Classification;
import model.sciences.biologie.reproduction.Reproduction;

/**
 * 	<h1> Organisme </h1>
 * 
 * 	<p> 
 * 	   	Un organisme est un �tre organis�, qui peut �tre unicellulaire ou multicellulaire. 
 * 	   	Le terme d'organisme complexe s'applique � tout organisme vivant ayant plus d'une cellule.
 * 	</p>
 * 	Selon la source d'�nergie utilis�e, on distingue :
 * 	<ul>
 * 			<li>Les chimiotrophes, tirant leur �nergie de mol�cules.</li> 
 * 			<li>Les phototrophes, tirant leur �nergie de la lumi�re.</li>
 * 	</ul>
 *
 * @author Dr.Who
 *
 */
public abstract class Organisme implements Vivant {
	
	/** La classification de l'organisme */
	protected Classification classification;

	/** Le mode de reproduction de l'organisme. */
	protected Reproduction reproduction;
	
	/** Le mode d'alimentation de l'organisme. */
	protected Alimentation alimentation;
	
	@Override
	public abstract void naitre();
	
	@Override
	public abstract void seDevelopper();
	
	@Override
	public abstract void seNourir();
	
	@Override
	public abstract void seReproduire();
	
	@Override
	public abstract void mourir();
	
}
