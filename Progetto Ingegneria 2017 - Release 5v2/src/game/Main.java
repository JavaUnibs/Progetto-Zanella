package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.unibs.ing.myutility.FolderExplorer;
import it.unibs.ing.myutility.LeggiInput;
import it.unibs.ing.myutility.Menu;
import it.unibs.ing.myutility.SalvataggioFile;
import it.unibs.ing.myutility.TxtToHashmap;

public class Main {
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Salva la sessione"};

	public static void main(String[] args) {
		HashMap<String, String> common_string=null;
		HashMap<String, String> local_string=null;
		HashMap<String, String[]> values=null;
		World mondo = null;
		Ground luogo_corrente = null;
		Navigation navigation=null;
		
		try {
			common_string=new TxtToHashmap("files\\COMMON_STRINGS.txt").convertToString();
		} catch (IOException e1) {
			System.out.println("Errore nel caricamento del file delle stringhe comuni");
			return;
		}
	
	//---------------------------------------------------------------------------------------------Scelta di caricamento
			if(LeggiInput.doppiaScelta(common_string.get("LOADING"))){
				
				LeggiInput.terminaRiga();
				String percorso= LeggiInput.riga(common_string.get("LOAD_LOCATION"));
				File fgame = new File(percorso);
				Save save = null;
				boolean caricamentoRiuscito = false;
				
				if ( fgame.exists() )
				{
				 try 
				  {
					 save = (Save)SalvataggioFile.caricaOggetto(fgame);
					 mondo = save.getMondo();
					 luogo_corrente = save.getLuogo_corrente();
					 local_string=save.getLocalString();
					 values=save.getValues();
					 navigation=save.getNavigation();
				   }
				  catch (ClassCastException e)
				   {
					 System.out.println(common_string.get("MSG_NO_CAST"));
					}
				   finally
					{
				      if ( (mondo != null) && (luogo_corrente != null) &&(local_string!=null) &&(values!=null) &&(navigation!=null))
					    {
						 System.out.println(common_string.get("MSG_OK_FILE"));
						 caricamentoRiuscito = true;
						 }
					  }
				 }
					
				if (!caricamentoRiuscito)
				   {
					System.out.println(common_string.get("MSG_NO_FILE"));
	                return;
				    }
				
			}
			//-----------------------------------------------------------------------------------------------Alternativa a caricamento (Creazione nuovo gioco)
			else{
				
				File nome_mondo;
				Iterator<File> fileList = FolderExplorer.listFiles("files").iterator();
				ArrayList<File> directoryList = new ArrayList<>();
				while(fileList.hasNext()) { 
					File file = fileList.next(); 
					if(!file.isDirectory()) 
						fileList.remove();
					else {
						directoryList.add(file);
					}
				}
				
				for(int y=0;y<directoryList.size();y++){
					System.out.println(y+") "+directoryList.get(y).getName()+"\n");
				}
				
				int num_mondo=LeggiInput.intero(common_string.get("CHOOSE_WORLD"));
				try{
					nome_mondo=directoryList.get(num_mondo);
				}catch(IndexOutOfBoundsException e){
					System.out.println(common_string.get("NO_WORLD"));
					return;
				}
				
				try {
					local_string=new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\local_string.txt").convertToString();
					values= new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\values.txt").convertToArray();
				} catch (IOException e) {
						System.out.println("Errore nel caricamento dei file delle stringhe locali o valori");
						return;
				}
				
				Factory factory= SetupGame.getFactory(values, common_string, local_string);
				mondo=factory.getWorld();
				luogo_corrente=mondo.getStartGround();
				navigation=factory.getNavigation();
				
				if(LeggiInput.doppiaScelta(common_string.get("MODIFY_WORLD"))){
					
					ModifyWorld gui= factory.getModify();
					gui.initialize();
				}
				
			}
			
			//-------------------------------------------------------------------------------------------------Inizio gioco
			System.out.println(local_string.get("START_MSG")+mondo.searchGround(convertValues(values, "END_H"), convertValues(values, "END_W"), convertValues(values, "END_D")).getName()+".");
			System.out.println(local_string.get("START_MSG2")+"\n"+local_string.get("START_MSG3")+"\n"+local_string.get("START_MSG4"));
			
			Menu elenco = new Menu(MENU_PRINCIPALE);
			int scelta;
			
			do {
				
				System.out.println(local_string.get("CURRENT_GROUND")+luogo_corrente.getName());
				scelta=elenco.stampaMenu();
			
				switch(scelta){
				
				case 0: {};	
				break;
				
				case 1: {
					luogo_corrente=navigation.navigate(luogo_corrente);
					if(luogo_corrente==null) return;
				}
				break;
				
				case 2:{
					
					Save save = new Save(mondo,luogo_corrente, local_string, values, navigation);
					LeggiInput.terminaRiga();
					File fgame = new File(LeggiInput.riga(common_string.get("SAVE_LOCATION")));
					if(fgame.exists()) {
						System.out.println(common_string.get("FILE_EXISTS")); 
						if(!LeggiInput.doppiaScelta(common_string.get("OVERWRITE")))
							break;
					}
			        SalvataggioFile.salvaOggetto(fgame, save);	
				}
				break;
				
				default: System.out.println(common_string.get("NO_OPZ"));
				break;
				}
				
			}while(scelta!=0);
			
			
	}
	
	static int convertValues(HashMap<String, String[]> map, String key){
		return Integer.parseInt(map.get(key)[0]);
	}
	
	
	
}