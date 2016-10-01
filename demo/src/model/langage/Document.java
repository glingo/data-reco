package model.langage;

import java.io.File;
import java.util.Date;

public class Document {

	private String auteur;
	private String titre;
	private Date xdate;
	private File file;
	
	public Document() {
		super();
	}
	
	public Document(String chemin) {
		setFile(new File(chemin));
	}

	public Document(String auteur, String chemin, String titre) {
		super();
		this.auteur = auteur;
		this.titre = titre;
		setFile(new File(chemin));
		setXdate(new Date());
	}
	
	public Document(String auteur, String chemin, String titre, Date xdate) {
		super();
		this.auteur = auteur;
		this.titre = titre;
		this.xdate = xdate;
		setFile(new File(chemin));
	}
	
	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}
	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return the xdate
	 */
	public Date getXdate() {
		return xdate;
	}
	/**
	 * @param xdate the xdate to set
	 */
	public void setXdate(Date xdate) {
		this.xdate = xdate;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
