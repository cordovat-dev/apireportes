package com.apireportes.rep.html;

import java.util.*;

/* 23/08/2010, CORDOVATJ se corrigio error que ocasionaba que se omitiera el contenido plano de un elemento si este tenia otros 
 * 						 elementos HMTL
 * 
 */
public class HtmlElement {
	
	private final List<HtmlElement> palElementos = new ArrayList<HtmlElement>();
	private final Map<String,HtmlAttribute> pmAtributos = new HashMap<String,HtmlAttribute>();
	protected String psDelimI="<E ";
	protected String psDelimD="</E>";
	private String psDato="";
	private int piIdentacion = 0;
	private String psIdentacion = "";
	private static final String CADENA_INDENTACION = "  ";
	private String psLink = "";
	private String psLinkTitle = "";
	
	public HtmlElement(){
		// constructor vacio
	}

	public HtmlElement(HtmlElement e_){
		add(e_);
	}	
	
	public HtmlElement(String dato_){
		
		psDato = dato_;
	}
	
	public List<HtmlElement> getElementos(){
		return palElementos;
	}
	
	public void setDato(String d_){
		psDato = d_;
	}	
	
	protected void incIdentacion(){
		piIdentacion++;
		psIdentacion = rellenar("",piIdentacion);
		for(HtmlElement e: palElementos){
			e.incIdentacion();
		}		
	}
	
	public final void add(HtmlElement e_){
		e_.incIdentacion();
		palElementos.add(e_);
	}
	
	public final void add(int indice_,HtmlElement e_){
		e_.incIdentacion();
		palElementos.add(indice_,e_);
	}	
	
	private static String rellenar(String cadena_,int longitud){
		  String s = "";
		  for (int i=1;i < longitud - cadena_.length() + 1;i++){
				s = s + CADENA_INDENTACION;
		  }
		  return s + cadena_;
	}	
	
	public String getHtml(){
		String lsCadenaElementos = psDato;

		lsCadenaElementos = armarCadenaElementos(lsCadenaElementos);	
		
		// procedo a armar la cadena de atributos
		
		String lsCadenaAtributos="";
	
		lsCadenaAtributos = armarCadenaAtributos(lsCadenaAtributos);
		
		// fin del armado de la cadena de atributos
		
		String lsIdentacionCierre;
		
		if (palElementos.isEmpty()){
			lsIdentacionCierre = "";
			lsCadenaElementos = armarLinkHtml(lsCadenaElementos);			
		} else {
			lsIdentacionCierre = psIdentacion;
		}
		
		String lsCadenaFinal = "\n" + psIdentacion + psDelimI + lsCadenaAtributos + ">" + lsCadenaElementos + lsIdentacionCierre + psDelimD + "\n";
		lsCadenaFinal = lsCadenaFinal.replaceAll("\n\n", "\n");
		
		return lsCadenaFinal.replaceAll(" >", ">");
	}

	private String armarLinkHtml(String lsCadenaElementos) {
		boolean esLink = !"".equals(psLink);
		boolean hayDato = !"".equals(psDato);
		if (esLink && hayDato){
			lsCadenaElementos = "<a href='" + psLink + "' title='" +psLinkTitle+ "'>" + lsCadenaElementos + "</a>";
		}
		return lsCadenaElementos;
	}

	private String armarCadenaAtributos(String lsCadenaAtributos) {
		for (HtmlAttribute a: pmAtributos.values()){
			lsCadenaAtributos += a.getHtml() + " ";
		}
		return lsCadenaAtributos;
	}

	private String armarCadenaElementos(String lsCadenaElementos) {
		for(HtmlElement e: palElementos){ 
			lsCadenaElementos += e.getHtml();
		}
		return lsCadenaElementos;
	}
	
	public void setAtributo(String a_, String v_){
		pmAtributos.put(a_, new HtmlAttribute(a_,v_));
	}
	
	public void setClass(String class_){
		setAtributo("class", class_);
	}
	
	public void setClass(int fila_, int col_, String class_){
		setAtributo(fila_, col_, "class", class_);
	}
	
	public void setLink(String link_){
		psLink = link_;		
	}
	
	public void setLink(String link_, String title_){
		psLink = link_;		
		psLinkTitle = title_;
	}	

	public void setLink(int fila_, int col_, String link_){
		HtmlElement el; 
		try {
			el = palElementos.get(fila_);
			el = el.getElementos().get(col_);
			el.setLink(link_);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		
		}
	}
	
	public HtmlAttribute getAtributo(String a_){		
		return pmAtributos.get(a_);
	}
	
	public void setAtributo(int fila_, int col_, String a_, String v_){
		HtmlElement el; 
		try {
			el = palElementos.get(fila_);
			el = el.getElementos().get(col_);
			el.setAtributo(a_, v_);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return this.getHtml();
	}
	
	
}
