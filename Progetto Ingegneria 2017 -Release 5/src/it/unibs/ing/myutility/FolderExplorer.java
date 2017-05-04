package it.unibs.ing.myutility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderExplorer {
	
	public static String getCurrentDirectory(){
		return System.getProperty("user.dir");
	}
	
	public static ArrayList<String> listFileNames(){
		File directory= new File(getCurrentDirectory());
		return new ArrayList<String>(Arrays.asList(directory.list()));
		
	}
	
	public static ArrayList<File> listFiles(){
		File directory= new File(getCurrentDirectory());
		return new ArrayList<File>(Arrays.asList(directory.listFiles()));
		
	}
	
	public static ArrayList<File> listFiles(String path){
		File directory= new File(getCurrentDirectory()+"\\"+path);
		return new ArrayList<File>(Arrays.asList(directory.listFiles()));
		
	}
	
	public static ArrayList<String> listFileNames(String path){
		File directory= new File(getCurrentDirectory()+"\\"+path);
		return new ArrayList<String>(Arrays.asList(directory.list()));
		
	}
	
	

}
