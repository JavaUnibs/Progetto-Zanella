package it.unibs.ing.myutility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderExplorer {
	
	public static String getCurrentDirectory(){
		return System.getProperty("user.dir");
	}
	
	public static ArrayList<String> listFiles(){
		File directory= new File(getCurrentDirectory());
		return new ArrayList<String>(Arrays.asList(directory.list()));
	}
	
	

}
