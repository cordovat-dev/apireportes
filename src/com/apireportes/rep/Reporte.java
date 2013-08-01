package com.apireportes.rep;

import java.io.*;
import java.sql.*;
import java.math.*;

/**
 * Esta clase consulta la base de datos y devuelve los resultados
 * de los queries en forma tabular pero sin HTML, para ser pintado
 * en JSP, especificamente el reporte de LGN<br>
 * <h4>/Historia de modificaciones</h4>/
 * <br>28/09/2012 CORDOVATJ, se cambio de String a Stringbuffer en getStrFilasRS para mayor rendimiento con grandes cantidades de data. Se agregó tipo de dato INTEGER en getStrFilasRS y se lanza excepción en casos no soportados. Se eliminó error latente en getStrColRsNumero, leyendo primero el número de rs antes de preguntar si fue null 
 * <br>28/05/2010 CORDOVATJ, se hizo una profunda refactorizacion para reducir la complejidad siclomatica, entre ellas la extraccion de muchos miembros a la clase FormatoReporte
 * <br>18/03/2010 CORDOVATJ, agregaron metodos getFormatoNumerosTotales, setFormatoNumerosTotales, setRedondearTotales y getRedondearTotales, por la necesidad de tener reportes con los detalles con decimales pero los totales redondeados ( ejemplo reporte de mermas ). Por lo cual se modific� el m�tgodo getStrTotales y getSumaColumna para cuyos comportamientos se afecta si la opci�n de redondear totales est� seteada ( por defecto false )
 * <br>08/01/2010 CORDOVATJ, se agrego un modo adicional llamado SIN_TOTALES_CON_PROMEDIOS que permite obtener el reporte con promedios pero sin totales, se manera que las clases que usan o heredan, no tengan que armar esa configuraci�n por nedio de varias llamadas separadas
 * <br>10/12/2009 CORDOVATJ, se aumento la visibilidad de protected a public para soportar el llamado desde otros paquetes, tal como se hizo en su momento con getStrFilasRS
 * <br>04/12/2009 CORDOVATJ, se modifico getRsQuery ( el unico sitio de la clase donde se crea un statement ) para que hiciera ALTER SESSION de las variables NLS de Oracle, coloc�ndolas en ingl�s estadounidense, evitando as� el error ORA-12705 al acceder a dblinks. Se hizo depender este comportamiento de una variable miembro que se puede setear e inspeccionar con los m�todos setForzarNLSUSA y getForzarNLSUSA respectivamente. El comportamiento est� activo por defecto.
 * <br>26/09/2008 CORDOVATJ, se modifico getStrFilasRS para que no instanciase un nuevo SimpleDateFormat para cada fila
 *                       en caso de fechas sino que use el miembro fechaFormat. Tambien se sustituyo getDate por
 *                       getTimeStamp para que las fechas traigan horas
 * <br>13/08/2007 CORDOVATJ, se cambio la visibilidad del metodo getStrFilasRS de protected a public de tal forma
 * que pueda ser utilizado por clases que no estan en la linea de herencia de Reporte. Esto es until para formatear
 * salidas que van a ser pintadas, pero la clase que la usa no es descendiente de Reporte
 * <br>08/03/2007 se agregan los metodos setSustituirTABporEspacio y getSustituirTABporEspacio
 * <br>14/02/2007 se agrego el metodo setFormatoFechas para poder cambiar el formato por defecto de las fechas
 * <br>14/02/2007 se agrego la constante SIN_TOTALES la cual si se le pasa a getStrFilas, hace que dicho metodo
 * excluya los totales de la cadena devuelta
 * <br>23/10/2006 se agregaron los metodos redondear y getStrNumero(String formato_, double numero_)
 * se agregaron varios comentarios de JavaDoc
 * <br>05/06/2006 se agregaron los metodos setMostrarLeyendaTotal(boolean opcion), getMostrarLeyendaTotal() y setMostrarLeyendaTotal(String leyenda)
 * y se modifico getStrTotalesRS en consecuencia
 * @author Aplicaciones Oriente
 * @version 2.0
 */
