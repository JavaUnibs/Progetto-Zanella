package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import it.unibs.ing.myutility.*;


public class ModifyMediumWorld extends ModifyWorld{
	
private final String[] menu={"Pesi delle chiavi","Tipi di chiavi", "Limite superiore del peso di una chiave", "Numero massimo di chiavi trasportabili", 
			"Peso massimo di chiavi trasportabili"};
private final String[] menu_addremove={"Aggiungi", "Rimuovi"};
private final String INSERT_NAME_KEY="Inserire il nome della chiave";
private final String INSERT_WEIGHT_KEY="Inserire il peso della chiave";
private final String OK_MODIFY="Modifica effettuata";
private final String NO_KEY="Non esistono chiavi con il nome inserito";
private final String NEGATIVE_VALUE="Il valore inserito non è valido";
private final String OVER_THE_LIMIT="Il valore inserito è maggiore del limite superiore del peso di una chiave";
private final String ADDREMOVEKEY="Vuoi aggiungere o rimuovere una chiave?";
private final String KEY_ADDED="Chiave aggiunta";
private final String KEY_REMOVED="Chiave rimossa";
private final String EXISTS_KEY="Esiste già una chiave con lo stesso nome";
private final String LIMIT="Inserire il nuovo limite";
private final String WARNING_LIMIT="I valori superiori al limite verranno troncati";
private final String MODIFY_OK="Modifica effettuata";
private MediumWorld mondo;
	
	ModifyMediumWorld(MediumWorld mondo){
		this.mondo=mondo;
	}


	public void initialize() {
		HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap=keepTrackKeys();
		Set<ArrayList<MediumGround>> groundSet = keyMap.keySet();
		String start="Questo mondo ha i seguenti parametri: \n";
		String tipi_chiavi="Tipi di chiavi con relativi pesi: \n"+mondo.getKeytypes().toString();
		String peso_max_chiave="Limite superiore del peso di una chiave: "+mondo.getPeso_max_chiave()+"\n";
		String num_max_chiavi="Numero massimo di chiavi trasportabili: "+mondo.getNumero_max_trasportabile()+"\n";
		String peso_max_trasp="Peso massimo trasportabile: "+mondo.getPeso_max_trasportabile()+"\n";
		String modifica="Scegli i parametri da modificare";
		System.out.println(start+tipi_chiavi+peso_max_chiave+num_max_chiavi+peso_max_trasp+modifica);
		
		Menu menu_modifica= new Menu(menu);
		int scelta;
		
		do{
			scelta=menu_modifica.stampaMenu();
			switch(scelta){
			//--------------------------------------------------------------------
			case 1:{         
																												//modifica dei pesi delle chiavi
				System.out.println("Tipi di chiavi con relativi pesi: \n"+mondo.getKeytypes().toString());
				LeggiInput.terminaRiga();
				modifyKeyWeights();
					
			}
			break;
			//-----------------------------------------------------------------------------
			case 2:{	
			
			System.out.println("Tipi di chiavi con relativi pesi: \n"+mondo.getKeytypes().toString());
			System.out.println(ADDREMOVEKEY);
			Menu sottomenu= new Menu(menu_addremove);
			ArrayList<Token> keys= mondo.getKeytypes();
			
			int scelta_2=sottomenu.stampaSottoMenu();
				switch(scelta_2){
				
				case 1:{  		
					
					LeggiInput.terminaRiga();
					addKey(groundSet, keys, keyMap);	
				}
				break;
				
				case 2:{															
					LeggiInput.terminaRiga();
					removeKey(groundSet, keys, keyMap);
				}
				break;
				
				case 0: break;
				default: break;
				}
			
			}
			break;
			//-----------------------------------------------------------------------------------
			case 3:{                  
																					
				System.out.println("Limite superiore del peso di una chiave: "+mondo.getPeso_max_chiave()+"\n");
				modifyKeyWeightLimit();
				
			}
			break;
			//---------------------------------------------------------------------------------------
			case 4:{
																											
				System.out.println("Numero massimo di chiavi trasportabili: "+mondo.getNumero_max_trasportabile()+"\n");
				modifyMaxTotalKeyNumber();
			}
			break;
			//---------------------------------------------------------------------------------------
			case 5:{
																												
				System.out.println("Peso massimo trasportabile: "+mondo.getPeso_max_trasportabile()+"\n");
				modifyMaxTotalKeyWeight();
			}
			break;
			
			case 0: break;
			default: break;
		}
			
		}while(scelta!=0);
		
	}
	
