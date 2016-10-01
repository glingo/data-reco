package com.glingo.hts.metier.environnement.biomasse.reproduction;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;

/**
 * <h1> Reproduction Sexu�e </h1>
 * 
 * <p>
 * 		La reproduction sexuee est assur�e par la fecondation, c'est � dire par fusion des gam�tes m�le et femelle donnant un oeuf (ou zygote).<br>
 * 		Cette reproduction permet le maintien d'une diversit� g�netique au sein des populations, car elle assure le brassage g�n�tique.
 * </p>
 * 
 * <p>
 * 		Cette reproduction fait r�f�rence � la rencontre d'indicidus de types sexuels differents. 
 * 		Elle n'implique pas forcement d'accouplement ou de copulation, car des organismes immobiles comme les plantes, les champignons, les moules, sont aussi capables de reproduction.<br>
 * 		La reproduction n'est partag�e que par les especes eucaryotes, ce qui permet chez elles le brassage g�n�tique.
 * </p>
 * 
 * <p> 
 * 		Dans une m�me espece, les individus ont quasiment le m�me nombre de g�nes (35 000 chez les humains par exemple). 
 * 		En revanche, les versions de ces g�nes (les all�les) ne sont pas les m�mes. 
 * 		C'est pour cela que chaque indicidu est different. <br>
 *		Chez les especes eucaryotes, la reproduction est l'occasion de brasser, ou de melanger ces all�les entre deux individus, en g�n�ral de sexes oppos�s. 
 * 		Cela produit une nouvelle combinaison d'all�les, donc un nouveau g�nome. 
 * 		Ceci permet l'evolution des populations, et si l'environnement venait � se modifier (r�chauffement du climat, nouveau parasite ...), ces nouvelles combinaisons pourront �tre favoris�es par la selection naturelle.
 * </p>
 * 
 * @author f.cailly
 *
 */
public interface ISexuee extends IReproduction {

	public void avoirUneRelationSexuelle(Vivant partenaire);
	
}
