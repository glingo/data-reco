package com.glingo.hts.tests;

import java.util.ArrayList;
import java.util.Iterator;

import com.glingo.hts.metier.environnement.monde.Case;
import com.glingo.hts.metier.environnement.monde.Monde;
import com.glingo.hts.metier.environnement.monde.Region;
import com.glingo.hts.metier.environnement.monde.Zone;

public class NouveauMonde {
	
	public static void main(String[] args) {
		Monde monde = new Monde();
		ajouterRegions(monde);
		System.out.println(monde);
	}
	
	private static void ajouterRegions(Monde monde) {
		ArrayList<Region> regions = new ArrayList<Region>();
		regions.add(new Region("nord"));
		regions.add(new Region("sud"));
		regions.add(new Region("est"));
		regions.add(new Region("ouest"));
		regions.add(new Region("centre"));
		for (Iterator<Region> iterator = regions.iterator(); iterator.hasNext();) {
			Region region = (Region) iterator.next();
			ajouterZones(region);
			monde.add(region);
		}
	}
	
	private static void ajouterZones(Region region) {
		for (int i = 0; i < 10; i++) {
			Zone zone = new Zone();
			ajouterCases(zone);
			region.add(zone);
		}
	}
	
	private static void ajouterCases(Zone zone) {
		for (int i = 0; i < 10; i++) {
			ArrayList<Case> cases = new ArrayList<Case>();
			for (int j = 0; j < 10; j++) {
				cases.add(new Case());
			}
			zone.put(Long.valueOf(i), cases);
		}
	}
}
