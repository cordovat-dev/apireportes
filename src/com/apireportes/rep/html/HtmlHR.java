package com.apireportes.rep.html;

public class HtmlHR extends HtmlElement{
	public HtmlHR(){
		super();
		psDelimI="<hr ";
		psDelimD="";
	}
	
	public HtmlHR(String s_) {
		super(s_);
		psDelimI="<hr ";
		psDelimD="";		
	}

	public HtmlHR(HtmlElement e_){
		super(e_);
		psDelimI="<hr ";
		psDelimD="";			
	}
}
