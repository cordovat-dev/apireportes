package com.apireportes.rep.html;

public class HtmlTD extends HtmlElement{
	public HtmlTD(){
		super();
		psDelimI="<td ";
		psDelimD="</td>";
	}
	
	public HtmlTD(String s_) {
		super(s_);
		psDelimI="<td ";
		psDelimD="</td>";		
	}

	public HtmlTD(HtmlElement e_){
		super(e_);
		psDelimI="<td ";
		psDelimD="</td>";			
	}
}
