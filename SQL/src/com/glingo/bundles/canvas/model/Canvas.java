package com.glingo.bundles.canvas.model;

public class Canvas {
	
	private int dimensions;
	private int width;
	private int height;
	
	public Canvas() {
		super();
	}
	
	public Canvas(int dimensions, int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.dimensions = dimensions;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}
	
}
