package com.marvin.bundles.menu.ressources.components;

import java.util.ArrayList;

public class Nav extends ArrayList<NavListItem> {
	
	/**	 */
	private static final long serialVersionUID = 1L;

	public Nav() {
		super();
	}

	public Nav(NavListItem... items) {
		super();
		if(items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {
				this.add(items[i]);
			}
		}
	};
}
