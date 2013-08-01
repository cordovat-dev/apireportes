package com.apireportes.rep.html;

public class HtmlImg extends HtmlElement {
	public HtmlImg(){
		super();
		psDelimI="<img ";
		psDelimD="";
	}
	
	public HtmlImg(String s_) {
		super("");
		psDelimI="<img ";
		psDelimD="";
		this.setAtributo("src", s_);
	}

	@SuppressWarnings("unused")
	private HtmlImg(HtmlElement e_){
		super(e_);
		psDelimI="<img ";
		psDelimD="";			
	}
	
	public static void main (String args[]){
		HtmlImg img = new HtmlImg("img/critica.jpg");
		img.setAtributo("height","16");
		img.setAtributo("width","16");
		System.out.println(img.getHtml());
	}
}
