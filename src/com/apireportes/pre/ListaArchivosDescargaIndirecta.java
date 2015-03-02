package com.apireportes.pre;

import java.io.File;

public class ListaArchivosDescargaIndirecta extends ListaArchivosDescarga {
	
	public ListaArchivosDescargaIndirecta(File ruta){
		super(ruta, "");
	}
	public ListaArchivosDescargaIndirecta(File ruta,String url_) {
		super(ruta,url_);
	}
	
	protected String getArmarLink(String archivo){
		return this.url+"procesar.jsp?item="+archivo;
	}	

}
