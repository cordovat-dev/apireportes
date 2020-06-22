package com.apireportes.pre;

import java.io.File;
import java.util.Comparator;

public class ComparatorNombreArchivos implements Comparator<File> {

	public int compare(File arg0, File arg1) {
		
		return arg0.getName().compareToIgnoreCase(arg1.getName());
	}

}
