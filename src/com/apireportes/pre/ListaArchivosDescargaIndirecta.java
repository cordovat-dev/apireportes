package com.apireportes.pre;

import java.io.File;

public class ListaArchivosDescargaIndirecta extends ListaArchivosDescarga {

	public ListaArchivosDescargaIndirecta(File ruta) {
		super(ruta);
	}
	
	protected String getArmarLink(String archivo){
		return "procesar.jsp?item="+archivo;
	}	

}
