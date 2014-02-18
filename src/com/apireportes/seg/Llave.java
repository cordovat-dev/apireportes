package com.apireportes.seg;

public class Llave {
	private String dbStr;
	private String dbUsr;
	private String dbPW;
	public Llave(String dbStr_, String dbUsr_, String dbPW_){
		dbStr = dbStr_;
		dbUsr = dbUsr_;
		dbPW  =	dbPW_;
	}


	public String getDBStr() { return dbStr;}
	public String getDBUsr() { return dbUsr;}
	public String getDBPW() { return dbPW;}

	public String toString(){
		return
		"\nLlave\ndbStr : " + dbStr +
		"\ndbUsr : " + dbUsr +
		"\ndbPW : " + dbPW ;
	}

}