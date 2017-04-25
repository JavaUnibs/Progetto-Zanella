package game;

import it.unibs.ing.myutility.*;

public class Main {	
	
	
	static final String[] MENU_PRINCIPALE= {"Vai in una direzione"}; 
	static final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	static final String NO_LUOGO= "Sei ai confini del mondo";
	static final String PASSAGGIO_CHIUSO="Questo passaggio è murato";
	static final String LUOGO_CORR="Il luogo corrente è: ";
	static final String END="Sei arrivato";
	static final String NO_OPZ="Opzione non definita";
	static final int ALTEZZA = 2, LARGHEZZA = 2, PROFONDITA = 3;
	static final int START_H = 0, START_W=0, START_D=0;
	static final int END_H = 1, END_W=1, END_D=2;
	static final String[] PASSAGGI_APERTI={"000-010","010-110","110-111","111-112"};
	
	
	
	
			
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

		

		do {
			
			scelta=elenco.stampaMenu();
		
			switch(scelta){
		
			case 0: {};	
			break;
			case 1: {
				int scelta_dir;
				
				do {
					
					Ground luogo_prossimo=luogo_corrente;
					System.out.println(LUOGO_CORR+luogo_corrente.getName());
					
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
					
					
					if(luogo_prossimo==null) System.out.println(NO_LUOGO);
					else if(luogo_prossimo==luogo_corrente);
					else {
						
						Passage ptemp=mondo.searchPassage(luogo_corrente, luogo_prossimo);
						if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
						else if(ptemp.isOpen()) luogo_corrente=luogo_prossimo;
						else System.out.println(PASSAGGIO_CHIUSO);
					}
					
					
				}while(scelta_dir>0);
				
			}break;
			
			default: System.out.println(NO_OPZ);
			break;
			}
		
		}while(scelta>0);


	}
	

	
	
	

}
