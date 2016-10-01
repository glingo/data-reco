package model.sciences.mathematiques.geometrie.implementation;

public class Polygone extends Polyedre {

	
//		   a	
//		   /\
//		  /  \
//	   	 /	  \		
//	  	/  	   \		
//	   /		\
//	  /		 	 \	
//	 /____________\
//	d		   	  c
	
//	a ___________ b		
//	 |			 |		
//	 |			 |		
//	 |			 |		
//	 |			 |
//	 |___________|
//	d			  c
	
//		a______ b		
//	    /	   \
//	 h /		\ c
//	   |	  	 |
//	 g \        / d
//		\______/
//		f		e
	
	public Polygone(FloatVecteur o, FloatVecteur... v) {
		super(o, v);
	}

}
