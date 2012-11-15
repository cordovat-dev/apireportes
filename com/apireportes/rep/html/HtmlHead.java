package com.apireportes.rep.html;

public class HtmlHead extends HtmlElement {
	public HtmlHead(){
		super();
		psDelimI="<head ";
		psDelimD="</head>";
	}
	
	public HtmlHead(String s_) {
		super(s_);
		psDelimI="<head ";
		psDelimD="</head>";		
	}

	public HtmlHead(HtmlElement e_){
		super(e_);
		psDelimI="<head ";
		psDelimD="</head>";			
	}
}
