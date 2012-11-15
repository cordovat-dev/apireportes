package com.apireportes.rep.html;

public class HtmlTH extends HtmlElement {
	public HtmlTH(){
		super();
		psDelimI="<th ";
		psDelimD="</th>";
	}

	public HtmlTH(String s_) {
		super(s_);
		psDelimI="<th ";
		psDelimD="</th>";		
	}
	
	public HtmlTH(HtmlElement e_){
		super(e_);
		psDelimI="<th ";
		psDelimD="</th>";		
	}
}
