package com.apireportes.pre;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apireportes.rep.html.HtmlA;
import com.apireportes.rep.html.HtmlContent;
import com.apireportes.rep.html.HtmlLI;
import com.apireportes.rep.html.HtmlUL;

public class ListaArchivosDescarga {
	
	private HtmlUL thisUL;
	private boolean hayArchivos = false;
	public static long BYTES_POR_KB = 1024;	
	public static long BYTES_POR_MB = BYTES_POR_KB * BYTES_POR_KB;
	public static long BYTES_POR_GB = BYTES_POR_MB * BYTES_POR_KB;
	String url="";
	public ListaArchivosDescarga(File ruta){
		this(ruta, "");
	}
	public ListaArchivosDescarga(File ruta,String url_){
		this.url=limpiarURL(url_);
		File f = ruta;

		File [] lista = f.listFiles();
		Arrays.sort(lista, new ComparatorNombreArchivos());

		hayArchivos=false;

		if ( lista != null) {	

			if (lista.length > 0) {
				
				HtmlUL ul = new HtmlUL();
				HtmlLI li;
				HtmlA a;
				for (File arc: lista){
					boolean esArchivoNormal = arc.isFile() && !arc.isHidden();
					if(esArchivoNormal){
						li = new HtmlLI();
						a = new HtmlA(arc.getName());
						a.setAtributo("href",this.getArmarLink(arc.getName()));
						li.add(a);
						li.add(new HtmlContent(" &nbsp;&nbsp;( " + ListaArchivosDescarga.tamanoAmigable(arc.length())+" )"));
						li.setAtributo("class", "arch_"+this.extraerExtension(arc.getName()));
						ul.add(li);						
						hayArchivos = true;			
					}

				}
				
				thisUL = ul;
			}
		}		
	}
	
	private String limpiarURL(String url_) {
		return url_.substring(0,url_.lastIndexOf('/')+1);
	}

	protected String getArmarLink(String archivo){
		return "archivos/"+archivo;
	}
	
	private String extraerExtension(String nombre){
		String extension;
		Pattern p = Pattern.compile("\\.([^\\.]*)$");
		Matcher m = p.matcher(nombre);
		m.find();
		try {
			extension = m.group(1);
		} catch ( java.lang.IllegalStateException e){
			extension = ".dat";
		}
		return extension;
	}

	public HtmlUL getUL() {
		return thisUL;
	}

	public boolean hayArchivos() {
		return hayArchivos;
	}
	
	public int cantArchivos(){
		int c = 0;
		if (this.hayArchivos){
			c = this.getUL().getElementos().size();
		}
		return c;
	}
	
	public static String tamanoAmigable(long bytes){
		Locale.setDefault(new Locale("en","US"));
		String s="";
		double bytesDouble = bytes;
		if (bytes >= BYTES_POR_GB ){
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			s = Double.valueOf(twoDForm.format(bytesDouble / BYTES_POR_GB)) + " GB";
		} else if (bytes >= BYTES_POR_MB){
			s = Math.round(bytesDouble / BYTES_POR_MB) + " MB";
		} else if (bytes >= BYTES_POR_KB){
			s = Math.round(bytesDouble /BYTES_POR_KB) + " KB";
		} else {
			s = (bytes) + " Bytes";
		}
		
		return s;
	}

}

