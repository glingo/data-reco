package com.marvin.model.sciences.genetique;

/**
 * <h1> Gene. </h1>
 * 
 * <p>  
 * 		Les génomes sont constitués de regions codantes, qui correspondent aux gènes, et des regions non-codantes. 
 * 		<br>Les regions non codantes sont constituées des segments intergéniques et des introns à l'interieur des gènes. 
 * 		<br>Le séquençage de l'ADN permet d'etablir l'enchainement des nucléotides des brins d'ADN, afin de cartographier le génome.
 * </p>
 * 
 * <p> 
 * 		Le nombre de gènes dans le génome des organismes vivants varie beaucoup moins que la taille du génome. Chez la plupart des organismes vivants il est compris entre 1 000 et 40 000.
 * 		<br>Il n'est pas non plus corrélé à la complexité apparente des organismes. 
 *		<br>La paramécie, organisme cilié unicellulaire, possède ainsi un génome contenant plus de gènes que celui de l'homme. 
 * 		<br>Le tableau suivant donne la taille totale du génome ( y compris les regions hétérochromatiques qui ne sont en général pas séquencées) 
 * 		et le nombre de gènes présents chez un certain nombre d'organisme dont le génome a été entierement séquencé.
 * </p>
 * 
 * <table>
 * 		<tr>
 * 			<th>Organisme</th>
 * 			<th>Nombre de gènes</th>	
 * 			<th>Taille du génome</th>
 * 		</tr>
 * 		<tr>
 * 			<td>Haemophilus influenzae(bacterie)</td>
 *			<td>1800</td>	
 *			<td>1,8Mpb</td>
 * 		</tr>
 * 		<tr>
 * 			<td>Escherichia coli(bacterie)</td>
 *			<td>4300</td>	
 *			<td>4,6Mpb</td>
 * 		</tr>
 * 		<tr>
 * 			<td>Levure de bière</td>
 *			<td>6000</td>	
 *			<td>12,1Mpb</td>
 *		</tr>
 * </table>
 * 
 * @author f.cailly
 */
public abstract class Gene {

	protected double mutationRate;
	protected String sequence;

	public Gene(double mutationRate, String sequence) {
		super();
		this.mutationRate = mutationRate;
	}
	
	/**
	 * @return the mutationRate
	 */
	public double getMutationRate() {
		return mutationRate;
	}


	/**
	 * @param mutationRate the mutationRate to set
	 */
	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
}
