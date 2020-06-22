package com.apireportes.rep.html;

public class HtmlImg extends HtmlElement {
	public HtmlImg(){
		super();
		psDelimI="<img ";
		psDelimD="";
	}
	
	public HtmlImg(String s_) {
		super("");
		psDelimI="<img ";
		psDelimD="";
		this.setAtributo("src", s_);
	}

	@SuppressWarnings("unused")
	private HtmlImg(HtmlElement e_){
		super(e_);
		psDelimI="<img ";
		psDelimD="";			
	}
	
}
