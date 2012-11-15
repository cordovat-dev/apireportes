package com.apireportes.rep.html;

public class HtmlTable extends HtmlElement{
	
	private boolean ultimaFilaTotales = false;
	private String caption="";
	
	public HtmlTable(){
		super();
		psDelimI="<table ";
		psDelimD="</table>";
		setAtributo("border","1");
	}
	
	public HtmlTable(String s_){
		String lsFilas[] = s_.split("\n");
		HtmlTR tr;
		HtmlElement td;
		for (int i = 0; i < lsFilas.length; i++){
			String lsTokens[] = lsFilas[i].split("\t");
			tr = new HtmlTR();
			for (int j = 0; j < lsTokens.length; j++){
				if ( i == 0 || ((i == lsFilas.length - 1) && (ultimaFilaTotales))){
					td = new HtmlTH(lsTokens[j]);
				} else {
					td = new HtmlTD(lsTokens[j]);
				}
				if (i % 2 == 0){
					tr.setAtributo("class","COLOR");
				}
				
				//td.setAtributo("class", "DERECHA");
				tr.add(td);
			}
			add(tr);
		}
		psDelimI="<table ";
		psDelimD="</table>";		
	}
	public HtmlTable(HtmlElement e_){
		super(e_);
		psDelimI="<table ";
		psDelimD="</table>";		
	}

	public boolean isUltimaFilaTotales() {
		return ultimaFilaTotales;
	}

	public void setUltimaFilaTotales(boolean ultimaFilaTotales) {
		this.ultimaFilaTotales = ultimaFilaTotales;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption_) {
		this.caption = caption_;
		this.add(0,new HtmlCaption(caption_));
	}
	
}
