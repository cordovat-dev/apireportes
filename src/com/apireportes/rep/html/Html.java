package com.apireportes.rep.html;

public class Html extends HtmlElement {
	private HtmlElement head;
	private HtmlBody body;
	private HtmlLink estilo=null;
	public Html(){
		super();
		psDelimI="<html ";
		psDelimD="</html>";
		armar();
	}
	
	public Html(HtmlElement e_){
		super(e_);
		psDelimI="<html ";
		psDelimD="</html>";
		armar();
	}	
	
	private void armar(){
		head = new HtmlHead();
		estilo = new HtmlLink();
		estilo.setAtributo("REL", "stylesheet");
		estilo.setAtributo("TYPE","text/css");
		estilo.setAtributo("HREF", "estilo.css");				
		body = new HtmlBody();
		head.add(estilo);
		
		this.add(head);
		this.add(body);		
	}

	public void addToHead(HtmlElement e_){
		e_.incIdentacion();
		head.add(e_);
	}
	
	public void addToBody(HtmlElement e_){
		e_.incIdentacion();
		body.add(e_);
	}
	
	public void setArchivoEstilos(String archivo_){
		estilo.setAtributo("HREF", archivo_);		
	}
}
