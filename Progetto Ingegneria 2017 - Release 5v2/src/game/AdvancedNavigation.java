package game;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.myutility.LeggiInput;
import it.unibs.ing.myutility.Menu;

public class AdvancedNavigation extends Navigation{
	
	private static final long serialVersionUID = 2L;
	private final String[] MENU_DIREZIONI = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	private AdvancedWorld mondo;
	private HashMap<String, String> local_string, common_string;
	private Menu elenco_dir;
	
	AdvancedNavigation(World mondo, HashMap<String, String> local_string, HashMap<String, String> common_string){
		elenco_dir= new Menu(MENU_DIREZIONI);
		this.mondo=(AdvancedWorld) mondo;
		this.local_string=local_string;
		this.common_string=common_string;
	}

	
	public Ground navigate(Ground luogo_corr) {
		AdvancedGround luogo_corrente= (AdvancedGround) luogo_corr;
		AdvancedGround luogo_prossimo;
		int scelta_dir;
		
		do{
			luogo_prossimo=luogo_corrente;
			
			if(luogo_corrente.isEnd()) {
				System.out.println(local_string.get("END"));
				if(mondo.getTrials().isEmpty()) 
					return null;
				else {
					if(!mondo.getTrials().isEmpty() && mondo.getPoints()<mondo.getPunteggio_finale()) 
						System.out.println(local_string.get("INSUFF_POINTS"));
					else if(!mondo.getTrials().isEmpty() && mondo.getPoints()>=mondo.getPunteggio_finale()) {
							System.out.println(local_string.get("ENOUGH_POINTS"));
							return null;
					}
				}
			}
			
			//----------------------------------------------------------------------------------------------------
	
			if(luogo_corrente.getKey()!=null&&!mondo.isDepositata()) {
				retrieveKey(luogo_corrente);
			}
			
			//-------------------------------------------------------------------------------------------
			
			else if(!mondo.getPlayerkeys().isEmpty()&&!luogo_corrente.isEnd()&&!luogo_corrente.isStart()&&luogo_corrente.getKey()==null&&
					LeggiInput.doppiaScelta(local_string.get("PUT_KEY"))
					){
						depositKey(luogo_corrente);
			}
			//-------------------------------------------------------------------------------------------------------------------------
			
			attemptTrial(luogo_corrente);
			
			
			//--------------------------------------------------------------------------------------------
			
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
			
			//----------------------------------------------------------------------------------------------------------------------------------
			attemptEntry(luogo_corrente, luogo_prossimo);
			
		}while(scelta_dir!=0);
		
		return luogo_corrente;
	}

	private void retrieveKey(AdvancedGround luogo_corrente){
		int peso_totale=mondo.totWeight();
		int num_totale=mondo.getPlayerkeys().size();
		
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
	
	private void depositKey(AdvancedGround luogo_corrente){
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
	
	private void attemptTrial(AdvancedGround luogo_corrente){
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
	}
	
	private void attemptEntry(AdvancedGround luogo_corrente, AdvancedGround luogo_prossimo){
		if(luogo_prossimo==null) System.out.println(local_string.get("NO_GROUND"));
		else if(luogo_prossimo==luogo_corrente);
		else {
				MediumPassage ptemp=mondo.searchPassage(luogo_corrente, luogo_prossimo);
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
	}
}
