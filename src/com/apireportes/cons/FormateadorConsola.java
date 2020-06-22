package com.apireportes.cons;



import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
//import com.gesgas.gg.ArchivoAbrevsNoEncontradoException;
import java.io.*;

/**
* Esta formatea una salida tabular de Reporte para su correcta presentaci�n por c�nsola
* <h4>Historia de modificaciones</h4>
*     12/01/2010 CORDOVATJ, se elimin� l�nea en blanco adicional entre los caption de los reportes y el reporte mismo, por alguna raz�n esa l�nea adicional no se veia
*                           al pegar dos reportes uno al lado del otro ( se eliminaba )
* <br>29/06/2009 CORDOVATJ, nuevo constructor que recibe el nombre del archivo de abreviaturas
* <br>30/03/2009 CORDOVATJ, se maneja el caso de columnas vacias ( en m�todo limpiaSalida ), lo cual corrige error de desfase de columnas
*                       de totales cuando se omite el total para una columna
* <br>30/03/2009 CORDOVATJ, se corrigi� error de c�lculo de las anchuras cuando el dato contiene &nbsp;
* @author Tulains C�rdova
* @version 1
*/

public class FormateadorConsola{

	private int 	piSepAdicional 	= 2; // relleno adicional que llevaran las columnas para que no se peguen de la anterior
	private String 	psSeparadorTitulos 		= "-"; // raya que separa los encabezados del cuerpo
	private String 	psSep 					= "\t";
	private String 	psSepFinLinea 			= "\n";
	private boolean	pbSubsAcentos 			= true;
	private boolean pbPrimFilaTitulos 		= true;
	private boolean pbAbrevTitulos			= false;
	private boolean pbCentrarTitulos		= false;
	private boolean pbCentrarTodo			= false;
	private boolean pbSepTotales			= false;
	private String	psPuntoAbrev			= ".";
	private	String	psRelleno				= " ";
	private int		piSepEntreReportes		= 3;
	private Map<String,String> 	pmAbrevs	= new HashMap<String,String>();
	private String	nomArchAbrevs;
	//private Map 	pmAnchosCols  			= new HashMap(); //Tanques
	//private String 	psCadena 				= "";
	//private int 	piAnchoTotal 			= 0;
	//private int 	piNumCols 				= 0;
	//private int 	piNumFilas 				= 0;

	/*
	* gets y sets simples
	*/
	public int 		getSeparacionAdicional()				{ return piSepAdicional;}
	public void 	setSeparacionAdicional(int margen_)		{ piSepAdicional = margen_;/*calculaAnchoTotal()*/;}

	public String 	getSeparadorTitulos()					{ return psSeparadorTitulos;}
	public void 	setSeparadorTitulos(char separador_)	{ psSeparadorTitulos = new Character(separador_).toString();}

	public String 	getSeparador()							{ return psSep;}
	public void 	setSeparador(char separador_)			{ psSep = new Character(separador_).toString();}

	public String 	getSeparadorFinLinea()					{ return psSepFinLinea;}
	public void 	setSeparadorFinLinea(char char_)		{ psSepFinLinea = new Character(char_).toString();}

	public boolean 	getSubstituirAcentos()					{ return pbSubsAcentos;}
	public void 	setSubstituirAcentos(boolean valor_)	{ pbSubsAcentos = valor_;}

	public boolean 	getPrimeraFilaTitulos()					{ return pbPrimFilaTitulos; }
	public void 	setPrimeraFilaTitulos(boolean valor_)	{ pbPrimFilaTitulos = valor_; }

	public boolean 	getAbreviarTitulos()					{ return pbAbrevTitulos; }
	public void 	setAbreviarTitulos(boolean valor_)		{ pbAbrevTitulos = valor_; }

	public boolean 	getPuntoAbrev()							{ return (psPuntoAbrev == "."); }
	public void 	setPuntoAbrev(boolean valor_)			{ if (valor_){psPuntoAbrev=".";} else {psPuntoAbrev = "";} }

	public boolean 	getCentrarTitulos()						{ return pbCentrarTitulos; }
	public void 	setCentrarTitulos(boolean valor_)		{ pbCentrarTitulos = valor_; }

	public boolean 	getCentrarTodo()						{ return pbCentrarTodo; }
	public void 	setCentrarTodo(boolean valor_)			{ pbCentrarTodo = valor_; }

