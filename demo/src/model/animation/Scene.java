package model.animation;

import java.awt.Image;

public class Scene {

	private Image image;
	private int end;
	
	public Scene(Image image, int end) {
		super();
		this.image = image;
		this.end = end;
	}

	/**
	 * @return the iimage
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	
}
