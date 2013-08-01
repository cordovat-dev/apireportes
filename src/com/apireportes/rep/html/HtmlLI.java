package com.apireportes.rep.html;

public class HtmlLI extends HtmlElement {
	public HtmlLI(){
		super();
		psDelimI="<li ";
		psDelimD="</li>";
	}
	
	public HtmlLI(String s_) {
		super(s_);
		psDelimI="<li ";
		psDelimD="</li>";		
	}

	public HtmlLI(HtmlElement e_){
		super(e_);
		psDelimI="<li ";
		psDelimD="</li>";			
	}
}
