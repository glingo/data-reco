package model.langage.xhtml;

public class XHTMLAttribut implements Comparable<XHTMLAttribut> {
	
	private String key;
	private String value;
	private String separator = "=";
	
	public XHTMLAttribut(String key, String value, String separator) {
		super();
		this.setKey(key);
		this.setSeparator(separator);
		this.setValue(value);
	}

	@Override
	public String toString() {
		return this.getKey() + this.getSeparator() + "'"+this.getValue()+"'";
	}
	
	@Override
	public int compareTo(XHTMLAttribut o) {
		
		if(this.getKey().equals(o.getKey())){
			return 0;
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			XHTMLAttribut objet = (XHTMLAttribut) obj;
			return this.getKey().equals(objet.getKey()) || this.getValue().equals(objet.getValue());
		}catch(Exception e){
			return false;
		}
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
