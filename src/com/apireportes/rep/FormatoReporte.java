package com.apireportes.rep;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FormatoReporte {
    private boolean mostrarCeros = true;
    private boolean sustituirNLporBR = false;
    private boolean sustituirTABporEspacio = false;
    private boolean omitirTotalesColsNoNum = false;
    private boolean omitirCols = false;
    private boolean mostarLeyendaTotal = false;
    private boolean redondearTotales = false;
    private String leyendaTotal = "Totales";
    private String leyendaPromedios = "Promedios";

    private Locale lLocale = Locale.US;
    private String patronFormato = "#########0.0####";
    private String patronFormatoTotales = patronFormato;
    private String patronFormatoFecha = "dd/MM/yyyy";
	private NumberFormat numberFormat = NumberFormat.getNumberInstance(lLocale);
	private DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
	private DecimalFormat decimalFormatTotales = (DecimalFormat)numberFormat;
	private SimpleDateFormat fechaFormat = new SimpleDateFormat (patronFormatoFecha,lLocale);

    private String cadenaSustitutoNull = "";	
    private boolean sustituirNull = false;
    public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}

	private String separador = "\t";
    private final List<Integer> ivColsOmitir = new ArrayList<Integer>();
    private final List<Integer> ivFilasOmitirTotal = new ArrayList<Integer>();
    private final List<Integer> ivColsNumericas = new ArrayList<Integer>();
    private final List<Integer> ivColsOmitirTotal = new ArrayList<Integer>();
    public final Map<Integer,DecimalFormat> pmFormatosColExcepcionales 	= new HashMap<Integer,DecimalFormat>();
 	
	public boolean getRedondearTotales(){
		return redondearTotales;
	}
	
	public void setRedondearTotales(boolean opcion_){
		redondearTotales = opcion_;
	}
	
	public void setColsOmitirTotal(int[] colsOmitirTotal_){
        ivColsOmitirTotal.clear();
        for(int i = 0;i < colsOmitirTotal_.length;i++){
            ivColsOmitirTotal.add(new Integer(colsOmitirTotal_[i]));
        }
	}

	public void setOmitirTotColsNoNum(boolean opcion_){
		omitirTotalesColsNoNum = opcion_;
	}
	
    /**
     * Devuelve el nombre del mes segun el locale de la instancia de Reporte.
     * @see #setLocale(Locale locale_)
     * @see #getDescMes(int mes_, Locale locale_)
     */
	public String getDescMes(int mes_) {
		return getDescMes(mes_, lLocale);
	}
	
    /**
     * Devuelve el nombre del mes segun el locale especificado.
     * @see #setLocale(Locale locale_)
     * @see #getDescMes(int mes_)
     */
	public static String getDescMes(int mes_, Locale locale_) {
		if (mes_ >= 0 && mes_ <= 11) {
			return new DateFormatSymbols(locale_).getMonths()[mes_];
		} else {return "mes desconocido";}
	}	

    /**
     * Establece el Locale usado para generar las cadenas. Por defecto es US.
     * @see Locale getLocale()
     * @see String getFormatoNumeros()
     * @see #setFormatoNumeros(String formato_)
     */
	public void setLocale(Locale locale_){
		lLocale	 = locale_;
		numberFormat = NumberFormat.getNumberInstance(lLocale);
		decimalFormat = (DecimalFormat)numberFormat;
		decimalFormat.applyPattern(patronFormato);
	}
	
    /**
     * Establece el formato numerico usado para generar las cadenas de totales. Por defecto es "#########0.0####".
     * @see #setLocale(Locale locale_)
     * @see String setFormatoNumerosTotales()
     */
	public void setFormatoNumerosTotales(String formato_){
		patronFormatoTotales = formato_;
		decimalFormatTotales.applyPattern(patronFormatoTotales);
	}
	
    /**
     * Establece el formato numerico usado para generar las cadenas. Por defecto es "#########0.0####".
     * @see #setLocale(Locale locale_)
     * @see String getFormatoNumeros()
     */
	public void setFormatoNumeros(String formato_){
		//patronFormato = formato_;
		//decimalFormat.applyPattern(patronFormato);		

		patronFormato = formato_;
		numberFormat = NumberFormat.getNumberInstance(lLocale);
		decimalFormat = (DecimalFormat)numberFormat;		
		decimalFormat.applyPattern(patronFormato);		
		
	}	

    /*
     * Establece el formato de fechas usado para generar las cadenas. Por defecto es "dd/MM/yyyy".
     * @see #getLocale()
     * @see #setLocale(Locale locale_)
     * @see String getFormatoNumeros()
     * @see #setFormatoNumeros(String formato_)
     * @see #getFormatoFechas()
     */
	public void setFormatoFechas(String formato_){
		patronFormatoFecha = formato_;
		fechaFormat = new SimpleDateFormat (patronFormatoFecha,lLocale);
	}	

    /*
     * Retorna el formato de fechas usado para generar las cadenas.
     * @see #getLocale()
     * @see #setLocale(Locale locale_)
     * @see String getFormatoNumeros()
     * @see #setFormatoNumeros(String formato_)
     * @see #setFormatoFechas()
     */	
	public String getFormatoFechas(){
		return patronFormatoFecha;
	}
	
	public void setFormatoColEspecifica(int col_, String formato_){

		DecimalFormat formatoExcepcional = (DecimalFormat)numberFormat;
		formatoExcepcional = (DecimalFormat)formatoExcepcional.clone();
		formatoExcepcional.applyPattern(formato_);
		pmFormatosColExcepcionales.put(new Integer(col_),formatoExcepcional);
	}

    /**
     *  @see #getSustituirNull
     */
    public boolean getSustituirNull(){
        return sustituirNull;
    }	

    /**
     * Si se le pasa true el primer dato de la fila de totales es sustituido por una leyenda.
     * La leyenda predeterminada es "Totales"
     * @see #getMostrarLeyendaTotal()
     * @see #setMostrarLeyendaTotal(String leyenda)
     * @see #setMostrarLeyendaTotal(String leyendaTotales_, String leyendaPromedios_)
     */
    public void setMostrarLeyendaTotal(boolean opcion){
		mostarLeyendaTotal = opcion;
	}

    /**
     * Indica si esta activada o no la funcionalidad de mostrar una leyenda en la fila de los totales.
     * @see #setMostrarLeyendaTotal(boolean opcion)
     * @see #setMostrarLeyendaTotal(String leyenda)
	 * @see #setMostrarLeyendaTotal(String leyendaTotales_, String leyendaPromedios_)
     */
	public boolean getMostrarLeyendaTotal(){
		return mostarLeyendaTotal;
	}

    /**
     * Establece la leyenda que debera aparecer en la fila de totales, sustituyendo le leyenda predeterminada,
     * al mismo tiempo que activa la funcionalidad
     * @see #setMostrarLeyendaTotal(boolean opcion)
     * @see #getMostrarLeyendaTotal()
     * @see #setMostrarLeyendaTotal(String leyendaTotales_, String leyendaPromedios_)
     *
     */
    public void setMostrarLeyendaTotal(String leyenda){
		mostarLeyendaTotal = true;
		leyendaTotal = leyenda;
	}
    
    public String getLeyendaTotal(){
    	return leyendaTotal;
    }
    
    public String getLeyendaPromedios(){
    	return leyendaPromedios;
    }

    /**
     * Establece la leyenda que debera aparecer en la fila de totales y promedios, sustituyendo le leyenda predeterminada,
     * al mismo tiempo que activa la funcionalidad
     * @see #setMostrarLeyendaTotal(boolean opcion)
     * @see #setMostrarLeyendaTotal(String leyenda)
     * @see #getMostrarLeyendaTotal()
     *
     */
    public void setMostrarLeyendaTotal(String leyendaTotales_, String leyendaPromedios_){
		mostarLeyendaTotal = true;
		leyendaTotal = leyendaTotales_;
		leyendaPromedios = leyendaPromedios_;
	}	
    /**
     * Si se le pasa true, hace que getStrFilas y getStrFilasRS devuelvan una cadena de sustitucion en lugar de los valores nulos.
     * La cadena de sustitucion por defecto es "" (cadena vacia).
     *
     * @see #setSustituirNull(boolean opcion, String cadena)
     */
    public void setSustituirNull(boolean opcion){
        sustituirNull = opcion;
    }

    /**
     * Igual que setSustituirNull(boolean opcion) pero establece al mismo tiempo la cadena de sustitucion deseada.
     * @see #setSustituirNull(boolean opcion)
     */
    public void setSustituirNull(boolean opcion, String cadena){
        setSustituirNull(opcion);
        cadenaSustitutoNull = cadena;
    }
    
    public String getCadenaSustitutoNull(){
    	return cadenaSustitutoNull;
    }

    /**
     * Establece las columnas a omitir del resultado de getStrFilas o getStrFilasRS si se activa dicho comportamiento pasandole
     * true a setOmitirColumnas(boolean opcion) o setOmitirColumnas(boolean opcion, int[] colsOmitir)
     * @see #setOmitirColumnas(boolean opcion)
     * @see #setOmitirColumnas(boolean opcion, int[] colsOmitir)
     * @param colsOmitir por ejemplo new int[]{2,4} hace que se omitan las columnas 2 y cuatro del resultado de ejecutar un query
     *
     */
    public void setColsOmitir(int[] colsOmitir){
        ivColsOmitir.clear();
        for(int i = 0;i < colsOmitir.length;i++){
            ivColsOmitir.add(new Integer(colsOmitir[i]));
        }
       omitirCols = true;
    }

    /**
     * Si se le pasa true hace que al llamar getStrFilas o getStrFilasRS se omitan del resultado
     * las columnas pasadas en un arreglo de int mediante setColsOmitir(int[] colsOmitir) o setOmitirColumnas(boolean opcion, int[] colsOmitir
     * @see #setColsOmitir(int[] colsOmitir)
     * @see #setOmitirColumnas(boolean opcion, int[] colsOmitir)
     */
    public void setOmitirColumnas(boolean opcion){
        omitirCols = opcion;
    }

    public void setOmitirColumnas(boolean opcion, int[] colsOmitir){
        setOmitirColumnas(opcion);
        if (omitirCols) { setColsOmitir(colsOmitir); }
    }

	public void setFilasOmitirTotal(int[] filasOmitir_){
		ivFilasOmitirTotal.clear();
		for(int i = 0; i < filasOmitir_.length; i++){
			ivFilasOmitirTotal.add(new Integer(filasOmitir_[i]));
		}

	}
	
    public void setSustituirNLporBR(boolean opcion)
    {
        sustituirNLporBR = opcion;
    }
    /**
    * @see #setSustituirNLporBR
    * @return boolean
    */
    public boolean getSustituirNLporBR()
    {
        return sustituirNLporBR;
    }

    public void setSustituirTABporEspacio(boolean opcion)
    {
        sustituirTABporEspacio = opcion;
    }
    /**
    * @see #setSustituirTABporEspacio
    * @return boolean
    */
    public boolean getSustituirTABporEspacio()
    {
        return sustituirTABporEspacio;
    }
    
    /**
     * Si se activa hace que getStrFilas y getStrFilasRS devuelvan espacios en blanco en lugar de ceros. Por defecto esta activado.
 	*
     * @param opcion True para activar el comportamiento descrito o false para desactivarlo.
     */
     public void setMostrarCeros(boolean opcion)
     {
         mostrarCeros = opcion;
     }
     
     public boolean mostrarColumna(int columna_){
    	 return (!omitirCols || ivColsOmitir.indexOf(new Integer(columna_)) == -1);
     }
     
     public boolean columnaTieneFormatoExcepcional(int columna){
    	 return !(pmFormatosColExcepcionales.get(new Integer(columna)) == null);
     }
     
     public boolean columnaEsNumerica(int columna){
    	 return !(ivColsNumericas.indexOf(new Integer(columna)) == -1);
     }
     
     public boolean omitirColumnaTotales(int columna){
    	 return (omitirTotalesColsNoNum && !columnaEsNumerica(columna)) || (ivColsOmitirTotal.indexOf(new Integer(columna)) != -1);
     }
     
     public boolean omitirFilaTotales(int fila){
    	 return !(ivFilasOmitirTotal.indexOf(new Integer(fila)) == -1);
     }
     
     public void agregarColumnaNumerica(int columna){
    	 ivColsNumericas.add(new Integer(columna));
    	 
     }
     
     public void borrarColumnasNumericas(){
    	 ivColsNumericas.clear();
     }
     
     public boolean mostrarNumero(double numero){
    	 return (numero != 0 || (numero == 0 && mostrarCeros));
     }
     
 	public String getStrNumero(int col_, double numero){
 		String resultado;
        if (mostrarNumero(numero)) {
			if (columnaTieneFormatoExcepcional(col_)){
				DecimalFormat formatoExcepcional = pmFormatosColExcepcionales.get(new Integer(col_));
				resultado = formatoExcepcional.format(numero);				
			} else {
				resultado = decimalFormat.format(numero);
			}
        } else {
        	resultado = " ";
        }
        return resultado;
	}  	
 	
 	public String getStrNumeroTotales(double numero){
 		return getStrNumero(patronFormatoTotales,numero);
 		
 	}
 	
 	public String getStrFecha(java.util.Date fecha){
 		return fechaFormat.format(fecha);
 	}
 	
    /**
     * Se usa internamente para convertir un numero a String usando un formato
     * particular sil alterar el formato de la instancia
     *
     * @see #getLocale
     * @see #setLocale(Locale locale_)
     * @see #getDecimalFormat
     * @see #getFormatoNumeros
     * @see #setFormatoNumeros(String formato_)
     * @see #setFormatoColEspecifica(int col_, String formato_)
     * @see #getStrNumero(double numero)
     * @see #getStrNumero(String formato_, double numero_)
     * @param formato_ el formato que se desea aplicar en la operacion
     * @param numero_ el numero que se desea convertir a String
     */
 	protected String getStrNumero(String formato_, double numero_)
 	{
 		// se "arma" un DecimalFormat local a este metodo para
 		// no alterar el de la instancia

 		NumberFormat nf = NumberFormat.getNumberInstance(lLocale);
 		DecimalFormat df = (DecimalFormat)nf;
 		df.applyPattern(formato_); // se le aplica el formato solicitado
 		String resultado;

         // todo borrar if (numero_ != 0 || (numero_ == 0 && formato.getMostrarCeros())) {
 		if (mostrarNumero(numero_)){
 			resultado = df.format(numero_); //se usa el DecimalFormat local para convertir el numero a String
         } else {
        	 resultado =  " ";
         }
 		
 		return resultado;
 	} 	

}
