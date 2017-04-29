package game;

import java.io.File;
import java.util.ArrayList;
import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Salva la sessione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String START_MSG="Benvenuto nel Castello del Mago Romolo!\n L'obiettivo del gioco è farti strada attraverso le sue stanze e raggiungere il suo studio,"
									+ "ovvero la stanza ";
	static final String START_MSG2= "Per riuscirci dovrai raccogliere chiavi di vari metalli e pesi\n e aprire dei passaggi, dando prova della tua memoria."
									+ "Inoltre il mago non riceve gente incolta: dovrai dimostrare la tua conoscenza attraverso dei quiz e ottenre abbastanza punti.\n"
									+ "Buona Fortuna!";
	static final String MSG_NO_CAST = "Attenzione, ci sono problemi con il cast del file!";
	static final String MSG_OK_FILE="File caricato correttamente";
	static final String MSG_NO_FILE="File non caricato correttamente";
	static final String FILE_EXISTS="Esiste già un file con lo stesso nome";
	static final String NO_GROUND= "Ti trovi davanti ad una finestra, non puoi andare oltre.";
	static final String CLOSED_PASSAGE="C'è un muro, non puoi andare oltre.";
	static final String KEY_PRESENT="E' presente una chiave di: ";
	static final String KEY_NEEDED="Questo passaggio necessita di una chiave di:";
	static final String NO_KEY="Non possiedi la chiave giusta. Prova un'altra direzione";
	static final String YES_KEY="Possiedi la chiave giusta, il passaggio si apre.";
	static final String CURRENT_GROUND="La stanza corrente è: ";
	static final String GET_KEY="Vuoi raccogliere la chiave?";
	static final String PUT_KEY="Vuoi depositare una chiave?";
	static final String TRIAL="E' presente una prova di: ";
	static final String TRIAL2=" Vuoi effettuarla?";
	static final String POINTS="Il punteggio corrente è ";
	static final String WEIGHT="Peso o numero massimo di chiavi trasportabili ecceduti";
	static final String CANT_PUT_KEY1="Non puoi depositare chiavi, prima muoviti in un'altra stanza (Sei nella stanza iniziale o c'è già un'altra chiave.)";
	static final String CANT_PUT_KEY2="Non possiedi alcuna chiave.";
	static final String GOT_KEY="Hai raccolto la chiave.";
	static final String END="Sei arrivato allo studio! Il Mago Romolo ti osserva e proclama: \n";
	static final String LOAD_LOCATION="Immettere il percorso assoluto del file da caricare";
	static final String SAVE_LOCATION="Immettere il percorso assoluto del file da salvare";
	static final String NO_OPZ="Opzione non definita";
	static final String WRONG="Risposta errata!";
	static final String RIGHT="Risposta corretta!";
	static final String INSUFF_POINTS="\"Non hai superato abbastaza prove: ritorna più tardi.\"";
	static final String ENOUGH_POINTS="\"Vedo che hai superato abbastanza prove. Accomodati.\" Hai vinto!";
	static final String LOADING="Vuoi caricare una sessione precedente?";
	static final int ALTEZZA = 3, LARGHEZZA = 3, PROFONDITA = 3;
	static final int START_H = 2, START_W=0, START_D=0;
	static final int END_H = 1, END_W=2, END_D=2;
	static final String[] PASSAGGI_APERTI={"000-100","100-200","200-210","210-220","220-120","020-021","021-121","121-221","221-211","011-001","202-212","212-112",
											"022-122"};
	static final String[] LUOGHI_CHIAVE={"220-Alluminio","000-Bronzo","001-Piombo","011-Oro","121-Platino","112-Rame","002-Stagno","012-Alluminio"};
	static final String[] PASSAGGI_CHIAVE={"120-020-Bronzo","021-011-Alluminio","101-201-Oro","201-211-Oro","201-202-Platino","112-012-Rame","002-012-Alluminio"};
	static final String[] LUOGHI_PROVE={"100-Qual è la formula chimica dell'acqua?-H2O-Scienza","221-In che anno inizio la seconda guerra mondiale?-1939-Storia", 
										"011-Quale popolo costruì il Partenone?-Greci-Arte","212-Come si chiama una reazione chimica che produce calore?-Esotermica-Scienza",};
	
	
	
	
			
	public static void main(String[] args) {
		
		int scelta;
		int peso_max=30;
		int numero_max=3;
		int punteggio_max=100;
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
		
		mondo= new World(ALTEZZA, LARGHEZZA, PROFONDITA);
		luogo_corrente= mondo.searchGround(START_H, START_W, START_D);
		luogo_corrente.setStart(true);
		mondo.searchGround(END_H, END_W, END_D).setEnd(true);
		
		
		if(!mondo.openPassages(PASSAGGI_APERTI)) return;
		if(!mondo.putKeyGrounds(LUOGHI_CHIAVE)) return;
		if(!mondo.putKeyPassages(PASSAGGI_CHIAVE)) return;
		if(!mondo.putTrialsGrounds(LUOGHI_PROVE)) return;
		
		
		}
		
		System.out.println(START_MSG+mondo.searchGround(END_H, END_W, END_D).getName());
		System.out.println(START_MSG2);
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
						if(mondo.getPoints()<punteggio_max) System.out.println(INSUFF_POINTS);
						else {
							System.out.println(ENOUGH_POINTS);
							return;
						}
					}
					//-------------------------------------------------------------------------------------------------------Recupero chiavi
					
					
					num_totale=mondo.getKeys().size();
					peso_totale=mondo.totWeight();
					
					if(luogo_corrente.getKey()!=null&&!mondo.isDepositata()) {
						Token key=luogo_corrente.getKey();
						
						System.out.println(KEY_PRESENT + key);
						if(peso_totale + key.getWeight() <= peso_max && num_totale + 1 <= numero_max){
							if(LeggiInput.doppiaScelta(GET_KEY)){
								mondo.getKeys().add(key);
								luogo_corrente.setKey(null);
								System.out.println(GOT_KEY);
							}
						}else System.out.println(WEIGHT);
					}
					//-------------------------------------------------------------------------------------------------------Deposito chiavi
					
					else if(!mondo.getKeys().isEmpty()&&!luogo_corrente.isEnd()&&!luogo_corrente.isStart()&&luogo_corrente.getKey()==null&&
							LeggiInput.doppiaScelta(PUT_KEY)
							){
						
						ArrayList<String> temp= new ArrayList<String>();
						for(Token a: mondo.getKeys()) temp.add(a.toString());
						String[] temp2= new String[temp.size()];
						temp2=temp.toArray(temp2);
						Menu elenco_chiavi = new Menu(temp2);
						int scelta_chiavi=elenco_chiavi.stampaSottoMenu();
						
				
						if(scelta_chiavi-1>=0&&scelta_chiavi<=temp.size()){
							
							Token key=mondo.getKeys().get(scelta_chiavi-1);
							luogo_corrente.setKey(key);
							mondo.getKeys().remove(key);
							System.out.println("La chiave scelta ï¿½ stata depositata");
							mondo.setDepositata(true);

						}
					}
					
					//--------------------------------------------------------------------------------------------------------Prova
					Trial trial= luogo_corrente.getTrial();
					if(!mondo.isProva_fatta()&&trial!=null&&LeggiInput.doppiaScelta(TRIAL+trial+TRIAL2)){
						String domanda=mondo.getQuestion(trial);
						LeggiInput.terminaRiga();
						String risposta=LeggiInput.riga(domanda);
						boolean correct= mondo.getAnswer(trial, domanda, risposta);
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
								
								if(mondo.getKeys().contains(ptemp.getKey())){
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
