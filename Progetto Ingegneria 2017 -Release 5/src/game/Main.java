package game;

import java.io.File;
import java.util.ArrayList;
import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Salva la sessione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String MSG_NO_CAST = "Attenzione, ci sono problemi con il cast del file!";
	static final String MSG_OK_FILE="File caricato correttamente";
	static final String MSG_NO_FILE="File non caricato correttamente";
	static final String FILE_EXISTS="Esiste gi� un file con lo stesso nome";
	static final String NO_GROUND= "Sei ai confini del mondo";
	static final String CLOSED_PASSAGE="Questo passaggio � murato";
	static final String KEY_PRESENT="E' presente una chiave di: ";
	static final String KEY_NEEDED="Questo passaggio necessita di una chiave di:";
	static final String NO_KEY="Non possiedi la chiave giusta. Prova un'altra direzione";
	static final String YES_KEY="Possiedi la chiave giusta, il passaggio si apre.";
	static final String CURRENT_GROUND="Il luogo corrente �: ";
	static final String GET_KEY="Vuoi raccogliere la chiave?";
	static final String PUT_KEY="Vuoi depositare una chiave?";
	static final String TRIAL="E' presente una prova di: ";
	static final String TRIAL2=" Vuoi effettuarla?";
	static final String POINTS="Il punteggio corrente � ";
	static final String WEIGHT="Peso o numero massimo di chiavi trasportabili ecceduti";
	static final String CANT_PUT_KEY1="Non puoi depositare chiavi, prima muoviti in un'altra direzione (Sei in Start/End o c'� gi� una chiave)";
	static final String CANT_PUT_KEY2="Non possiedi alcuna chiave";
	static final String GOT_KEY="Hai raccolto la chiave";
	static final String END="Sei arrivato";
	static final String LOAD_LOCATION="Immettere il percorso assoluto del file da caricare";
	static final String SAVE_LOCATION="Immettere il percorso assoluto del file da salvare";
	static final String NO_OPZ="Opzione non definita";
	static final String WRONG="Risposta errata";
	static final String RIGHT="Risposta corretta";
	static final String INSUFF_POINTS="Punti insufficienti per finire il gioco";
	static final String LOADING="Vuoi caricare una sessione precedente?";
	static final String CHOOSE_WORLD="Scegli uno dei mondi disponibili";
	static final String NO_WORLD="Il mondo selezionato non esiste";
	static final String MODIFY_WORLD="Vuoi modificare i parametri del mondo scelto?";
	
	static final int ALTEZZA_FW = 3, LARGHEZZA_FW = 4, PROFONDITA_FW = 4;
	static final int START_H_FW = 1, START_W_FW=1, START_D_FW=0;
	static final int END_H_FW = 0, END_W_FW=0, END_D_FW=2;
	
	static final int ALTEZZA_NG = 3, LARGHEZZA_NG = 3, PROFONDITA_NG = 3;
	static final int START_H_NG = 0, START_W_NG=0, START_D_NG=0;
	static final int END_H_NG = 2, END_W_NG=2, END_D_NG=1;
	
	static final int PESO_MAX_TRAS_FW=50;
	static final int NUM_MAX_TRAS_FW=5;
	static final int PESO_MAX_CHIAVE_FW=25;
	static final int PUNTI_FIN_FW=100;
	static final int PUNTI_MAX_PROVA_FW=50;
	static final int PUNTI_INIZIALI_FW=10;
	
	static final int PESO_MAX_TRAS_NG=100;
	static final int NUM_MAX_TRAS_NG=5;
	static final int PESO_MAX_CHIAVE_NG=100;
	static final int PUNTI_FIN_NG=100;
	static final int PUNTI_MAX_PROVA_NG=50;
	static final int PUNTI_INIZIALI_NG=10;
	
	
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
	
	static final String[] PASSAGGI_APERTI_NG={"000-010","010-020","020-120","120-110","110-100"};
	static final String[] CHIAVI_NG={"30-Alluminio","25-Bronzo"};
	static final String[] LUOGHI_CHIAVE_NG={"110-Alluminio", "100-Bronzo"};
	static final String[] PASSAGGI_CHIAVE_NG={"120-220-Alluminio","220-221-Bronzo"};
	static final String[] PROVE_NG={"50-Scienza"};
	static final String[] QUIZ1_NG={"Scienza","Qual � la formula chimica dell'acqua?-H2O", "Di che metallo � fatto il bronzo oltre al rame?-Stagno"};
	static final String[] LUOGHI_PROVE_NG={"020","010"};
	
	static final String[] MONDI={"Fancy World", "Fancy World NG"};
	
	
	
	
	
			
	public static void main(String[] args) {
		
		int scelta;
		int peso_totale;
		int num_totale;
		
		Menu elenco = new Menu(MENU_PRINCIPALE);
		Menu elenco_dir= new Menu(MENU_DIREZIONI);
		World mondo = null;
		Ground luogo_corrente = null;
		
		//---------------------------------------------------------------------------------------------Scelta di caricamento
		if(LeggiInput.doppiaScelta(LOADING)){
			
			LeggiInput.terminaRiga();
			String percorso= LeggiInput.riga(LOAD_LOCATION);
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
			   }
			  catch (ClassCastException e)
			   {
				 System.out.println(MSG_NO_CAST);
				}
			   finally
				{
			      if ( (mondo != null) && (luogo_corrente != null) )
				    {
					 System.out.println(MSG_OK_FILE);
					 caricamentoRiuscito = true;
					 }
				  }
				
			 }
				
			if (!caricamentoRiuscito)
			   {
				System.out.println(MSG_NO_FILE);
                return;
			    }
			
		}
		//-----------------------------------------------------------------------------------------------Alternativa a caricamento (Creazione nuovo gioco)
		else{
			String nome_mondo;
			for(int y=0;y<MONDI.length;y++){
				System.out.println(y+") "+MONDI[y]+"\n");
			}
			
			int num_mondo=LeggiInput.intero(CHOOSE_WORLD);
			try{
				nome_mondo=MONDI[num_mondo];
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println(NO_WORLD);
				return;
			}
			
			SetupWorld setup=null;
			switch(nome_mondo){
			case "Fancy World":{
				mondo= new World(ALTEZZA_FW, LARGHEZZA_FW, PROFONDITA_FW, PESO_MAX_TRAS_FW, NUM_MAX_TRAS_FW, PESO_MAX_CHIAVE_FW, PUNTI_FIN_FW, PUNTI_MAX_PROVA_FW, PUNTI_INIZIALI_FW);
				setup=new SetupWorld(mondo);
				setup.openPassages(PASSAGGI_APERTI_FW);
				setup.addKeys(CHIAVI_FW);
				setup.putKeyGrounds(LUOGHI_CHIAVE_FW);
				setup.putKeyPassages(PASSAGGI_CHIAVE_FW);
				luogo_corrente=mondo.searchGround(START_H_FW, START_W_FW, START_D_FW);
				luogo_corrente.setStart(true);
				mondo.searchGround(END_H_FW, END_W_FW, END_D_FW).setEnd(true);
				
			}
			break;
			
			case "Fancy World NG":{
				mondo= new World(ALTEZZA_NG, LARGHEZZA_NG, PROFONDITA_NG, PESO_MAX_TRAS_NG, NUM_MAX_TRAS_NG, PESO_MAX_CHIAVE_NG, PUNTI_FIN_NG, PUNTI_MAX_PROVA_NG, PUNTI_INIZIALI_NG);
				setup=new SetupWorld(mondo);
				setup.openPassages(PASSAGGI_APERTI_NG);
				setup.addKeys(CHIAVI_NG);
				setup.putKeyGrounds(LUOGHI_CHIAVE_NG);
				setup.putKeyPassages(PASSAGGI_CHIAVE_NG);
				setup.addTrials(PROVE_NG);
				setup.addQA(QUIZ1_NG);
				setup.putTrialsGrounds(LUOGHI_PROVE_NG);
				luogo_corrente=mondo.searchGround(START_H_NG, START_W_NG, START_D_NG);
				luogo_corrente.setStart(true);
				mondo.searchGround(END_H_NG, END_W_NG, END_D_NG).setEnd(true);
			}
			break;
			}
			
			if(LeggiInput.doppiaScelta(MODIFY_WORLD)){
				if(setup!=null){
					InterfaceSetupWorld gui= new InterfaceSetupWorld(setup);
					gui.initialize();
				}
				else System.out.println("Errore nel setup");
				
			}
		
		
		}
		
        //-------------------------------------------------------------------------------------------------Inizio gioco
		do {
			System.out.println(CURRENT_GROUND+luogo_corrente.getName());
			scelta=elenco.stampaMenu();
		
			switch(scelta){
		
			case 0: {};	
			break;
			case 1: {
				
				int scelta_dir;
				
				
				do {
					
					
					Ground luogo_prossimo=luogo_corrente;
					
					if(luogo_corrente.isEnd()) {
						System.out.println(END);
						if(!mondo.getTrials().isEmpty() && mondo.getPoints()<mondo.getPunteggio_finale()) System.out.println(INSUFF_POINTS);
						else return;
					}
					//-------------------------------------------------------------------------------------------------------Recupero chiavi
					
					
					num_totale=mondo.getPlayerkeys().size();
					peso_totale=mondo.totWeight();
					
					if(luogo_corrente.getKey()!=null&&!mondo.isDepositata()) {
						Token key=luogo_corrente.getKey();
						
						System.out.println(KEY_PRESENT + key);
						if(LeggiInput.doppiaScelta(GET_KEY)){   //LeggiInput.doppiaScelta(GET_KEY)
							if(peso_totale + key.getWeight() <= mondo.getPeso_max_trasportabile() && num_totale + 1 <= mondo.getNumero_max_trasportabile()){											//peso_totale + key.getWeight() <= peso_max && num_totale + 1 <= numero_max
								mondo.getPlayerkeys().add(key);
								luogo_corrente.setKey(null);
								System.out.println(GOT_KEY);
							}
							else System.out.println(WEIGHT);
						}
					}
					
					//-------------------------------------------------------------------------------------------------------Deposito chiavi
					
					else if(!mondo.getPlayerkeys().isEmpty()&&!luogo_corrente.isEnd()&&!luogo_corrente.isStart()&&luogo_corrente.getKey()==null&&
							LeggiInput.doppiaScelta(PUT_KEY)
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
							System.out.println("La chiave scelta � stata depositata");
							mondo.setDepositata(true);

						}
					}
					
					//--------------------------------------------------------------------------------------------------------Prova
					Trial trial= luogo_corrente.getTrial();
					if(!mondo.isProva_fatta()&&trial!=null&&LeggiInput.doppiaScelta(TRIAL+trial+TRIAL2)){
						String domanda=trial.getQuestion();
						LeggiInput.terminaRiga();
						String risposta=LeggiInput.riga(domanda);
						boolean correct= trial.getAnswer(domanda, risposta);
						if(correct) System.out.println(RIGHT);
						else System.out.println(WRONG);
						mondo.updatePoint(trial, correct);
						mondo.setProva_fatta(true);
						System.out.println(POINTS+mondo.getPoints());
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
					
					default: System.out.println(NO_OPZ);
					break;
					
					}
					
					
					if(luogo_prossimo==null) System.out.println(NO_GROUND);
					else if(luogo_prossimo==luogo_corrente);
					else {
						
						Passage ptemp=mondo.searchPassage(luogo_corrente, luogo_prossimo);
						if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
						
						else {
							
							if(ptemp.getKey()!=null){
								System.out.println(KEY_NEEDED+ptemp.getKey());
								
								if(mondo.getPlayerkeys().contains(ptemp.getKey())){
									System.out.println(YES_KEY);
									ptemp.setOpen(true);
									ptemp.setKey(null);
								}
								
								else System.out.println(NO_KEY);
								
							
							}
							
							if(ptemp.isOpen()) {
								luogo_corrente=luogo_prossimo;
								System.out.println(CURRENT_GROUND+luogo_corrente.getName());
								mondo.setDepositata(false);
								mondo.setProva_fatta(false);

							}
							

							else if(ptemp.getKey()==null) System.out.println(CLOSED_PASSAGE);
					}
					}
					
					
				}while(scelta_dir>0);
				
			}
			break;
			
			case 2:{
				
				Save save = new Save(mondo,luogo_corrente);
				LeggiInput.terminaRiga();
				File fgame = new File(LeggiInput.riga(SAVE_LOCATION));
				if(fgame.exists()) {
					System.out.println(FILE_EXISTS); 
					break;
				}
				else
		        SalvataggioFile.salvaOggetto(fgame, save);	
			}
			break;
			
			default: System.out.println(NO_OPZ);
			break;
			}
		
		}while(scelta>0);


	}
	

	
	
	

}
