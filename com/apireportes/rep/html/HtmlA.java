package com.apireportes.rep.html;

public class HtmlA extends HtmlElement {
	public HtmlA(){
		super();
		psDelimI="<a ";
		psDelimD="</a>";
	}
	
	public HtmlA(String s_) {
		super(s_);
		psDelimI="<a ";
		psDelimD="</a>";		
	}

	public HtmlA(HtmlElement e_){
		super(e_);
		psDelimI="<a ";
		psDelimD="</a>";			
	}
}
