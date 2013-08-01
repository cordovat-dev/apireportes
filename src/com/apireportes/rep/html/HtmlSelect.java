package com.apireportes.rep.html;

public class HtmlSelect extends HtmlElement {
	public HtmlSelect(){
		super();
		psDelimI="<select ";
		psDelimD="</select>";
	}
	
	public HtmlSelect(String s_) {
		super(s_);
		psDelimI="<select ";
		psDelimD="</select>";		
	}

	public HtmlSelect(HtmlElement e_){
		super(e_);
		psDelimI="<select ";
		psDelimD="</select>";			
	}
	
	public void setSelected(String v_){
		for(HtmlElement he: this.getElementos()){
			if (he instanceof HtmlOption){
				HtmlOption ho = (HtmlOption)he;				
				ho.setSelected((ho.getValue().equals(v_)));
				System.out.println(ho.getValue().equals(v_));

			}
		}
	}
	protected void finalize(){
		for (int i = 0; i < 50; i++){
			System.out.println("bye");
		}
		
	}		
}
