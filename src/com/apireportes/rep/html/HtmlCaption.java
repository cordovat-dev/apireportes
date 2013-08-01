package com.apireportes.rep.html;

public class HtmlCaption extends HtmlElement {
	@SuppressWarnings("unused")
	private HtmlCaption(){
		super();
	}
	
	public HtmlCaption(String s_) {
		super(s_);
		psDelimI="<caption ";
		psDelimD="</caption>";		
	}

	@SuppressWarnings("unused")
	private HtmlCaption(HtmlElement e_){
		super(e_);		
	}
}
