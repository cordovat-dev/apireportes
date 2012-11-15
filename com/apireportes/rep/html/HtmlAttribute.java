package com.apireportes.rep.html;

public class HtmlAttribute {
	private String psNombreAttr = "";
	private String psValorAttr = "";
	
	public HtmlAttribute(String n_, String v_){
		psNombreAttr = n_;
		psValorAttr = v_;
	}
	public String getNombreAttr(){return psNombreAttr;}
	public String getValorAttr(){return psValorAttr;}
	public void setNombreAttr(String s_){ psNombreAttr = s_;}
	public void setValorAttr(String s_){ psValorAttr = s_;}	
	
	public String getHtml(){ 
		String s = "";
		if (!"".equals(psValorAttr)){
			s = psNombreAttr + "=\"" + psValorAttr +"\"";
		} 
		return s;
	}
}
