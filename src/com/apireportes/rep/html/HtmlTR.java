package com.apireportes.rep.html;

public class HtmlTR extends HtmlElement {
	
	public HtmlTR(){
		super();
		psDelimI="<tr ";
		psDelimD="</tr>";
	}
	public HtmlTR(HtmlElement e_){
		super(e_);
		psDelimI="<tr ";
		psDelimD="</tr>";		
	}	
}
