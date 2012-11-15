package com.apireportes.rep.html;

public class HtmlOption extends HtmlElement {
	private String value="";
	private boolean selected=false;
	public HtmlOption(){
		super();
		psDelimI="<option ";
		psDelimD="</option>";
	}
	
	public HtmlOption(String s_) {
		super(s_);
		psDelimI="<option ";
		psDelimD="</option>";
		value=s_;
		this.setAtributo("value", s_);
	}
	
	public HtmlOption(String s_, String v_) {
		super(s_);
		psDelimI="<option ";
		psDelimD="</option>";
		value=v_;
		this.setAtributo("value", v_);
	}	

	public HtmlOption(HtmlElement e_){
		super(e_);
		psDelimI="<option ";
		psDelimD="</option>";			
	}

	public String getValue() {
		return value;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (this.selected) {
			this.setAtributo("selected", "1");
		}
	}
	
	public boolean equals(Object o){
		boolean result=false;
		
		if (this == o){
			result = true;
		} else {
			if((o != null) && (o.getClass() == this.getClass())){
				HtmlOption ho = (HtmlOption)o;
				result = this.getValue().equals(ho.getValue());
			}
		}
		
		return result;
	}
	
	public int hashCode(){
		return this.getValue().hashCode();
	}
	
}