public class Reporte
{
	FormatoReporte formato = new FormatoReporte();
    private final Connection conn;
    private boolean mostrarFechaDebug = false;
    private boolean mostrarSQLDebug = false;
    private boolean forzarNLSUSA = true;
    private int numColsUltRS = 0;
    public static final int ENCABEZADOS = 0;
    public static final int CUERPO = 1;
    public static final int TOTALES = 2;
    @Deprecated
    public static final int TODO = 3;
    public static final int COMPLETO = 3;
    public static final int PROMEDIOS = 4;
    public static final int TODO_MAS_PROMEDIOS = 5;
    public static final int SIN_TOTALES = 6;
	public static final int SIN_TOTALES_CON_PROMEDIOS = 7;

	
	public FormatoReporte getFormato(){
		return formato;
	}
    /**
     * Activa la opcion que hace que se pongan en ingles estadounidense todos los parametros de NLS de Oracle (usando ALTER SESSION) para evitar el error ORA-12705 al usar DBlinks de Oracle.
     * Esta opcion esta por defecto en true.
     * @see #getForzarNLSUSA()
     */
	public void setForzarNLSUSA(boolean opcion_){
		forzarNLSUSA = opcion_;
	}

    /**
     * Obtiene el valor de la opcion que hace que se pongan en ingles estadounidense todos los paramatros de NLS de Oracle (usando ALTER SESSION) para evitar el error ORA-12705 al usar DBlinks de Oracle.
     * Esta opcion esta por defecto en true.
     * @see #setForzarNLSUSA(boolean opcion_)
     */
	public boolean getForzarNLSUSA(){
		return forzarNLSUSA;
	}	
	
   /**
   *  Devuelve el objeto Connection que fue pasado en el constructor
   *
   *  @see #Reporte
   *  @return java.sql.Connection
   *
   *
   */
    protected Connection getConn()
    {
        return conn;
    }

    /**
    * Causa que se muestren en la salida estandar las sentencias SQL antes de pasarlas al manejador. Esto es util para detectar errores.
    *
    * @see #getMostrarSQLDebug
    * @see #setMostrarFechaDebug
    * @see #getMostrarFechaDebug
    * @param opcion True para activar el comportamiento descrito o false para desactivarlo.
    */
    public void setMostrarSQLDebug(boolean opcion)
    {
        mostrarSQLDebug = opcion;
    }
    /**
    * @see #setMostrarSQLDebug
    * @see #setMostrarFechaDebug
    * @see #getMostrarFechaDebug
    * @return boolean
    */
    public boolean getMostrarSQLDebug()
    {
        return mostrarSQLDebug;
    }
    /**
    * Causa que se muestren en la salida estandar las fechas utilizadas en los queries.
    * El comportamiento se debe implementar en las clases hijas. Esto es util para detectar errores.
    *
    * @see #getMostrarSQLDebug
    * @see #setMostrarSQLDebug
    * @see #getMostrarFechaDebug
    * @param opcion True para activar el comportamiento descrito o false para desactivarlo.
    */
    public void setMostrarFechaDebug(boolean opcion)
    {
        mostrarFechaDebug = opcion;
    }

    public boolean getMostrarFechaDebug()
    {
        return mostrarFechaDebug;
    }

    public Reporte(Connection pconn) throws SQLException
    {
        conn = pconn;        
    }

    public double getSumaColumna(ResultSet rs, int col) throws SQLException
    {   // se recorre las filas de una columna num�rica y se acumulan
        double total = 0;
        int fila = 0;
        rs.beforeFirst();       
        while (rs.next()){
        		++fila;
				if (!formato.omitirFilaTotales(fila)) {
                	total += rs.getDouble(col);
				}
        }
        
        if (formato.getRedondearTotales()) {
        	total = Math.round(total);
        }
        
        return total;
    }

