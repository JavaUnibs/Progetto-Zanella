package game;

import java.io.File;
import java.util.ArrayList;
import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Deposita una chiave", "Salva la sessione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String MSG_NO_CAST = "Attenzione, ci sono problemi con il cast del file!";
	static final String MSG_OK_FILE="File caricato correttamente";
	static final String MSG_NO_FILE="File non caricato correttamente";
	static final String FILE_EXISTS="Esiste già un file con lo stesso nome";
	static final String NO_GROUND= "Sei ai confini del mondo";
	static final String CLOSED_PASSAGE="Questo passaggio è murato";
	static final String KEY_PRESENT="E' presente una chiave di: ";
	static final String KEY_NEEDED="Questo passaggio necessita di una chiave di:";
	static final String NO_KEY="Non possiedi la chiave giusta. Prova un'altra direzione";
	static final String YES_KEY="Possiedi la chiave giusta, il passaggio si apre.";
	static final String CURRENT_GROUND="Il luogo corrente è: ";
	static final String GET_KEY="Vuoi raccogliere la chiave?";
	static final String WEIGHT="Peso o numero massimo di chiavi trasportabili ecceduti";
	static final String CANT_PUT_KEY1="Non puoi depositare chiavi, prima muoviti in un'altra direzione (Sei in Start/End o c'è già una chiave)";
	static final String CANT_PUT_KEY2="Non possiedi alcuna chiave";
	static final String GOT_KEY="Hai raccolto la chiave";
	static final String END="Sei arrivato";
	static final String LOAD_LOCATION="Immettere il percorso assoluto del file da caricare";
	static final String SAVE_LOCATION="Immettere il percorso assoluto del file da salvare";
	static final String NO_OPZ="Opzione non definita";
	static final String LOADING="Vuoi caricare una sessione precedente?";
	static final int ALTEZZA = 3, LARGHEZZA = 3, PROFONDITA = 3;
	static final int START_H = 0, START_W=0, START_D=0;
	static final int END_H = 2, END_W=2, END_D=2;
	static final String[] PASSAGGI_APERTI={"000-010","010-110","010-020","020-120","220-221","221-222"};
	static final String[] LUOGHI_CHIAVE={"020-Alluminio"};
	static final String[] PASSAGGI_CHIAVE={"120-220-Alluminio"};
	
	
	
	
			
	public static void main(String[] args) {
		
		int scelta;
		int peso_max=50;
		int numero_max=5;
		
		Menu elenco = new Menu(MENU_PRINCIPALE);
		Menu elenco_dir= new Menu(MENU_DIREZIONI);
		World mondo = null;
		Ground luogo_corrente = null;
		
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
		else{
		
		mondo= new World(ALTEZZA, LARGHEZZA, PROFONDITA);
		luogo_corrente= mondo.searchGround(START_H, START_W, START_D);
		luogo_corrente.setStart(true);
		mondo.searchGround(END_H, END_W, END_D).setEnd(true);
		
		
		if(!mondo.openPassages(PASSAGGI_APERTI)) return;
		if(!mondo.putKeyGrounds(LUOGHI_CHIAVE)) return;
		if(!mondo.putKeyPassages(PASSAGGI_CHIAVE)) return;
		
		
		}
		


		

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
						return;
					}
					
					int peso_totale=0;
					int num_totale=mondo.getKeys().size();
					for(Token a: mondo.getKeys()) peso_totale=peso_totale+a.getWeight();
					
					if(luogo_corrente.getKey()!=null&&!mondo.isDepositata()&&peso_totale<=peso_max&&num_totale<=numero_max) {
						Token key=luogo_corrente.getKey();
						
						System.out.println(KEY_PRESENT+key);
						if(LeggiInput.doppiaScelta(GET_KEY)){
							mondo.getKeys().add(key);
							luogo_corrente.setKey(null);
							System.out.println(GOT_KEY);
						}
					}else if(!(peso_totale<=peso_max&&num_totale<=numero_max)) System.out.println(WEIGHT);

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

							}
							

							else if(ptemp.getKey()==null) System.out.println(CLOSED_PASSAGE);
					}
					}
					
					
				}while(scelta_dir>0);
				
			}
			break;
			
			
			case 2:{
				
				if(!mondo.getKeys().isEmpty()&&!luogo_corrente.isEnd()&&!luogo_corrente.isStart()&&luogo_corrente.getKey()==null){
					
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
						System.out.println("La chiave scelta è stata depositata");
						mondo.setDepositata(true);

					}
				}else if(mondo.getKeys().isEmpty()) System.out.println(CANT_PUT_KEY2);
				else System.out.println(CANT_PUT_KEY1);
					
				
				
			}
			break;
			
			case 3:{
				
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
