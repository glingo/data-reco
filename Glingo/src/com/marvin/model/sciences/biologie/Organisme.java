package com.marvin.model.sciences.biologie;

import com.marvin.model.sciences.biologie.alimentation.Alimentation;
import com.marvin.model.sciences.biologie.classification.Classification;
import com.marvin.model.sciences.biologie.reproduction.Reproduction;

/**
 * 	<h1> Organisme </h1>
 * 
 * 	<p> 
 * 	   	Un organisme est un être organisé, qui peut être unicellulaire ou multicellulaire. 
 * 	   	Le terme d'organisme complexe s'applique à tout organisme vivant ayant plus d'une cellule.
 * 	</p>
 * 	Selon la source d'énergie utilisée, on distingue :
 * 	<ul>
 * 			<li>Les chimiotrophes, tirant leur énergie de molécules.</li> 
 * 			<li>Les phototrophes, tirant leur énergie de la lumière.</li>
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