	public boolean 	getSepTotales()							{ return pbSepTotales; }
	public void 	setSepTotales(boolean valor_)			{ pbSepTotales = valor_; }

	public int 		getSepEntreReportes()					{ return piSepEntreReportes;}
	public void 	setSepEntreReportes(int margen_)		{ piSepEntreReportes = margen_;}

	public String 	getRelleno()							{ return psRelleno;}
	public void 	setRelleno(char relleno_)				{ psRelleno = new Character(relleno_).toString();}

	/*
	* @deprecated
	*/
	public FormateadorConsola(){
		  //psCadena = cadena_;
		  //psCadena = abreviarLarga(psCadena);
		  //calcularAnchos(psCadena);
		  //cargarAbreviaturas();
	}

	public FormateadorConsola(String nomArchAbrevs_){
		nomArchAbrevs = nomArchAbrevs_;
		cargarAbreviaturas();
	}

	/*
	* Transforma una cadena de filas de datos separadas por alg�n psSep ( por ej: tabulaci�n )
	* eliminando los separadores y rellenando para producir anchura fija y de esa manera ser
	* visualizado de manera arm�nica por c�nsola
	*/

	public String getReporteConsola(String cabecera_, String cadena_){

		  Map<Integer,Integer> lmAnchosCols;// = new HashMap();

		  /* se eliminan los acentos, ya que no se ven bien en consola
		  	 ojo esta operaci�n se hacia al final sobre la cadena ya procesada, pero generaba un error de mal c�lculo en los anchos
		     debido a la �substuci�n de &nbsp; por eso se movi� aqu� para que afectara la cadena antes de calcular los anchos.
		     habr� que ver luego como limpiar &nbsp;�sin afectar los acentos, si se decide no limpiar los acentos ( separar la limpieza de acentos
		     de la limpieza de &nbsp; */
		  if (pbSubsAcentos) {
			  cadena_ = limpiaSalida(cadena_);
		  }

		  lmAnchosCols = calcularAnchos(cadena_);
		  String salida = ""; 		// resultado final a devolver
		  int liCol = 0;			// contador de columnas
		  int liContFilas = 0;		// contador de filas
		  int liAnchoTotal = calculaAnchoTotal(lmAnchosCols);
		  Integer lILargo;			//
		  String lsTokenCol = "";	// texto de columna
		  String lsSepTotales = "";
		  //boolean bBanderaDebug = false; // variable de utilidad para hacer pruebas y debug



		  StringTokenizer tFilas = new StringTokenizer(cadena_ ,psSepFinLinea);	// tokenizer para las filas
		  StringTokenizer tCols;	  		// tokenizer para las columnas
		  while (tFilas.hasMoreTokens())	// por cada fila
		  {
				 tCols = new StringTokenizer(tFilas.nextToken(),psSep); // tokenizer para las columnas
				 liCol = 0; 	// se inicializa el contador de columnas
				 liContFilas++;	// se incrementa el contador de fiilas

				 while (tCols.hasMoreTokens()) // por cada fila
				 {
						liCol++;	// se incrementa el contador de columnas
						lILargo = (Integer)lmAnchosCols.get(new Integer(liCol)); // se consulta el ancho de la columna
						lsTokenCol = tCols.nextToken();		// el pr�ximo pedazo de texto ( columna )



						/*if (lsTokenCol.equals("10171") || lsTokenCol.equals("1413170")){
							bBanderaDebug = true;
							System.out.println("   aqui    ");
							System.out.println("ancho total " + liAnchoTotal);
							System.out.println("largo       " + lILargo);
							System.out.println("columna      " + liCol);
						}*/

						if (pbSepTotales && !tFilas.hasMoreTokens()) { // esto es para imprimir una raya separadora entre el cuerpo y los totales
							lsSepTotales = rellenar("",psSeparadorTitulos,liAnchoTotal,false,false) + "\n";
						} else {
							lsSepTotales = "";
						}

						if (liCol>1){ // a partir de la 2da columna se agrega un espacio para separarlo de la columna
									  // anterior
							   if ((liContFilas==1) && pbAbrevTitulos && (pbPrimFilaTitulos==true)) { // abreviar los t�tulos
							   		salida = salida + rellenar(abreviar(lsTokenCol),psRelleno,lILargo.intValue() + piSepAdicional,pbCentrarTitulos,false);
								} else {
									salida = salida + rellenar(lsTokenCol,psRelleno,lILargo.intValue() + piSepAdicional,pbCentrarTodo,false);
								}
						} else { // la primera columna va pegada de la izquierda sin psSep adicional
							   if ((liContFilas==1) && pbAbrevTitulos && (pbPrimFilaTitulos==true)) { // abreviar los t�tulos
							   	   salida = salida + rellenar(abreviar(lsTokenCol),psRelleno,lILargo.intValue() ,pbCentrarTitulos,false);
							   } else {
								   salida = salida + lsSepTotales + rellenar(lsTokenCol,psRelleno,lILargo.intValue() ,pbCentrarTodo,false);
							   }
						}
				 }

				 salida =  salida + "\n"; // se le coloca su fin �de nuevo sun fin de l�nea

				 // se agrega psSep de t�tulos
				 if ((liContFilas == 1) && (pbPrimFilaTitulos==true)){
					 // se pasa cadena vac�a (longitud cero) para que rellene todo el ancho
					 salida = salida + rellenar("",psSeparadorTitulos,liAnchoTotal,false,false) + "\n";
				 }

		  }

		  if (cabecera_ != null && !cabecera_.equals("")) { // formatea la la cabecera o t�tulo del reporte
			  salida =
			  rellenar("",psRelleno,liAnchoTotal,false,false) + "\n" + 			// l�nea en blanco
			  rellenar(cabecera_,psRelleno,liAnchoTotal,false,true) + "\n" + 	// t�tulo
			  rellenar("",psRelleno,liAnchoTotal,false,false) + "\n" + // l�nea en blanco
			  salida;
		  }


		  return salida;

	}


