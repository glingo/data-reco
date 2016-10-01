package com.glingo.bundles.menu.model;

import java.util.ArrayList;

public class NavListItem extends ArrayList<NavItem> {

	/** Serial. */
	private static final long serialVersionUID = 6066880556596910649L;
	
	private String label;
	
	public NavListItem(String label) {
		super();
		this.label = label;
	};

	public NavListItem(String label, NavItem... items) {
		super();
		this.label = label;
		if(items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {
				this.add(items[i]);
			}
		}
	};
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	};
	
}
