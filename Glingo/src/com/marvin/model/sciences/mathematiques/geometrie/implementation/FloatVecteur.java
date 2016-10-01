package com.marvin.model.sciences.mathematiques.geometrie.implementation;

import java.util.Arrays;

import com.marvin.model.sciences.mathematiques.IMatrice;
import com.marvin.model.sciences.mathematiques.IVecteur;
import com.marvin.model.sciences.mathematiques.Vecteur;
import com.marvin.model.sciences.mathematiques.geometrie.exceptions.DimensionException;

public class FloatVecteur extends Vecteur<Float> {
	
	public FloatVecteur(int capacity) {
		super(capacity);
	}
	
	public FloatVecteur(Float... values) {
		super(values);
	}
	
	@Override
	public FloatVecteur copy() {
		int i = 0;
		int len = this.getDimension();
		
		FloatVecteur v = new FloatVecteur(len);
		
		for (; i < len; i++) {
			v.set(i, this.get(i));
		}
		
		return v;
	}
	
	/**
	 * Le produit vectoriel sert à calculer le FloatVecteur perpendiculaire à deux autres FloatVecteurs. 
	 * Le produit vectoriel se dit cross product (produit croisé) en anglais.
	 * Le calcul du produit vectoriel se fait à partir de deux FloatVecteurs et permet d'obtenir un autre FloatVecteur. 
	 * Ce FloatVecteur obtenu est perpendiculaire aux deux autres FloatVecteurs... 
	 * tout comme la normale d'une face est perpendiculaire à la face elle même.
	 */
	@Override
	public FloatVecteur cross(IVecteur<Float> v) {
		
		if ((this.getDimension() != 3) || (v.getDimension() != 3))
			throw new DimensionException(
			"Le cross product ne peut être effectuée que si les deux FloatVecteurs sont de dimensions 3");
		
		return new FloatVecteur(
			(this.get(1) * v.get(2)) - (this.get(2) * v.get(1)),
			(this.get(2) * v.get(0)) - (this.get(0) * v.get(2)),
			(this.get(0) * v.get(1)) - (this.get(1) * v.get(0))
		);
	}
	
	public static float dot(FloatVecteur a, FloatVecteur b) {
		
		if(a.getDimension() != b.getDimension())
			throw new DimensionException(
			"Le produit scalaire ne peut être effectuée que si les deux FloatVecteurs ont les mêmes dimensions");
		
		FloatVecteur r = a.copy();
		return r.dot(b);
	}
	
	public static FloatVecteur mult(FloatVecteur a, float k) {
		
		FloatVecteur r = a.copy();
		r.mult(k);
		
		return r;
	}
	
	public static FloatVecteur extend(FloatVecteur a, float... v) {
		FloatVecteur r = new FloatVecteur(v.length + a.getDimension());
		int j = 0;
		for (int i = 0; i < r.getDimension(); i++) {
			if ( i < a.getDimension()) {
				r.set(i, a.get(i));
			} else {
				r.set(i, v[j++]);
			}
		}
		return r;
	}
	
	/**
	 * Notez que si vous échangez les FloatVecteurs a et b dans les calculs, 
	 * le FloatVecteur obtenu sera exactement le FloatVecteur opposé à celui que vous auriez obtenu sans échanger a et b.
	 * @param a
	 * @param b
	 * @return
	 */
	public static FloatVecteur cross(FloatVecteur a, FloatVecteur b) {
		
		if(a.getDimension() != b.getDimension())
			throw new DimensionException(
			"La multiplication ne peut être effectuée que si les deux FloatVecteurs ont les mêmes dimensions");
		
		FloatVecteur r = a.copy();
		return r.cross(b);
	}
	
	public static IVecteur<Float> opposite(FloatVecteur a) {
		FloatVecteur r = a.copy();
		return r.opposite();
	}

	public static FloatVecteur add(FloatVecteur a, FloatVecteur b) {
		
		if(a.getDimension() != b.getDimension())
			throw new DimensionException(
			"L'addition ne peut être effectuée que si les deux FloatVecteurs ont les mêmes dimensions");
		
		FloatVecteur r = a.copy();
		return (FloatVecteur) r.add(b);
	}

	public static FloatVecteur sub(FloatVecteur a, FloatVecteur b) {
		
		if(a.getDimension() != b.getDimension())
			throw new DimensionException(
			"La substraction ne peut être effectuée que si les deux FloatVecteurs ont les mêmes dimensions");
		
		FloatVecteur r = a.copy();
		r.sub(b);
		
		return r;
	}
	
	public static IVecteur<Float> transform(FloatVecteur v, IMatrice<Float> m) {
		return v.transform(m);
	}

	@Override
	public IVecteur<Float> transform(IMatrice<Float> m) {
		return FloatMatrice.multiply(this, m).getRow(0);
	}

	@Override
	public IVecteur<Float> homogene(int size, Float c) {
		FloatVecteur v = new FloatVecteur(this.getDimension() + (  size - this.getDimension())); 
		for (int i = 0; i < size; i++) {
			if(i < getDimension())
				v.set(i, this.get(i));
			else
				v.set(i, c);
		}
		return v;
	}

	@Override
	public int compareTo(IVecteur<Float> o) {
		if(this.getDimension() != o.getDimension())
			throw new DimensionException();
		return (int) (this.length() - o.length());
	}

	@Override
	public IVecteur<Float> add(IVecteur<Float> v) {
		int i = 0;
		int len = this.getDimension();
		
		for (; i < len; i++) {
			this.set(i, this.get(i) + v.get(i));
		}
		
		return this;
	}

	@Override
	public IVecteur<Float> truncate(int dim) {
		
		if(this.getDimension() < dim)
			throw new DimensionException();
		
		FloatVecteur v = new FloatVecteur(dim);
		
		int i = 0;
		int len = v.getDimension();
		
		for (; i < len; i++) {
			v.set(i, this.get(i));
		}
		
		return v;
	}

	@Override
	public void sub(IVecteur<Float> v) {
		
		int i = 0;
		int len = this.getDimension();
		
		for (; i < len; i++) {
			this.set(i, this.get(i) - v.get(i));
		}
		
	}

	@Override
	public IVecteur<Float> mult(Float n) {
		
		int i = 0;
		int len = this.getDimension();
		
		for (; i < len; i++) {
			this.set(i, this.get(i) * n);
		}
		
		return this;
	}

	@Override
	public void div(Float n) {
		
		int i = 0;
		int len = this.getDimension();
		
		for (; i < len; i++) {
			this.set(i, this.get(i) / n);
		}
		
	}

	@Override
	public Float dot(IVecteur<Float> vecteur) {
		
		int i = 0;
		int len = this.getDimension();
		float dot = 0;
		
		for (; i < len; i++) {
			dot += this.get(i) * this.get(i);
		}
		
		return dot; 
	}

	@Override
	public IVecteur<Float> opposite() {
		
		int i = 0;
		int len = this.getDimension();
		
		for (; i < len; i++) {
			this.set(i, -this.get(i));
		}
		
		return this;
	}

	@Override
	public Float length() {
		return (float) Math.sqrt(this.dot(this));
	}

	@Override
	public IVecteur<Float> normalize() {
		float len = this.length();
		if(len > 0)
			this.div(len);
		
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloatVecteur other = (FloatVecteur) obj;
		if (getDimension() != other.getDimension())
			return false;
		if (!Arrays.equals(getValues(), other.getValues()))
			return false;
		return true;
	}
}
