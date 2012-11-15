package com.apireportes.rep.html;

public class HtmlForm extends HtmlElement {
	public HtmlForm(){
		super();
		psDelimI="<form ";
		psDelimD="</form>";
	}
	
	public HtmlForm(String s_) {
		super(s_);
		psDelimI="<form ";
		psDelimD="</form>";		
	}

	public HtmlForm(HtmlElement e_){
		super(e_);
		psDelimI="<form ";
		psDelimD="</form>";				
	}
}
