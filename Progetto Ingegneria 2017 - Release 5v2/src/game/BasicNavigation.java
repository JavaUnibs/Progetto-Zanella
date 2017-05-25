package game;

import java.util.HashMap;

import it.unibs.ing.myutility.Menu;

public class BasicNavigation extends Navigation{
	
	private static final long serialVersionUID = 2L;
	private final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	private BasicWorld mondo;
	private HashMap<String, String> local_string, common_string;
	private Menu elenco_dir;
	
	BasicNavigation(World mondo, HashMap<String, String> local_string, HashMap<String, String> common_string){
		elenco_dir= new Menu(MENU_DIREZIONI);
		this.mondo=(BasicWorld) mondo;
		this.local_string=local_string;
		this.common_string=common_string;
	}
	
	
	
	
	public Ground navigate(Ground luogo_corr) {
		BasicGround luogo_corrente= (BasicGround) luogo_corr;
		BasicGround luogo_prossimo;
		int scelta_dir;
		
		do{

			luogo_prossimo=luogo_corrente;
			
			if(luogo_corrente.isEnd()) {
				System.out.println(local_string.get("END"));
				return null;	
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
			
			default: System.out.println(common_string.get("NO_OPZ"));
			break;
			
			}
			
			if(luogo_prossimo==null) System.out.println(local_string.get("NO_GROUND"));
			else if(luogo_prossimo==luogo_corrente);
			else {
				
				BasicPassage ptemp=mondo.searchPassage(luogo_corrente, luogo_prossimo);
				if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
				else if(ptemp.isOpen()) luogo_corrente=luogo_prossimo;
				else System.out.println(local_string.get("CLOSED_PASSAGE"));
			}
			
		}while(scelta_dir!=0);
		
		
		return luogo_corrente;
	}
	


}