    public ResultSet getRsQuery (String strSQL) throws SQLException
    {
        if (mostrarSQLDebug) { System.out.println(strSQL); }
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);        

        if (forzarNLSUSA) {
			stmt.execute("ALTER SESSION SET NLS_CURRENCY = '$'");
			stmt.execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-RR'");
			stmt.execute("ALTER SESSION SET NLS_DATE_LANGUAGE = 'AMERICAN'");
			stmt.execute("ALTER SESSION SET NLS_SORT = 'BINARY'");
			stmt.execute("ALTER SESSION SET NLS_TERRITORY = 'AMERICA'");
		}

        ResultSet rset = stmt.executeQuery(strSQL);
        numColsUltRS = rset.getMetaData().getColumnCount();
        
        return rset;
    }

    protected String getStrNumero(long numero)
    {
    	if (formato.mostrarNumero(numero)){
        	return numero + "";
        } else {
            return " ";
        }
    }

    protected String getStrEncabezadosRS(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData(); // obtiene los metadatos de un ResultSet
        int numberOfColumns = rsmd.getColumnCount(); // obtiene el total de columnas del ResultSet
        numColsUltRS = numberOfColumns;
        String retorno = "";
        //int i;
        for (int i=1;i<=numberOfColumns;i++){ // por cada columna...
            // concatena el encabezado de la columna siempre y cuando omitirCols sea false o, siendo true
            // no se encuentre el indice de la columna en el arreglo de columnas a omitir (indexOf devuelve -1)
        	if (formato.mostrarColumna(i)) {
                    retorno += rsmd.getColumnLabel(i) + formato.getSeparador();
        	}
        }
        return retorno + "\n";
    }

    protected String getStrSustituirLNporBR (String cadena)
    {
    	return cadena.replaceAll("\n","<br>");
       /*String line;
        StringTokenizer st = new StringTokenizer(cadena,"\n");
        line = "";
        while (st.hasMoreTokens())
        {
            line += (st.nextToken()) + "<br>";
        }
        return line;*/
    }

    protected String getStrSustituirTABporEspacio (String cadena)
    {
    	return cadena.replaceAll("\t","&nbsp;&nbsp;");
    }

	public int getNumColsRS(){
		return numColsUltRS;
	}

	public int getNumColsRS(ResultSet rs) throws SQLException {
		return rs.getMetaData().getColumnCount();
	}

	private String getStrColRsNumero(ResultSet rs, int col) throws SQLException{
		String retorno = ""; 
		double d = rs.getDouble(col);
        if (formato.getSustituirNull()) { // si no debo sustituir los nulos
            if (rs.wasNull()){ // si el valor fue nulo
                retorno += formato.getCadenaSustitutoNull() + formato.getSeparador();
            } else {
                retorno += formato.getStrNumero(col,d) + formato.getSeparador();
            }                                
        } else { //si debo sustituir los nulos
        	retorno += formato.getStrNumero(col,d) + formato.getSeparador();
        }
        
        formato.agregarColumnaNumerica(col);
                
        return retorno;
	}	
	
	private String getStrColRsTexto(ResultSet rs, int col) throws SQLException{
    	String cadenaTemp = rs.getString(col);
    	if (cadenaTemp == null){ 
    		cadenaTemp = "";
    	
    	}
    	String retorno="";
    	
    	if (formato.getSustituirTABporEspacio()) {
			cadenaTemp = getStrSustituirTABporEspacio(cadenaTemp);
		}
        if (formato.getSustituirNLporBR()) { // si debo sustituir NL por <BR> (para pintar las paginas y
                                // evitar que los NL contenidos en los datos hagan fallar a la clase
            retorno += getStrSustituirLNporBR(cadenaTemp.replace('\r',' ')) + formato.getSeparador();
        } else {
            retorno += cadenaTemp.replace('\r',' ').replace('\n',' ') + formato.getSeparador();
        }
        
        return retorno;
	}	

	private String getStrColRsFecha(ResultSet rs, int col) throws SQLException{
        String dateString = formato.getStrFecha(rs.getTimestamp(col));
        return dateString + formato.getSeparador();
	}	
	
    public String getStrFilasRS (ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData(); //obtengo los metadatos del ResultSet para saber el tipo de datos de cada columna
        int numberOfColumns = rsmd.getColumnCount();
        numColsUltRS = numberOfColumns;
        formato.borrarColumnasNumericas(); //blanqueo el vector que indica las columnas numericas
        StringBuffer retorno = new StringBuffer();
        
        rs.beforeFirst();
        while (rs.next()){ //por cada fila del ResultSet
            for (int i=1;i<=numberOfColumns;i++){ // por cada columna
            	if (formato.mostrarColumna(i)){
                   switch (rsmd.getColumnType(i)){ //segun el tipo de dato de la columna
                   		case Types.INTEGER:
                        case Types.NUMERIC:
                            retorno.append(getStrColRsNumero(rs,i));
                            break;
                        case Types.VARCHAR:
                        case Types.CHAR:
                        	retorno.append(getStrColRsTexto(rs,i));
                            break;
                        case Types.DATE:
                        case Types.TIMESTAMP:
                        	retorno.append(getStrColRsFecha(rs,i));
                            break;
                        default: 
                        	throw new SQLException("<<<<<TIPO DE DATO NO RECONOCIDO " + rsmd.getColumnType(i) + " >>>>>"); 
                   }
               }
            }
            retorno.append("\n");
        }
        return retorno.toString();
    }

    protected String getStrTotalesRS (ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        int fila = 0;
        numColsUltRS = numberOfColumns;
        double[] totales = new double[numberOfColumns];
        rs.beforeFirst();
        
        while (rs.next()){
        	if(!formato.omitirFilaTotales(++fila)){
				// Por cada fila voy acumulando los valores numericos en la posicion correspondiente de un arreglo				
				for (int i=1;i<=numberOfColumns;i++){
					if (formato.mostrarColumna(i)) {
						if (rsmd.getColumnType(i) == Types.NUMERIC){
							totales[i-1] += rs.getDouble(i);							
						} else {
							totales[i-1] = 0; // si el dato no es numerico acumulo cero							
						}
						// se refactorizo
					}
				}
			}
        }
        
        return getStrTotalesArreglo(totales,0) + "\n";
    }
    
    protected String getStrTotalesArreglo(double[] totales, int numFilas){
        boolean leyendaTotalProcesada = false;
        String retorno = "";
        String lsLeyenda;
        int divisor = 1;
        boolean redondear = false;
        if ( numFilas == 0 ) {
        	lsLeyenda = formato.getLeyendaTotal();   
        	redondear = formato.getRedondearTotales();
        } else {
        	lsLeyenda = formato.getLeyendaPromedios();
        	divisor = numFilas;
        }
        
        for (int i=1;i<=totales.length;i++){ // una vez lleno el arreglo de totales genero la fila de totales
            // NOTA: modificar para no totalize (con cero por supuesto) las columnas no numericas (devolver espacio)
        	if (formato.mostrarColumna(i)){ 
					if (formato.getMostrarLeyendaTotal() && !leyendaTotalProcesada){
						retorno += lsLeyenda;
						leyendaTotalProcesada = true;
					} else {
						if (!formato.omitirColumnaTotales(i)) {
							if (redondear){
								totales[i-1] = Math.round(totales[i-1]);
								retorno += formato.getStrNumeroTotales(totales[i-1]);
							} else {
								retorno += formato.getStrNumero(i,totales[i-1] / divisor);
							}
						}
					}
					
					retorno += formato.getSeparador();
			}
            
            
        } 
        
        return retorno;
    }
    

    protected String getStrPromediosRS (ResultSet rs) throws SQLException
    {
        // funciona igual que el metodo de los totales, se podria poner la parte inicial en una sola funcion ya que es codigo duplicado
        ResultSetMetaData rsmd = rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        numColsUltRS = numberOfColumns;
        double[] totales = new double[numberOfColumns];
        int liContadorFilas = 0;
        rs.beforeFirst();
        while (rs.next()){
            for (int i=1;i<=numberOfColumns;i++){
                if (formato.mostrarColumna(i)){
                	if (rsmd.getColumnType(i) == Types.NUMERIC){
                		totales[i-1] += rs.getDouble(i);
                	} else {
                		totales[i-1] = 0;
                	}
                }
            }
            liContadorFilas++;
        }
        
        return getStrTotalesArreglo(totales,liContadorFilas) + "\n";
        
    }

    public String getStrFilas (ResultSet rs, int modo)  throws SQLException
    {
            String sCadena = "";
            switch (modo)
            {
                case ENCABEZADOS:
                    sCadena += getStrEncabezadosRS(rs); break;
                case CUERPO:
                    sCadena += getStrFilasRS(rs); break;
                case TOTALES:
                    sCadena += getStrTotalesRS(rs); break;
                case TODO:
                    sCadena += getStrEncabezadosRS(rs) + getStrFilasRS(rs) + getStrTotalesRS(rs); break;
                case PROMEDIOS:
                    sCadena += getStrPromediosRS(rs);break;
                case TODO_MAS_PROMEDIOS:
                    sCadena += getStrEncabezadosRS(rs) + getStrFilasRS(rs) + getStrTotalesRS(rs) + getStrPromediosRS(rs);break;
                case SIN_TOTALES:
                	sCadena += getStrEncabezadosRS(rs) + getStrFilasRS(rs); break;
                case SIN_TOTALES_CON_PROMEDIOS:
                	sCadena += getStrEncabezadosRS(rs) + getStrFilasRS(rs) + getStrPromediosRS(rs);break;
                default:
                    sCadena += getStrEncabezadosRS(rs) + getStrFilasRS(rs) + getStrTotalesRS(rs); break;
            }
            return sCadena;
    }


