package com.apireportes.rep.html;

public class HtmlUL extends HtmlElement{
	public HtmlUL(){
		super();
		psDelimI="<ul ";
		psDelimD="</ul>";
	}
	
	public HtmlUL(String s_) {
		super(s_);
		psDelimI="<ul ";
		psDelimD="</ul>";		
	}

	public HtmlUL(HtmlElement e_){
		super(e_);
		psDelimI="<ul ";
		psDelimD="</ul>";			
	}
	
	public void addAsLi(HtmlElement e_){
		this.add(new HtmlLI(e_));
	}
	
	public void addAsLi(String dato_){
		this.add(new HtmlLI(new HtmlSpan(dato_)));
	}	
}
