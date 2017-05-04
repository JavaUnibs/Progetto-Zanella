package it.unibs.ing.myutility;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TxtToHashmap {
	
	private String filePath;
	
	public TxtToHashmap(String file)
	{
		this.filePath = file;
	}

	/*
	 * Da un file di testo strutturato come:
	 * KEY="valore"
	 * ....
	 * ritorna un HashMap con elementi che hanno come chiave la KEY e valore VALORE
	 */
	public HashMap<String, String> convertToString() throws IOException
	{
	    HashMap<String, String> map = new HashMap<String, String>();

	    String line;
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
			while ((line = reader.readLine()) != null)
		    {
		        String[] parts = (line.split("=", 2)); //tolto replaceAll
		        if (parts.length >= 2)
		        {
		            String key = parts[0];
		            String value = parts[1];
		            map.put(key, value);
		        } else {
		            System.out.println("ignoring line: " + line);
		        }
		    }
			
			//for (String key : map.keySet())
		    //{
		    //    System.out.println(key + ":" + map.get(key));
		    // }
		    reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   
	    return map;
	}
	
	/*
	 * Da un file txt strutturato come:
	 * KEY
	 * valori,valori,
	 * valori,valori, valori,
	 * ...
	 * $
	 * KEY
	 * ...
	 * 
	 * Restituisce un HashMap<String, String[]> 
	 */
	public HashMap<String, String[]> convertToArray() throws IOException
	{
	    HashMap<String, String[]> map = new HashMap<String, String[]>();
	    String[] list;
	    
	    String line;
	    BufferedReader reader;
		
	    try {
			reader = new BufferedReader(new FileReader(filePath));
			
			while ((line = reader.readLine()) != null)
		    {
				String key = line;
				String value = "";
				String tmp;
				
				while(!(tmp=reader.readLine()).equals("$"))
				{
					value = value+tmp; 
					
				}
				String values = value.replaceAll("\"", "");
				
				String[] parts = values.split(",");
		        map.put(key, parts);
		    }
			
			/*for (String key : map.keySet())
		    {
		        System.out.println(key);
		        for(int i=0; i < map.get(key).length; i++)
		        {
		        	System.out.println(map.get(key)[i]);
		        }
		    }*/
			
		    reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   
	    return map;
	}
}