/**
 * Permite redondear un double a una cantidad especifica de decimales
 *
 * @param numero_ es el numero que se quiere redondear
 * @param decimales_ es la cantidad de decimales a la cual se quiere redondear el numero
 */
    public static double redondear(double numero_, int decimales_){
		BigDecimal bd = new BigDecimal(numero_);
		bd = bd.setScale(decimales_,BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

/**
 * Permite enviar un String a un archivo plano. Se puede usar con getStrFilas para enviar la salida al archivo.
 *
 * @param dato es el String que se quiere guardar en disco.
 * @param nomArch es el nombre del archivo ya sea con ruta absoluta ("c:\\carpeta\\salida.txt") o relativa ("salida.txt").
 */
    public static void escribirDisco(String dato, String nomArch)
    {
		Reporte.escribirDisco(dato, nomArch, false,true);
	}
    
    public static void escribirDisco(String dato, String nomArch, boolean append, boolean reportar)
    {
		PrintWriter outFile;
		File archivo = new File(nomArch);
		try
		{
			outFile = new PrintWriter(new FileWriter(archivo,append));
			outFile.println(dato);
			outFile.close();
			if (reportar) {
				if (append) {
					System.out.println("Se escribio en el archivo \"" + archivo.getAbsolutePath() + "\".");
				} else {
					System.out.println("Se creo el archivo \"" + archivo.getAbsolutePath() + "\".");
				}
				
			}
		}
		catch (IOException e)
		{
			System.out.println("Problemas con el archivo \"" + archivo.getAbsolutePath() + "\" (volcar)");
		}
	}
    
	public static void escribirDiscoAppend(String dato, String nomArch) {
		Reporte.escribirDisco(dato, nomArch, true,false);		
	}  
    
    

}
