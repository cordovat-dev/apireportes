package com.apireportes.rep.html;

public class HtmlBr extends HtmlElement {
	public HtmlBr(){
		super();
		psDelimI="<br ";
		psDelimD="";
	}
	
	public HtmlBr(String s_) {
		super("");
		psDelimI="<br ";
		psDelimD="";	
	}

	public HtmlBr(HtmlElement e_){
		super("");
		psDelimI="<br ";
		psDelimD="";		
	}	

}
