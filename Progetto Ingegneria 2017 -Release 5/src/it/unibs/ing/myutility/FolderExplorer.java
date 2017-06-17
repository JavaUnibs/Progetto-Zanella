package it.unibs.ing.myutility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderExplorer {
	
	private static String opSys;
	
	public static String getCurrentDirectory(){
		return System.getProperty("user.dir");
	}
	
	public static void getOS(){
		opSys = System.getProperty("os.name");
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
		getOS();
		if(opSys.equals("Linux")){
			File directory= new File(getCurrentDirectory()+"/"+path);
			return new ArrayList<File>(Arrays.asList(directory.listFiles()));
		}
		else{ 
			File directory= new File(getCurrentDirectory()+"\\"+path);
			return new ArrayList<File>(Arrays.asList(directory.listFiles()));
		}
	}
	
	public static ArrayList<String> listFileNames(String path){
		getOS();
		if(opSys.equals("Linux")){
			File directory= new File(getCurrentDirectory()+"/"+path);
			return new ArrayList<String>(Arrays.asList(directory.list()));
		}
		else{
			File directory= new File(getCurrentDirectory()+"\\"+path);
			return new ArrayList<String>(Arrays.asList(directory.list()));
		}
	}
	
	

}
