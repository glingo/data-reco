package com.marvin.model.langage.xhtml;

import java.util.Iterator;

public class XHTMLElement {

	private String nom;
	private boolean isSelfDefined = false;
	private XHTMLAttributList attributs = new XHTMLAttributList();
	private XHTMLElementList children = new XHTMLElementList();
	
	public XHTMLElement() {
		super();
	}
	
	public XHTMLElement(String nom) {
		super();
		this.setNom(nom);
		this.setSelfDefined(false);
	}
	
	public XHTMLElement(String nom, boolean selfClosed) {
		super();
		this.setNom(nom);
		this.setSelfDefined(selfClosed);
	}
	
	public XHTMLElement(String nom, XHTMLAttributList attributs) {
		super();
		this.setNom(nom);
		this.setAttributs(attributs);
		this.setSelfDefined(false);
	}

	public XHTMLElement(String nom, XHTMLAttributList attributs, boolean selfClosed) {
		super();
		this.nom = nom;
		this.setAttributs(attributs);
		this.setSelfDefined(selfClosed);
	}
	
	public XHTMLElement(String nom, XHTMLAttributList attributs, XHTMLElementList children) {
		super();
		this.setNom(nom);
		this.setAttributs(attributs);
		this.setChildren(children);
		this.setSelfDefined(false);
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public XHTMLAttributList getAttributs() {
		return attributs;
	}

	public void setAttributs(XHTMLAttributList attributs) {
		this.attributs = attributs;
	}

	public XHTMLElement find(XHTMLAttribut attr) {
		for (Iterator<XHTMLElement> iterator = this.getChildren().iterator(); iterator.hasNext();) {
			XHTMLElement child = (XHTMLElement) iterator.next();
			if(child.getAttributs().contains(attr)){
				System.out.println("trouve");
			}
		}
		return null;
	}
	
	public XHTMLElementList getChildren() {
		return children;
	}

	public void setChildren(XHTMLElementList children) {
		this.children = children;
	}
	
	public void addChild(XHTMLElement child) {
		if(children == null){
			children = new XHTMLElementList();
		}
		this.getChildren().add(child);
	}

	public boolean isSelfDefined() {
		return isSelfDefined;
	}

	public void setSelfDefined(boolean selfClosed) {
		this.isSelfDefined = selfClosed;
	}
	
	@Override
	public String toString() {
		String build = "<$name $params";
		build = build.replace("$name", this.getNom());
		build = build.replace("$params", this.getAttributs().toString());
		if(this.isSelfDefined()){
			build = build.trim() + "/>";
		} else {
			build = build.trim() + ">";
			
			if(this.getChildren() != null && !this.getChildren().isEmpty())
				build += this.getChildren().toString();
			
			build += "</$name>".replace("$name", this.getNom());
		}
		
		return build;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new XHTMLElement(nom, attributs, children);
	}
}
