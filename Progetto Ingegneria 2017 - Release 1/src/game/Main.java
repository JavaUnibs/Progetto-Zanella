package game;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String START_MSG="Benvenuto nel Labirinto di Minosse. L'obiettivo del gioco è farti strada\nattraverso le sue stanze e raggiungere la sala del tesoro, "
			+ "ovvero la  ";
	static final String START_MSG2= "\nBuona Fortuna!\n";
	static final String NO_GROUND= "Sei ai confini del labirinto, non puoi andare oltre.";
	static final String CLOSED_PASSAGE="Questo passaggio è murato, non puoi andare oltre";
	static final String CURRENT_GROUND="La sala corrente è: ";
	static final String END="Sei finalmente arrivato alla sala del tesoro! Hai vinto il gioco!";
	static final String NO_OPZ="Opzione non definita";
	static final int ALTEZZA = 3, LARGHEZZA = 4, PROFONDITA = 4;
	static final int START_H = 1, START_W = 1, START_D = 0;
	static final int END_H = 0, END_W = 0, END_D = 2;
	static final String[] PASSAGGI_APERTI={"000-010","030-130","020-120","010-110","100-110","100-200","110-210",
			"210-220","220-230","230-130","200-201","201-101","111-101","001-101","001-011","011-021","021-121",
			"121-131","131-031","111-211","211-221","221-231","202-102","112-102","112-212","112-122",
			"122-222","222-232","232-132","132-133","122-022","022-032","022-012","203-103","203-213","213-223",
			"223-233","223-123","123-023","023-033","033-133","200-201","120-020","020-010","020-030","102-002",
			"002-012","032-132","003-103","003-013","013-113","123-113","123-133","011-012"};
	
	
	
	
			
	public static void main(String[] args) {
		
		int scelta;
		
		World mondo= new World(ALTEZZA, LARGHEZZA, PROFONDITA);
		
		Menu elenco = new Menu(MENU_PRINCIPALE);
		Menu elenco_dir= new Menu(MENU_DIREZIONI);
		
		Ground luogo_corrente= mondo.searchGround(START_H, START_W, START_D);
		Ground luogo_finale = mondo.searchGround(END_H, END_W, END_D);
		
		luogo_corrente.setStart(true);
		luogo_finale.setEnd(true);
		
		if(!mondo.openPassages(PASSAGGI_APERTI)) return;

		
		System.out.println(START_MSG+mondo.searchGround(END_H, END_W, END_D).getName());
		System.out.println(START_MSG2);
		
		do {
			
			scelta=elenco.stampaMenu();
		
			switch(scelta){
		
			case 0: {};	
			break;
			case 1: {
				int scelta_dir;
				
				do {
					
					Ground luogo_prossimo=luogo_corrente;
					System.out.println(CURRENT_GROUND+luogo_corrente.getName());
					
					if(luogo_corrente.isEnd()) {
						System.out.println(END);
						return;
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
						else if(ptemp.isOpen()) luogo_corrente=luogo_prossimo;
						else System.out.println(CLOSED_PASSAGE);
					}
					
					
				}while(scelta_dir>0);
				
			}break;
			
			default: System.out.println(NO_OPZ);
			break;
			}
		
		}while(scelta>0);


	}
	

	
	
	

}