	public String getReporteConsola(String cadena_){
		  return getReporteConsola(null,cadena_);
	}

	/*
	* Elimina caracteres que se muestran mal en la consola
	*/
	public static String limpiaSalida(String sTexto_){
		  String lsSalida = "";
		  lsSalida = sTexto_;
		  lsSalida = lsSalida.replaceAll("á","a");
		  lsSalida = lsSalida.replaceAll("é","e");
		  lsSalida = lsSalida.replaceAll("í","i");
		  lsSalida = lsSalida.replaceAll("ó","o");
		  lsSalida = lsSalida.replaceAll("ú","u");
		  lsSalida = lsSalida.replaceAll("Á","A");
		  lsSalida = lsSalida.replaceAll("É","E");
		  lsSalida = lsSalida.replaceAll("Í","I");
		  lsSalida = lsSalida.replaceAll("Ó","O");
		  lsSalida = lsSalida.replaceAll("Ú","U");
		  lsSalida = lsSalida.replaceAll("ñ","n");
		  lsSalida = lsSalida.replaceAll("Ñ","N");
		  lsSalida = lsSalida.replaceAll("&nbsp;","");
		  lsSalida = lsSalida.replaceAll("\t\t","\t \t");
		  lsSalida = lsSalida.replaceAll("\n\t","\n \t");

		  return lsSalida;
	}

	private String abreviar(String cadena_){		
		String lsCadena = cadena_;
		String lsAbrv = (String) pmAbrevs.get(cadena_.toLowerCase());
		if (lsAbrv == null) {
			if (!lsCadena.matches(".* .*")) {
				/*
				* Esta es la forma sencilla de abreviar, cuando el t�tulo es una sola palabra
				*/
				if (lsCadena.length() > 5) {  // se excede 5 caracteres
					lsCadena = lsCadena.substring(0,4) + psPuntoAbrev;	 // se corta y se coloca un punto
				}
			} else { // si son varias palabras
				StringTokenizer tEsp = new StringTokenizer(lsCadena," ");
				String s = "";
				while(tEsp.hasMoreTokens()){
					s = s + tEsp.nextToken().substring(0,1);
				}
				lsCadena = s;
			}
		} else {
			lsCadena = lsAbrv;
		}

		return lsCadena;
	}

