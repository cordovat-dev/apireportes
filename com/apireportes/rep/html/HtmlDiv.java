package com.apireportes.rep.html;

public class HtmlDiv extends HtmlElement{
	public HtmlDiv(){
		super();
		psDelimI="<div ";
		psDelimD="</div>";
	}
	
	public HtmlDiv(String s_) {
		super(s_);
		psDelimI="<div ";
		psDelimD="</div>";		
	}

	public HtmlDiv(HtmlElement e_){
		super(e_);
		psDelimI="<div ";
		psDelimD="</div>";			
	}
}
