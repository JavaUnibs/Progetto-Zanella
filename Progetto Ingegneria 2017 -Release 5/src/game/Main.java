package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Salva la sessione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	
	static final int ALTEZZA_FW = 3, LARGHEZZA_FW = 4, PROFONDITA_FW = 4;
	static final int START_H_FW = 1, START_W_FW=1, START_D_FW=0;
	static final int END_H_FW = 0, END_W_FW=0, END_D_FW=2;

	static final int PESO_MAX_TRAS_FW=50;
	static final int NUM_MAX_TRAS_FW=5;
	static final int PESO_MAX_CHIAVE_FW=25;
	static final int PUNTI_FIN_FW=0;
	static final int PUNTI_MAX_PROVA_FW=0;
	static final int PUNTI_INIZIALI_FW=0;
	

	static final String[] PASSAGGI_APERTI_FW={"000-010","030-130","010-110","100-110","100-200","110-210",
			"210-220","220-230","230-130","201-101","111-101","001-101","001-011","011-021","021-121",
			"121-131","131-031","111-211","211-221","221-231","202-102","112-102","112-212","112-122",
			"122-222","222-232","232-132","132-133","122-022","022-032","022-012","203-103","203-213","213-223",
			"223-233","223-123","123-023","023-033","033-133"};
	static final String[] CHIAVI_FW={"5-Rame","10-Ferro","15-Acciaio","15-Piombo","10-Alluminio","15-Bronzo","15-Argento","5-Stagno",
			"25-Oro","25-Platino"};
	static final String[] LUOGHI_CHIAVE_FW={"100-Acciaio","120-Oro","000-Piombo","231-Ferro","111-Rame","212-Bronzo",
			"232-Stagno","022-Alluminio","233-Argento","003-Platino","023-Argento"};
	static final String[] PASSAGGI_CHIAVE_FW={"200-201-Acciaio","120-020-Ferro","020-010-Piombo","020-030-Acciaio",
			"102-002-Oro","002-012-Platino","032-132-Alluminio","003-103-Argento","003-013-Bronzo","013-113-Stagno",
			"123-113-Stagno","123-133-Stagno","011-012-Rame"};
		
	public static void main(String[] args) {
		
		HashMap<String, String> common_string=null;
		HashMap<String, String> local_string=null;
		HashMap<String, String[]> values=null;
		
			try {
				common_string=new TxtToHashmap("files\\COMMON_STRINGS.txt").convertToString();
			} catch (IOException e1) {
				e1.printStackTrace();
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
			   }
			  catch (ClassCastException e)
			   {
				 System.out.println(common_string.get("MSG_NO_CAST"));
				}
			   finally
				{
			      if ( (mondo != null) && (luogo_corrente != null) )
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
			ArrayList<File> fileList= FolderExplorer.listFiles("files");
			for(File s: fileList) if(!s.isDirectory()) fileList.remove(s);
			
			for(int y=0;y<fileList.size();y++){
				System.out.println(y+") "+fileList.get(y).getName()+"\n");
			}
			
			int num_mondo=LeggiInput.intero(common_string.get("CHOOSE_WORLD"));
			try{
				nome_mondo=fileList.get(num_mondo);
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println(common_string.get("NO_WORLD"));
				return;
			}
			
			try {
				local_string=new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\local_string.txt").convertToString();
				values= new TxtToHashmap(nome_mondo.getAbsolutePath()+"\\values.txt").convertToArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			SetupWorld setup=new SetupWorld(values);
			setup.initialize();
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
		System.out.println(local_string.get("START_MSG")+mondo.searchGround(convertValues(values, "END_H"), convertValues(values, "END_W"), convertValues(values, "END_D")).getName());
		System.out.println(local_string.get("START_MSG2"));
		
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
						if(!mondo.getTrials().isEmpty() && mondo.getPoints()<mondo.getPunteggio_finale()) System.out.println(local_string.get("INSUFF_POINTS"));
						else if(!mondo.getTrials().isEmpty() && mondo.getPoints()>=mondo.getPunteggio_finale()) System.out.println(local_string.get("ENOUGH_POINTS"));
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
					
					
				}while(scelta_dir>0);
				
			}
			break;
			
			case 2:{
				
				Save save = new Save(mondo,luogo_corrente, local_string);
				LeggiInput.terminaRiga();
				File fgame = new File(LeggiInput.riga(common_string.get("SAVE_LOCATION")));
				if(fgame.exists()) {
					System.out.println(common_string.get("FILE_EXISTS")); 
					break;
				}
				else
		        SalvataggioFile.salvaOggetto(fgame, save);	
			}
			break;
			
			default: System.out.println(common_string.get("NO_OPZ"));
			break;
			}
		
		}while(scelta>0);


	}
	

	static int convertValues(HashMap<String, String[]> map, String key){
		return Integer.parseInt(map.get(key)[0]);
		
	}
	
	

}
