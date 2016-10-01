package com.marvin.model.langage.xhtml;

import java.util.ArrayList;
import java.util.Iterator;

public class XHTMLElementList extends ArrayList<XHTMLElement> {

	public XHTMLElementList() {
		super();
	}

	private static final long serialVersionUID = 6070002789821610878L;

	@Override
	public String toString() {
		String build = "";
		for (Iterator<XHTMLElement> iterator = this.iterator(); iterator.hasNext();) {
			XHTMLElement el = (XHTMLElement) iterator.next();
			build += el.toString();
			if(iterator.hasNext()){
				build += " ";
			}
		}
		return build;
	}
}