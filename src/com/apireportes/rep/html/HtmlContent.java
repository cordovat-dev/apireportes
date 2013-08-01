package com.apireportes.rep.html;

public class HtmlContent extends HtmlElement {
	public HtmlContent(){
		super();
		psDelimI="<span ";
		psDelimD="</span>";
	}
	
	public HtmlContent(String s_) {
		super(s_);
		psDelimI="<span ";
		psDelimD="</span>";		
	}

	public HtmlContent(HtmlElement e_){
		super(e_);
		psDelimI="<span ";
		psDelimD="</span>";			
	}
}
