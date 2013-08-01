package com.apireportes.rep.html;

public class HtmlSpan extends HtmlElement {
	public HtmlSpan(){
		super();
		psDelimI="<span ";
		psDelimD="</span>";
	}
	
	public HtmlSpan(String s_) {
		super(s_);
		psDelimI="<span ";
		psDelimD="</span>";		
	}

	public HtmlSpan(HtmlElement e_){
		super(e_);
		psDelimI="<span ";
		psDelimD="</span>";			
	}
	
	public static HtmlSpan getInsConClase(String dato, String clase){
		HtmlSpan s = new HtmlSpan(dato);
		s.setClass(clase);
		return s;
	}
}
