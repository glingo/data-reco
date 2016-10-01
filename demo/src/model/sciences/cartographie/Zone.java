package model.sciences.cartographie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zone extends HashMap<Long, ArrayList<Case>>{

	/** Serial Id. */
	private static final long serialVersionUID = -8329918328410156212L;

	public Zone() {
		super();
	}

	public Zone(int taille, float arg1) {
		super(taille, arg1);
	}

	public Zone(int taille) {
		super(taille);
	}

	public Zone(Map<? extends Long, ? extends ArrayList<Case>> zones) {
		super(zones);
	}

	@Override
	public String toString() {
		return "\n\t\tZone : \n\t\t" +super.toString();
	}
}
