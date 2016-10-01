package model.sciences.cartographie;

import java.util.ArrayList;
import java.util.Collection;

public class Region extends ArrayList<Zone> {

	/** Serial Id. */
	private static final long serialVersionUID = 4648886454786138087L;

	/** Le nom de la region */
	private String nom;
	
	public Region(String nom) {
		super(new ArrayList<Zone>());
		this.nom = nom;
	}

	public Region(String nom, Collection<? extends Zone> zones) {
		super(zones);
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "\n\tRegion " + nom + " : \n\t" +super.toString();
	}
	
}
