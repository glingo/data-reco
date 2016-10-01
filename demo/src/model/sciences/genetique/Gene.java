package model.sciences.genetique;

/**
 * <h1> Gene. </h1>
 * 
 * <p>  
 * 		Les g�nomes sont constitu�s de regions codantes, qui correspondent aux g�nes, et des regions non-codantes. 
 * 		<br>Les regions non codantes sont constitu�es des segments interg�niques et des introns � l'interieur des g�nes. 
 * 		<br>Le s�quen�age de l'ADN permet d'etablir l'enchainement des nucl�otides des brins d'ADN, afin de cartographier le g�nome.
 * </p>
 * 
 * <p> 
 * 		Le nombre de g�nes dans le g�nome des organismes vivants varie beaucoup moins que la taille du g�nome. Chez la plupart des organismes vivants il est compris entre 1 000 et 40 000.
 * 		<br>Il n'est pas non plus corr�l� � la complexit� apparente des organismes. 
 *		<br>La param�cie, organisme cili� unicellulaire, poss�de ainsi un g�nome contenant plus de g�nes que celui de l'homme. 
 * 		<br>Le tableau suivant donne la taille totale du g�nome ( y compris les regions h�t�rochromatiques qui ne sont en g�n�ral pas s�quenc�es) 
 * 		et le nombre de g�nes pr�sents chez un certain nombre d'organisme dont le g�nome a �t� entierement s�quenc�.
 * </p>
 * 
 * <table>
 * 		<tr>
 * 			<th>Organisme</th>
 * 			<th>Nombre de g�nes</th>	
 * 			<th>Taille du g�nome</th>
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
 * 			<td>Levure de bi�re</td>
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
