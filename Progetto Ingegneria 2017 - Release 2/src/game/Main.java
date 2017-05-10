package game;

import java.util.ArrayList;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Deposita una chiave"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String START_MSG="Benvenuto nel Labirinto di Minosse. L'obiettivo del gioco è farti strada\nattraverso le sue stanze e raggiungere la sala del tesoro, "
			+ "ovvero la  ";
	static final String START_MSG2= "Per riuscirci dovrai raccogliere chiavi di vari metalli e pesi\ne aprire dei passaggi tra i vari saloni. "
			+ "\nBuona Fortuna!\n";
	static final String NO_GROUND= "Sei ai confini del labirinto, non puoi andare oltre.";
	static final String CLOSED_PASSAGE="Questo passaggio è murato, non puoi andare oltre";
	static final String KEY_PRESENT="E' presente una chiave di: ";
	static final String KEY_NEEDED="Questo passaggio necessita di una chiave di:";
	static final String NO_KEY="Non possiedi la chiave giusta. Prova un'altra direzione";
	static final String YES_KEY="Possiedi la chiave giusta, il passaggio si apre.";
	static final String CURRENT_GROUND="La sala corrente è: ";
	static final String GET_KEY="Vuoi raccogliere la chiave?";
	static final String CANT_PUT_KEY1="Non puoi depositare chiavi, prima muoviti in un'altra direzione (Sei nel luogo iniziale, finale o c'è già una chiave)";
	static final String CANT_PUT_KEY2="Non possiedi alcuna chiave";
	static final String GOT_KEY="Hai raccolto la chiave";
	static final String END="Sei finalmente arrivato alla sala del tesoro! Hai vinto il gioco!";
	static final String NO_OPZ="Opzione non definita";
	static final int ALTEZZA = 3, LARGHEZZA = 4, PROFONDITA = 4;
	static final int START_H = 1, START_W = 1, START_D = 0;
	static final int END_H = 0, END_W = 0, END_D = 2;
	static final String[] PASSAGGI_APERTI={"000-010","030-130","010-110","100-110","100-200","110-210",
			"210-220","220-230","230-130","201-101","111-101","001-101","001-011","011-021","021-121",
			"121-131","131-031","111-211","211-221","221-231","202-102","112-102","112-212","112-122",
			"122-222","222-232","232-132","132-133","122-022","022-032","022-012","203-103","203-213","213-223",
			"223-233","223-123","123-023","023-033","033-133"};
	static final String[] LUOGHI_CHIAVE={"100-Acciaio","120-Oro","000-Piombo","231-Ferro","111-Rame","212-Bronzo",
			"232-Stagno","022-Alluminio","233-Argento","003-Platino","023-Argento"};
	static final String[] PASSAGGI_CHIAVE={"200-201-Acciaio","120-020-Ferro","020-010-Piombo","020-030-Acciaio",
			"102-002-Oro","002-012-Platino","032-132-Alluminio","003-103-Argento","003-013-Bronzo","013-113-Stagno",
			"123-113-Stagno","123-133-Stagno","011-012-Rame"};
	
	
	
	
			
	public static void main(String[] args) {
		
		int scelta;
		boolean depositata=true;
		
		World mondo= new World(ALTEZZA, LARGHEZZA, PROFONDITA);
		
		Menu elenco = new Menu(MENU_PRINCIPALE);
		Menu elenco_dir= new Menu(MENU_DIREZIONI);
		
		Ground luogo_corrente= mondo.searchGround(START_H, START_W, START_D);
		Ground luogo_finale = mondo.searchGround(END_H, END_W, END_D);
		
		luogo_corrente.setStart(true);
		luogo_finale.setEnd(true);
		
		if(!mondo.openPassages(PASSAGGI_APERTI)) return;
		if(!mondo.putKeyGrounds(LUOGHI_CHIAVE)) return;
		if(!mondo.putKeyPassages(PASSAGGI_CHIAVE)) return;

		System.out.println(START_MSG+mondo.searchGround(END_H, END_W, END_D).getName());
		System.out.println(START_MSG2);

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
					
					if(luogo_corrente.getKey()!=null&&!depositata) {
						Token key=luogo_corrente.getKey();
						
						System.out.println(KEY_PRESENT+key);
						if(LeggiInput.doppiaScelta(GET_KEY)){
							mondo.getKeys().add(key);
							luogo_corrente.setKey(null);
							System.out.println(GOT_KEY);
						}
					}

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
								depositata=false;

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
						depositata=true;

					}
				}else if(mondo.getKeys().isEmpty()) System.out.println(CANT_PUT_KEY2);
				else System.out.println(CANT_PUT_KEY1);
					
				
				
			}
			break;
			
			default: System.out.println(NO_OPZ);
			break;
			}
		
		}while(scelta>0);


	}
	

	
	
	

}
