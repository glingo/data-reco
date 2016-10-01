package model.sciences.modelisme;

import java.util.ArrayList;
import java.util.Iterator;

import model.sciences.mathematiques.geometrie.implementation.FloatMatrice;
import model.sciences.mathematiques.geometrie.implementation.FloatVecteur;
import model.sciences.mathematiques.geometrie.implementation.Solide;

public class Monde {

	private FloatMatrice matrice = FloatMatrice.identity(4, 4, 1f);
	private ArrayList<Solide> solides = new ArrayList<Solide>();
	private ArrayList<Camera> cameras = new ArrayList<Camera>();
	private int selectedCamera;
	
	public Monde() {
		getCameras().add(new Camera());
		selectedCamera = 0;
	}
	
	public ArrayList<FloatMatrice> getRendu2D(int w, int h) {
		ArrayList<FloatMatrice> rendu = new ArrayList<FloatMatrice>();
		
		for (Iterator<Solide> iterator = solides.iterator(); iterator.hasNext();) {
			FloatMatrice solide = (FloatMatrice) iterator.next().getPoints().copy();
			rendu.add(getSelectedCamera().see(solide, w, h));
		}
		
		return rendu;
	}
	
	public FloatVecteur getRendu2D(FloatVecteur vecteur, int w, int h) {
		return (FloatVecteur) getSelectedCamera().see(vecteur, w, h);
	}
	
	public FloatMatrice getMatrice() {
		return matrice;
	}

	public void setMatrice(FloatMatrice matrice) {
		this.matrice = matrice;
	}

	public ArrayList<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(ArrayList<Camera> cameras) {
		this.cameras = cameras;
	}

	public Camera getSelectedCamera() {
		return this.getCameras().get(selectedCamera);
	}

	public void setSelectedCamera(int selectedCamera) {
		this.selectedCamera = selectedCamera;
	}

	public ArrayList<Solide> getSolides() {
		return solides;
	}

	public void setSolides(ArrayList<Solide> solides) {
		this.solides = solides;
	}
	
	public void addSolide(Solide solide) {
		getSolides().add(solide);
	}
}
