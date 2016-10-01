package com.glingo.marvin.bundles.menu.ressources.components;

public class NavItem extends Object {
	
	private String parent;

	private String label;
	
	private String action;
	
	public NavItem(String label) {
		super();
		setLabel(label);
	}
	
	public NavItem(String titre, String action, String parent) {
		super();
		setLabel(titre);
		setAction(action);
		setParent(parent);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
