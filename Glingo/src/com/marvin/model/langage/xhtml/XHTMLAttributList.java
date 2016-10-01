package com.marvin.model.langage.xhtml;

import java.util.ArrayList;
import java.util.Iterator;

public class XHTMLAttributList extends ArrayList<XHTMLAttribut> {

	private static final long serialVersionUID = 6070002789821610878L;
	
	public XHTMLAttributList() {
		super();
	}

	@Override
	public String toString() {
		String build = "";
		for (Iterator<XHTMLAttribut> iterator = this.iterator(); iterator.hasNext();) {
			XHTMLAttribut attr = (XHTMLAttribut) iterator.next();
			build += attr.toString();
			if(iterator.hasNext()){
				build += " ";
			}
		}
		return build;
	}
	
	@Override
	public boolean add(XHTMLAttribut add) {
		if(this.contains(add)){
			XHTMLAttribut attr = this.get(this.indexOf(add));
			add.setValue(add.getValue() + " " + attr.getValue());
			this.remove(attr);
		}
		return super.add(add);
	}
}
