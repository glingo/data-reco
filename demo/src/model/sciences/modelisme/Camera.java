package model.sciences.modelisme;

import model.sciences.mathematiques.IVecteur;
import model.sciences.mathematiques.geometrie.implementation.FloatMatrice;
import model.sciences.mathematiques.geometrie.implementation.FloatVecteur;

public class Camera {

	private FloatVecteur position;
	private FloatVecteur lookAt;
	
	private double rotX;
	private double rotY;
	private double rotZ;
	
	private int focale = 800;
	private float far = 500;
	private float near = 10;
	private float angle = 90;

	public Camera() {
		lookAt = new FloatVecteur(0f, 0f, 0f);
		position = new FloatVecteur(0f, 0f, -50f);
	}
	
	public FloatMatrice see(FloatMatrice floatMatrice, int w, int h){
		FloatMatrice seen = new FloatMatrice(2, floatMatrice.getColSize());
		
		//si w == 1, alors le vecteur (x, y, z, 1) est une position dans l'espace ;
		//si w == 0, alors le vecteur (x, y, z, 0) est une direction.
		
		// nous assumons le fait que la FloatMatrice est une liste horizontale de vecteur.
		for (int i = 0; i < floatMatrice.getColSize(); i++) {
			FloatVecteur v = (FloatVecteur) floatMatrice.getColumn(i);
			seen.setColumn(i, see(v, w, h));
		}
		
		return seen;
	}

	
//	| u |			| x |
//	| v	| = K.(R.t).| y |
//	| 1 |			| z |
	public IVecteur<Float> see(FloatVecteur v, int w, int h){
		
		FloatVecteur s = v.copy();
		
		s.sub(position);
		
//		IMatrice clip = FloatMatrice.homogene(getClipFloatMatrice(w, h), 4, 0, 1);
		FloatMatrice K = FloatMatrice.homogene(getInterne(), 4, 0, 1);
		FloatMatrice R = FloatMatrice.homogene(getRotation(), 4, 0, 0);
		FloatMatrice t = FloatMatrice.homogene(getTransition(), 4, 0, 1);
		
		FloatMatrice Rt = FloatMatrice.multiply(R, t);
		FloatMatrice KRt = FloatMatrice.multiply(K, Rt);

 		s = (FloatVecteur) s.transform(KRt);
 		
// 		s = Vecteur.transform(s, clip);
		
		return s.truncate(2);
	}
	

	
	public FloatMatrice getClipFloatMatrice(int w, int h) {

//		| 		fov * ar  		,	  		0 				,			0				|
//		|     		0			,	 	   fov				,			0				|
//		|	 		0			, -(2*near*far)/(far-near)	, 			-1				|
//		|			0			,	-(far+near)/(far-near)	,			0				|	
		
		FloatMatrice floatMatrice = new FloatMatrice(4, 4);
		float fov = 1.0f / (float) Math.tan(angle/2.0);
		floatMatrice.set(0, 0, fov * ( w / h ));
		floatMatrice.set(1, 1, fov);
		floatMatrice.set(2, 2, -(far+near)/(far-near));
		floatMatrice.set(2, 3, -(2*near*far)/(far-near));
		floatMatrice.set(3, 2, -1f);
		
		return floatMatrice;
	}
	
	public FloatMatrice getInterne() {

		FloatVecteur up = new FloatVecteur((float) Math.sin(rotZ), (float) -Math.cos(rotZ), 0f);
		FloatVecteur forward = (FloatVecteur) FloatVecteur.sub(lookAt, position).normalize();
		FloatVecteur right = (FloatVecteur) FloatVecteur.cross(forward, up).normalize();
		up = (FloatVecteur) FloatVecteur.cross(forward, right).normalize();
		
		FloatMatrice m = FloatMatrice.identity(4, 4, 1f);
		m.setRow(0, right.homogene(4, 0f));
		m.setRow(1, up.homogene(4, 0f));
		m.setRow(2, forward.homogene(4, 0f));
		
		return m;
	}
	
	public void moveTo(FloatVecteur vecteur) {
		position.add(vecteur);
	}

	public void rotateX(double d) {
		rotX += d;
	}
	
	public void rotateY(double d) {
		rotY += d;
	}
	
	public void rotateZ(double d) {
		rotZ += d;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getFar() {
		return far;
	}

	public void setFar(float far) {
		this.far = far;
	}

	public float getNear() {
		return near;
	}

	public void setNear(float near) {
		this.near = near;
	}

	public int getFocale() {
		return focale;
	}

	public void setFocale(int focale) {
		this.focale = focale;
	}

	public FloatVecteur getPosition() {
		return position;
	}

	public void setPosition(FloatVecteur position) {
		this.position = position;
	}

	public FloatMatrice getRotation() {
		return (FloatMatrice) FloatMatrice.multiply(FloatMatrice.rotationX(rotX), FloatMatrice.rotationY(rotY), FloatMatrice.rotationZ(rotZ));
	}

	public FloatMatrice getTransition() {
		FloatMatrice floatMatrice = FloatMatrice.identity(4, 4, 1f);
		floatMatrice.setRow(3, position.opposite().homogene(4, 1f));
		return floatMatrice;
	}

	public float getFov() {
		return 1.0f / (float) Math.tan(angle/2.0);
	}

	public double getRotX() {
		return rotX;
	}

	public void setRotX(double rotX) {
		this.rotX = rotX;
	}

	public double getRotY() {
		return rotY;
	}

	public void setRotY(double rotY) {
		this.rotY = rotY;
	}

	public double getRotZ() {
		return rotZ;
	}

	public void setRotZ(double rotZ) {
		this.rotZ = rotZ;
	}

	public FloatVecteur getLookAt() {
		return lookAt;
	}

	public void setLookAt(FloatVecteur lookAt) {
		this.lookAt = lookAt;
	}

}
