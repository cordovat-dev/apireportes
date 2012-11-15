package com.apireportes.rep.html;

public class HtmlLink extends HtmlElement {
	public HtmlLink(){
		super();
		psDelimI="<link ";
		psDelimD="";
	}
	
	public HtmlLink(String s_) {
		super(s_);
		psDelimI="<link ";
		psDelimD="";		
	}

	public HtmlLink(HtmlElement e_){
		super(e_);
		psDelimI="<link ";
		psDelimD="";		
	}
}
