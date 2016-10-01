package com.marvin.model.langage.xhtml;

public abstract class XHTMLText extends XHTMLElement {

	private String value;
	
	public XHTMLText(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
