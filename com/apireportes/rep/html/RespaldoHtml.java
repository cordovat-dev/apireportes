package com.apireportes.rep.html;

public class RespaldoHtml extends HtmlElement {
	public RespaldoHtml(){
		super();
		psDelimI="<html><head><LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"estilo.css\"></head><body ";
		psDelimD="</body></html>";
	}
	
	public RespaldoHtml(HtmlElement e_){
		super(e_);
		psDelimI="<html><head><LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"estilo.css\"></head><body ";
		psDelimD="</body></html>";			
	}	
	
}
