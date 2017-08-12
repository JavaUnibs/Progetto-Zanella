package game.Medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import game.Token;
import game.Abstract.ModifyWorld;
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
private MediumWorld world;
	
	ModifyMediumWorld(MediumWorld world){
		this.world=world;
	}


	public void initialize() {
		HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap=keepTrackKeys();
		Set<ArrayList<MediumGround>> groundSet = keyMap.keySet();
		String start="Questo mondo ha i seguenti parametri: \n";
		String key_types="Tipi di chiavi con relativi pesi: \n"+world.getKeytypes().toString();
		String max_key_weight="Limite superiore del peso di una chiave: "+world.getMax_key_weight()+"\n";
		String max_transportable_key_number="Numero massimo di chiavi trasportabili: "+world.getMax_transportable_keys_number()+"\n";
		String max_transportable_key_weight="Peso massimo trasportabile: "+world.getMax_transportable_keys_weight()+"\n";
		String modify_choice="Scegli i parametri da modificare";
		System.out.println(start+key_types+max_key_weight+max_transportable_key_number+max_transportable_key_weight+modify_choice);
		
		Menu modify_menu= new Menu(menu);
		int choice;
		
		do{
			choice=modify_menu.stampaMenu();
			switch(choice){
			//--------------------------------------------------------------------
			case 1:{         
																												//modifica dei pesi delle chiavi
				System.out.println("Tipi di chiavi con relativi pesi: \n"+world.getKeytypes().toString());
				LeggiInput.terminaRiga();
				modifyKeyWeights();
					
			}
			break;
			//-----------------------------------------------------------------------------
			case 2:{	
			
			System.out.println("Tipi di chiavi con relativi pesi: \n"+world.getKeytypes().toString());
			System.out.println(ADDREMOVEKEY);
			Menu sottomenu= new Menu(menu_addremove);
			ArrayList<Token> keys= world.getKeytypes();
			
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
																					
				System.out.println("Limite superiore del peso di una chiave: "+world.getMax_key_weight()+"\n");
				modifyKeyWeightLimit();
				
			}
			break;
			//---------------------------------------------------------------------------------------
			case 4:{
																											
				System.out.println("Numero massimo di chiavi trasportabili: "+world.getMax_transportable_keys_number()+"\n");
				modifyMaxTotalKeyNumber();
			}
			break;
			//---------------------------------------------------------------------------------------
			case 5:{
																												
				System.out.println("Peso massimo trasportabile: "+world.getMax_transportable_keys_weight()+"\n");
				modifyMaxTotalKeyWeight();
			}
			break;
			
			case 0: break;
			default: break;
		}
			
		}while(choice!=0);
		
	}
	
	private HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keepTrackKeys(){   
		
		HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> map = new HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>>();
		for(Token t: world.getKeytypes()){
			ArrayList<MediumGround> grounds= new ArrayList<MediumGround>();
			ArrayList<MediumPassage> passages= new ArrayList<MediumPassage>();
			for(MediumGround g: world.getGrounds()){
				if(g.getKey()==t) grounds.add(g);
			}
			for(MediumPassage p:world.getPassages()){
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
		for(Token a: world.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	private void modifyKeyWeights(){
		Token key=world.searchKeyTypes(LeggiInput.riga(INSERT_NAME_KEY));
		if(CheckValues.noElement(key, NO_KEY)) return;
		else {
			int weight=Integer.parseInt(LeggiInput.riga(INSERT_WEIGHT_KEY));
			if(CheckValues.isNegative(weight, NEGATIVE_VALUE)) return;
			else if (CheckValues.isOverLimit(weight, world.getMax_key_weight(), OVER_THE_LIMIT)) return;
			else{
				key.setWeight(weight);
				System.out.println(OK_MODIFY);
			}
		}
	}
	
	private void addKey(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		String name= LeggiInput.riga(INSERT_NAME_KEY);
		if(CheckValues.existsElement(world.searchKeyTypes(name), EXISTS_KEY)) return;
		int weight=LeggiInput.intero(INSERT_WEIGHT_KEY);
		if(CheckValues.isNegative(weight, NEGATIVE_VALUE)) return;
		else if (CheckValues.isOverLimit(weight, world.getMax_key_weight(), OVER_THE_LIMIT)) return;
		else {
			keys.add(new Token(weight, name));              							
			applyKeepTrackKeys(groundSet, keys, keyMap);
			System.out.println(KEY_ADDED);	
		}
	}
	
	private void removeKey(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		Token key=world.searchKeyTypes(LeggiInput.riga(INSERT_NAME_KEY));
		if(CheckValues.noElement(key, NO_KEY)) return;
		else keys.remove(key);
		applyKeepTrackKeys(groundSet, keys, keyMap);
		System.out.println(KEY_REMOVED);
	}
	
	private void modifyKeyWeightLimit(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else{
			world.setMax_key_weight(limit);
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
			world.setMax_transportable_keys_number(limit);
			System.out.println(MODIFY_OK);
		}
	}
	
	private void modifyMaxTotalKeyWeight(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			world.setMax_transportable_keys_weight(limit);
			System.out.println(MODIFY_OK);
		}
	}
	

	

}
