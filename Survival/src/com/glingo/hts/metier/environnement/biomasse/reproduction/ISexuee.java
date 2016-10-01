package com.glingo.hts.metier.environnement.biomasse.reproduction;

import com.glingo.hts.metier.environnement.biomasse.abstracts.Vivant;

/**
 * <h1> Reproduction Sexuée </h1>
 * 
 * <p>
 * 		La reproduction sexuee est assurée par la fecondation, c'est à dire par fusion des gamètes mâle et femelle donnant un oeuf (ou zygote).<br>
 * 		Cette reproduction permet le maintien d'une diversité génetique au sein des populations, car elle assure le brassage génétique.
 * </p>
 * 
 * <p>
 * 		Cette reproduction fait référence à la rencontre d'indicidus de types sexuels differents. 
 * 		Elle n'implique pas forcement d'accouplement ou de copulation, car des organismes immobiles comme les plantes, les champignons, les moules, sont aussi capables de reproduction.<br>
 * 		La reproduction n'est partagée que par les especes eucaryotes, ce qui permet chez elles le brassage génétique.
 * </p>
 * 
 * <p> 
 * 		Dans une même espece, les individus ont quasiment le même nombre de gènes (35 000 chez les humains par exemple). 
 * 		En revanche, les versions de ces gènes (les allèles) ne sont pas les mêmes. 
 * 		C'est pour cela que chaque indicidu est different. <br>
 *		Chez les especes eucaryotes, la reproduction est l'occasion de brasser, ou de melanger ces allèles entre deux individus, en général de sexes opposés. 
 * 		Cela produit une nouvelle combinaison d'allèles, donc un nouveau génome. 
 * 		Ceci permet l'evolution des populations, et si l'environnement venait à se modifier (réchauffement du climat, nouveau parasite ...), ces nouvelles combinaisons pourront être favorisées par la selection naturelle.
 * </p>
 * 
 * @author f.cailly
 *
 */
public interface ISexuee extends IReproduction {

	public void avoirUneRelationSexuelle(Vivant partenaire);
	
}
