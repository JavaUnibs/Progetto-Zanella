package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Salva la sessione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	/**
	 * Metodo main che permette all'utente di giocare, ha il compito di fornire un menu testuale con il quale l'utente può interagire
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashMap<String, String> common_string=null;
		HashMap<String, String> local_string=null;
		HashMap<String, String[]> values=null;
		
			try {
					if(System.getProperty("os.name").equals("Linux")){
						common_string=new TxtToHashmap("files/COMMON_STRINGS.txt").convertToString();
					}
					else{
						common_string=new TxtToHashmap("files\\COMMON_STRINGS.txt").convertToString();
					}
				
			} catch (IOException e1) {
				System.out.println("Errore nel caricamento del file delle stringhe comuni");
				return;
			}			

		
		int scelta;
		int peso_totale;
		int num_totale;
		World mondo = null;
		Ground luogo_corrente = null;
		
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
			   }
			  catch (ClassCastException e)
			   {
				 System.out.println(common_string.get("MSG_NO_CAST"));
				}
			   finally
				{
			      if ( (mondo != null) && (luogo_corrente != null) &&(local_string!=null) &&(values!=null))
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
					if(System.getProperty("os.name").equals("Linux")){
						local_string=new TxtToHashmap(nome_mondo.getAbsolutePath()+"//local_string.txt").convertToString();
						values= new TxtToHashmap(nome_mondo.getAbsolutePath()+"//values.txt").convertToArray();
					}
					else{
						local_string=new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\local_string.txt").convertToString();
						values= new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\values.txt").convertToArray();
					}
					
				} catch (IOException e) {
					System.out.println("Errore nel caricamento dei file delle stringhe locali o valori");
					return;
				}
				
				
				SetupWorld setup=new SetupWorld(values);
				if(!setup.initialize()) return;
				mondo= setup.getMondo();
				luogo_corrente=mondo.searchGround(convertValues(values, "START_H"), convertValues(values, "START_W"), convertValues(values, "START_D"));
				luogo_corrente.setStart(true);
				mondo.searchGround(convertValues(values, "END_H"), convertValues(values, "END_W"), convertValues(values, "END_D")).setEnd(true);
					
		
				if(LeggiInput.doppiaScelta(common_string.get("MODIFY_WORLD"))){
				
						InterfaceSetupWorld gui= new InterfaceSetupWorld(setup);
						gui.initialize();
				}

		}
		
		
        //-------------------------------------------------------------------------------------------------Inizio gioco
		System.out.println(local_string.get("START_MSG")+mondo.searchGround(convertValues(values, "END_H"), convertValues(values, "END_W"), convertValues(values, "END_D")).getName()+".");
		System.out.println(local_string.get("START_MSG2")+"\n"+local_string.get("START_MSG3")+"\n"+local_string.get("START_MSG4"));
		
		Menu elenco = new Menu(MENU_PRINCIPALE);
		Menu elenco_dir= new Menu(MENU_DIREZIONI);
		
		do {
			
			
			System.out.println(local_string.get("CURRENT_GROUND")+luogo_corrente.getName());
			scelta=elenco.stampaMenu();
		
			switch(scelta){
		
			case 0: {};	
			break;
			case 1: {
				
				int scelta_dir;
				
				
				do {
					
					
					Ground luogo_prossimo=luogo_corrente;
					
					if(luogo_corrente.isEnd()) {
						System.out.println(local_string.get("END"));
						if(mondo.getTrials().isEmpty()) 
							return;
						else {
							if(!mondo.getTrials().isEmpty() && mondo.getPoints()<mondo.getPunteggio_finale()) 
								System.out.println(local_string.get("INSUFF_POINTS"));
							else if(!mondo.getTrials().isEmpty() && mondo.getPoints()>=mondo.getPunteggio_finale()) {
									System.out.println(local_string.get("ENOUGH_POINTS"));
									return;
							}
						}
					}
					//-------------------------------------------------------------------------------------------------------Recupero chiavi
					
					
					num_totale=mondo.getPlayerkeys().size();
					peso_totale=mondo.totWeight();
					
					if(luogo_corrente.getKey()!=null&&!mondo.isDepositata()) {
						Token key=luogo_corrente.getKey();
						
						System.out.println(local_string.get("KEY_PRESENT") + key);
						if(LeggiInput.doppiaScelta(local_string.get("GET_KEY"))){   //LeggiInput.doppiaScelta(GET_KEY)
							if(peso_totale + key.getWeight() <= mondo.getPeso_max_trasportabile() && num_totale + 1 <= mondo.getNumero_max_trasportabile()){											//peso_totale + key.getWeight() <= peso_max && num_totale + 1 <= numero_max
								mondo.getPlayerkeys().add(key);
								luogo_corrente.setKey(null);
								System.out.println(local_string.get("GOT_KEY"));
							}
							else System.out.println(local_string.get("WEIGHT"));
						}
					}
					
					//-------------------------------------------------------------------------------------------------------Deposito chiavi
					
					else if(!mondo.getPlayerkeys().isEmpty()&&!luogo_corrente.isEnd()&&!luogo_corrente.isStart()&&luogo_corrente.getKey()==null&&
							LeggiInput.doppiaScelta(local_string.get("PUT_KEY"))
							){
						
						ArrayList<String> temp= new ArrayList<String>();
						for(Token a: mondo.getPlayerkeys()) temp.add(a.toString());
						String[] temp2= new String[temp.size()];
						temp2=temp.toArray(temp2);
						Menu elenco_chiavi = new Menu(temp2);
						int scelta_chiavi=elenco_chiavi.stampaSottoMenu();
						
				
						if(scelta_chiavi-1>=0&&scelta_chiavi<=temp.size()){
							
							Token key=mondo.getPlayerkeys().get(scelta_chiavi-1);
							luogo_corrente.setKey(key);
							mondo.getPlayerkeys().remove(key);
							System.out.println(local_string.get("PUT_KEY_OK"));
							mondo.setDepositata(true);

						}
					}
					
					//--------------------------------------------------------------------------------------------------------Prova
					Trial trial= luogo_corrente.getTrial();
					if(!mondo.isProva_fatta()&&trial!=null&&LeggiInput.doppiaScelta(local_string.get("TRIAL")+trial+local_string.get("TRIAL2"))){
						String domanda=trial.getQuestion();
						LeggiInput.terminaRiga();
						String risposta=LeggiInput.riga(domanda);
						boolean correct= trial.getAnswer(domanda, risposta);
						if(correct) System.out.println(local_string.get("RIGHT"));
						else System.out.println(local_string.get("WRONG"));
						mondo.updatePoint(trial, correct);
						mondo.setProva_fatta(true);
						System.out.println(local_string.get("POINTS")+mondo.getPoints());
					}
					
					
                    //-----------------------------------------------------------------------------------------------------------Scelta direzione
					
					scelta_dir=elenco_dir.stampaMenu();
					
					switch(scelta_dir){
					
					case 0:{}; break;
					case 1: {
						
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight()+1, luogo_corrente.getWidth(), luogo_corrente.getLevel());
					}
					
				    break;
				    
					case 2:{
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight()-1, luogo_corrente.getWidth(), luogo_corrente.getLevel());
						
					}break;
					
					case 3:{
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight(), luogo_corrente.getWidth()-1, luogo_corrente.getLevel());
						
					}break;
					
					case 4:{
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight(), luogo_corrente.getWidth()+1, luogo_corrente.getLevel());
						
					}break;
					
					case 5:{
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight(), luogo_corrente.getWidth(), luogo_corrente.getLevel()+1);
						
					}break;
					
					case 6:{
						luogo_prossimo= mondo.searchGround(luogo_corrente.getHeight(), luogo_corrente.getWidth(), luogo_corrente.getLevel()-1);
						
					}break;
					
					default: System.out.println(common_string.get("NO_OPZ"));
					break;
					
					}
					
					
					if(luogo_prossimo==null) System.out.println(local_string.get("NO_GROUND"));
					else if(luogo_prossimo==luogo_corrente);
					else {
						
						Passage ptemp=mondo.searchPassage(luogo_corrente, luogo_prossimo);
						if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
						
						else {
							
							if(ptemp.getKey()!=null){
								System.out.println(local_string.get("KEY_NEEDED")+ptemp.getKey());
								
								if(mondo.getPlayerkeys().contains(ptemp.getKey())){
									System.out.println(local_string.get("YES_KEY"));
									ptemp.setOpen(true);
									ptemp.setKey(null);
								}
								
								else System.out.println(local_string.get("NO_KEY"));
								
							
							}
							
							if(ptemp.isOpen()) {
								luogo_corrente=luogo_prossimo;
								System.out.println(local_string.get("CURRENT_GROUND")+luogo_corrente.getName());
								mondo.setDepositata(false);
								mondo.setProva_fatta(false);

							}
							

							else if(ptemp.getKey()==null) System.out.println(local_string.get("CLOSED_PASSAGE"));
					}
					}
					
					
				}while(scelta_dir!=0);
				
			}
			break;
			
			case 2:{
				
				Save save = new Save(mondo,luogo_corrente, local_string, values);
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
