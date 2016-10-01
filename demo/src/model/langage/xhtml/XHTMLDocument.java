package model.langage.xhtml;

import java.util.Date;

import model.langage.Document;

public class XHTMLDocument extends Document {
	
	private XHTMLElementList elements;
	
	public XHTMLDocument() {
		super();
	}
	
	public XHTMLDocument(String auteur, String chemin, String titre, Date xdate, XHTMLElementList elements) {
		super(auteur, chemin, titre, xdate);
		setElements(elements);
	}

	public XHTMLDocument(String auteur, String chemin, String titre, XHTMLElementList elements) {
		super(auteur, chemin, titre);
		setElements(elements);
	}

	public XHTMLDocument(String chemin, XHTMLElementList elements) {
		super(chemin);
		setElements(elements);
	}

	public XHTMLDocument(XHTMLElementList elements) {
		super();
		setElements(elements);
	}
	
	public void addElement(XHTMLElement element) {
		if(elements == null){
			elements = new XHTMLElementList();
		}
		this.getElements().add(element);
	}
	
	public XHTMLElementList getElements() {
		return elements;
	}

	public void setElements(XHTMLElementList elements) {
		this.elements = elements;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if(this.getElements() != null) buffer.append(this.getElements());
		return buffer.toString();
	}
	
}