	private void cargarAbreviaturas() {
	/*
	* Muestra el contenido del archivo de ayuda
	*/
		String line;
		String lsPalabra= "";
		String lsAbreviatura= "";
		File archivo = new File(nomArchAbrevs);
		try {
			BufferedReader inFile = new BufferedReader(new FileReader(archivo));
			StringTokenizer t;
			while((line = inFile.readLine()) != null) {
				if (!line.equals("")) {
					t = new StringTokenizer(line,";");
					lsPalabra = t.nextToken();
					try {
						lsAbreviatura = t.nextToken();
						pmAbrevs.put(lsPalabra.toLowerCase() ,lsAbreviatura);
					} catch (java.util.NoSuchElementException e) {
						System.out.println("par errado en archivo de abreviaturas \"" + nomArchAbrevs +"\" : " + lsPalabra);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			//throw new ArchivoAbrevsNoEncontradoException(nomArchAbrevs);
			System.out.println("no se encontro archivo de abreviaturas \"" + nomArchAbrevs +"\"");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error en el formato de archivo de abreviaturas \"" + nomArchAbrevs +"\"");
		} finally {
		}
	}

	private Map<Integer,Integer> calcularAnchos(String cadena_){
		  Map<Integer,Integer> lmAnchosCols = new HashMap<Integer,Integer>();
		  int liContCols = 0;
		  int liContFilas = 0;
		  //int liAnchoActual = 0;

		  StringTokenizer tFilas = new StringTokenizer(cadena_ ,psSepFinLinea);
		  StringTokenizer tCols;
		  Integer lILargo = new Integer(0);
		  Integer lILargoGuardado = new Integer(0);
		  Integer lICol;

		  while (tFilas.hasMoreTokens())
		  {
				 liContFilas++;

				 tCols = new StringTokenizer(tFilas.nextToken(),psSep);

				 liContCols = 0;

				 while (tCols.hasMoreTokens())
				 {
						liContCols++;

						if (pbAbrevTitulos && (liContFilas == 1) && (pbPrimFilaTitulos==true)) {
							lILargo = new Integer(abreviar(tCols.nextToken()).length());
						} else {
							lILargo = new Integer(tCols.nextToken().length());
						}

						lICol = new Integer(liContCols);

						if (lmAnchosCols.containsKey(lICol)) {

							   lILargoGuardado = (Integer)lmAnchosCols.get(lICol);

							   if (lILargoGuardado.intValue() < lILargo.intValue()) {
									  lmAnchosCols.put(lICol,lILargo);
							   }

						} else {
							   lmAnchosCols.put(lICol,lILargo);
						}
				 }

		  }
		  return lmAnchosCols;
	}

	private int calculaAnchoTotal(Map<Integer,Integer> mAnchos_){
		  Collection<Integer> c = mAnchos_.values();
		  Iterator<Integer> i = c.iterator();
		  int liAnchoTotal = 0;
		  int liNumCols = mAnchos_.size();
		  Integer n;

		  while(i.hasNext()){
				 n = (Integer)i.next();
				 liAnchoTotal = liAnchoTotal + n.intValue();
		  }

		  liAnchoTotal = liAnchoTotal + ( piSepAdicional * (liNumCols -1)  );

		  return liAnchoTotal;
	}

	public String pegarReportes(String cadena1_, String cadena2_){

		String s = "";
		String s1 = "";
		String s2 = "";
		int l1 = 0;
		int l2 = 0;

		StringTokenizer tFilas1 = new StringTokenizer(cadena1_,psSepFinLinea);
		StringTokenizer tFilas2 = new StringTokenizer(cadena2_,psSepFinLinea);

		while(tFilas1.hasMoreTokens() || tFilas2.hasMoreTokens()){
			if (tFilas1.hasMoreTokens()) {
				s1 = tFilas1.nextToken();
				l1 = s1.length();
			} else {
				s1 = rellenar("",psRelleno,l1,false,false);
			}

			if (tFilas2.hasMoreTokens()) {
				s2 = tFilas2.nextToken();
				l2 = s2.length();
			} else {
				s2 = rellenar("",psRelleno,l2,false,false);
			}

			s = s + s1+rellenar("",psRelleno,piSepEntreReportes,false,false)+s2 + "\n";
		}

		return s;
	}

	/*
	* Esta funci�n deber� devolver in String con la cadena rellenada con caracteres hasta
	* alcanzar la longitud deseada, ejemplo
	*
	* rellenar("2,344.54","",15) devolver�a "       2,344.54"
	*/
	public static String rellenar(String cadena_,String relleno_,int longitud, boolean centrar_, boolean derecha_){

		  String s = "";

		  for (int i=1;i < longitud - cadena_.length() + 1;i++){
				s = s + relleno_;
		  }

		  if (derecha_) {
  			return cadena_ + s;
		  } else {
		  	return s + cadena_;
		  }
	}

}
