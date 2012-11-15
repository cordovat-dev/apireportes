package com.apireportes.rep.html;

public class HtmlBody extends HtmlElement {

	public HtmlBody(){
		super();
		psDelimI="<body ";
		psDelimD="</body>";
	}
	
	public HtmlBody(String s_) {
		super(s_);
		psDelimI="<body ";
		psDelimD="</body>";		
	}

	public HtmlBody(HtmlElement e_){
		super(e_);
		psDelimI="<body ";
		psDelimD="</body>";			
	}

}