	private HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keepTrackKeys(){   
		
		HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> map = new HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>>();
		for(Token t: mondo.getKeytypes()){
			ArrayList<MediumGround> grounds= new ArrayList<MediumGround>();
			ArrayList<MediumPassage> passages= new ArrayList<MediumPassage>();
			for(MediumGround g: mondo.getGrounds()){
				if(g.getKey()==t) grounds.add(g);
			}
			for(MediumPassage p:mondo.getPassages()){
				if(p.getKey()==t) passages.add(p);
			}
			map.put(grounds, passages);
		}
		return map;	
	}
	

	private void applyKeepTrackKeys(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		Token tempkey=null;
		for(ArrayList<MediumGround> array: groundSet){
			if(!keys.isEmpty()){
				int rnd=RandomValues.ranIntLimite(0, keys.size()-1);
				tempkey=keys.get(rnd);
			}
			
			for(MediumGround g: array) g.setKey(tempkey);
			for(MediumPassage p: keyMap.get(array)) {
				if(keys.isEmpty()) p.setOpen(true);
				else p.setOpen(false);
				p.setKey(tempkey);
			}
		}	
	}
	
	private ArrayList<Token> invalidKeysWeight(int max_weight){                    
		ArrayList<Token> temp= new ArrayList<Token>();
		for(Token a: mondo.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	private void modifyKeyWeights(){
		Token key=mondo.searchKeyTypes(LeggiInput.riga(INSERT_NAME_KEY));
		if(CheckValues.noElement(key, NO_KEY)) return;
		else {
			int weight=Integer.parseInt(LeggiInput.riga(INSERT_WEIGHT_KEY));
			if(CheckValues.isNegative(weight, NEGATIVE_VALUE)) return;
			else if (CheckValues.isOverLimit(weight, mondo.getPeso_max_chiave(), OVER_THE_LIMIT)) return;
			else{
				key.setWeight(weight);
				System.out.println(OK_MODIFY);
			}
		}
	}
	
	private void addKey(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		String name= LeggiInput.riga(INSERT_NAME_KEY);
		if(CheckValues.existsElement(mondo.searchKeyTypes(name), EXISTS_KEY)) return;
		int weight=LeggiInput.intero(INSERT_WEIGHT_KEY);
		if(CheckValues.isNegative(weight, NEGATIVE_VALUE)) return;
		else if (CheckValues.isOverLimit(weight, mondo.getPeso_max_chiave(), OVER_THE_LIMIT)) return;
		else {
			keys.add(new Token(weight, name));              							
			applyKeepTrackKeys(groundSet, keys, keyMap);
			System.out.println(KEY_ADDED);	
		}
	}
	
	private void removeKey(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		Token key=mondo.searchKeyTypes(LeggiInput.riga(INSERT_NAME_KEY));
		if(CheckValues.noElement(key, NO_KEY)) return;
		else keys.remove(key);
		applyKeepTrackKeys(groundSet, keys, keyMap);
		System.out.println(KEY_REMOVED);
	}
	
	private void modifyKeyWeightLimit(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else{
			mondo.setPeso_max_chiave(limit);
			System.out.println(WARNING_LIMIT);
			ArrayList<Token> keys =invalidKeysWeight(limit);
			for(Token k: keys) if(k.getWeight()>limit) k.setWeight(limit);
			System.out.println(MODIFY_OK);	
		}
	}
	
	private void modifyMaxTotalKeyNumber(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			mondo.setNumero_max_trasportabile(limit);
			System.out.println(MODIFY_OK);
		}
	}
	
	private void modifyMaxTotalKeyWeight(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			mondo.setPeso_max_trasportabile(limit);
			System.out.println(MODIFY_OK);
		}
	}
	

	

}
