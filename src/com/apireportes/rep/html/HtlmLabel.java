package com.apireportes.rep.html;

public class HtlmLabel extends HtmlElement {
	public HtlmLabel(){
		super();
		psDelimI="<label ";
		psDelimD="</label>";
	}
	
	public HtlmLabel(String s_) {
		super(s_);
		psDelimI="<label ";
		psDelimD="</label>";	
	}

	public HtlmLabel(HtmlElement e_){
		super(e_);
		psDelimI="<label ";
		psDelimD="</label>";	
	}
	
	public HtlmLabel(String s_, String for_){
		super(s_);
		psDelimI="<label ";
		psDelimD="</label>";	
		this.setAtributo("for", for_);		
	}
	
}
