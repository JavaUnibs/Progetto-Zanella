package game;

import java.util.ArrayList;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione", "Deposita una chiave"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String NO_GROUND= "Sei ai confini del mondo";
	static final String CLOSED_PASSAGE="Questo passaggio � murato";
	static final String KEY_PRESENT="E' presente una chiave di: ";
	static final String KEY_NEEDED="Questo passaggio necessita di una chiave di:";
	static final String NO_KEY="Non possiedi la chiave giusta. Prova un'altra direzione";
	static final String YES_KEY="Possiedi la chiave giusta, il passaggio si apre.";
	static final String CURRENT_GROUND="Il luogo corrente �: ";
	static final String GET_KEY="Vuoi raccogliere la chiave?";
	static final String CANT_PUT_KEY1="Non puoi depositare chiavi, prima muoviti in un'altra direzione (Sei in Start/End o c'� gi� una chiave)";
	static final String CANT_PUT_KEY2="Non possiedi alcuna chiave";
	static final String GOT_KEY="Hai raccolto la chiave";
	static final String END="Sei arrivato";
	static final String NO_OPZ="Opzione non definita";
	static final int ALTEZZA = 4, LARGHEZZA = 4, PROFONDITA = 3;
	static final int START_H = 0, START_W = 2, START_D = 0;
	static final int END_H = 3, END_W = 1, END_D = 2;
	static final String[] PASSAGGI_APERTI={"310-210","320-220","320-330","330-230","200-100","220-120","230-130",
			"100-110","100-000","110-010","120-130","130-030","010-020","020-030","301-311","311-321","321-331",
			"321-221","201-101","211-221","221-231","231-131","101-001","111-121","121-131","121-021","302-312",
			"302-202","202-102","212-222","212-112","222-232","232-132","102-002","112-122","122-132","132-032",
			"002-012","012-022"};
	static final String[] LUOGHI_CHIAVE={"301-Acciaio","111-Acciaio","300-Oro","331-Piombo","000-Ferro","010-Rame","112-Bronzo",
			"332-Stagno","201-Alluminio","130-Argento","011-Platino"};
	static final String[] PASSAGGI_CHIAVE={"210-211-Rame","110-210-Ferro","021-031-Piombo","001-011-Acciaio",
			"312-322-Oro","022-032-Platino","200-300-Alluminio","032-031-Argento","332-232-Bronzo","322-222-Stagno",
			"211-201-Ferro","131-031-Piombo"};
	
	
	
	
			
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
						System.out.println("La chiave scelta � stata depositata");
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
