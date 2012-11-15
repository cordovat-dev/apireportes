package com.apireportes.rep.html;

public class HtmlNav extends HtmlElement {
	public HtmlNav(){
		super();
		psDelimI="<nav ";
		psDelimD="</nav>";
	}
	
	public HtmlNav(String s_) {
		super(s_);
		psDelimI="<nav ";
		psDelimD="</nav>";		
	}

	public HtmlNav(HtmlElement e_){
		super(e_);
		psDelimI="<nav ";
		psDelimD="</nav>";			
	}
}
