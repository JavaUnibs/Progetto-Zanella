package game.Medium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import game.Token;
import game.Abstract.ModifyWorld;
import it.unibs.ing.myutility.*;

/**
 * Classe concreta che estende l'abstract product ModifyWorld.
 */
public class ModifyMediumWorld extends ModifyWorld implements Serializable{
	

	private static final long serialVersionUID = 1L;
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
	private final String NO_REMOVE="Impossibile rimuovere ulteriori elementi";
	private final String OVER_THE_LIMIT_WEIGHT="Il valore inserito viola i vincoli di integrità tra\nil limite superiore del peso di una chiave e peso massimo trasportabile";
	private MediumWorld world;
	
	/**
	 * Costruttore della classe ModifyMediumWorld.
	 * @param world
	 */
	ModifyMediumWorld(MediumWorld world){
		this.world=world;
	}

	/**
	 * Metodo che permette la modifica del parametri del MediumWorld.
	 * @pre la variabile world e tutti i suoi campi non siano null
	 */
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
	
	/**
	 * Metodo che permette la raggiungibilità delle chiavi.
	 * @return map, HashMap di MediumGround e MediumPassage.
	 * @pre i campi mondo.grounds, mondo.passages non siano null
	 */
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
	
	/**
	 * Metodo che aggiorna la lista delle chiavi nel mondo per permettere la raggiungibilità del goal
	 * 
	 * @param groundSet
	 * @param keys
	 * @param keyMap
	 * @pre groundSet, keys e keyMap non siano nulli, e i luoghi e i passaggi specificati esistano in world.grounds e world.passages 
	 * @post i campi key dei luoghi e passaggi specificati siano diversi da null e siano aggiornati secondo la logica voluta
	 */
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
	
	/**
	 * Metodo che ritorna una lista di chiavi che non rispettano il vincolo di peso massimo.
	 * 
	 * @param max_weight
	 * @return temp, ArrayList di oggetti Token.
	 * @pre max_weight deve essere > 0
	 */
	private ArrayList<Token> invalidKeysWeight(int max_weight){                    
		ArrayList<Token> temp= new ArrayList<Token>();
		for(Token a: world.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	/**
	 * Metodo che permette la modifica del peso delle chiavi.
	 * @post il campo weight della chiave specificata risulti aggiornato con il nuovo valore (se valido)
	 */
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
	
	/**
	 * Metodo che permette l'aggiunta di chiavi.
	 * @param groundSet
	 * @param keys
	 * @param keyMap
	 * @pre groundSet, keys e keyMap non siano null, e i luoghi e i passaggi specificati esistano in world.grounds e world.passages 
	 * @post il nuovo oggetto di tipo Token risulti in world.keytypes
	 */
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
	
	/**
	 * Metodo che permette di rimuovere le chiavi.
	 * @param groundSet
	 * @param keys
	 * @param keyMap
	 * @pre groundSet, keys e keyMap non siano nulli, e i luoghi e i passaggi specificati esistano in world.grounds e world.passages
	 * @post la chiave specificata sia assente da world.keytypes (se esistente)
	 */
	private void removeKey(Set<ArrayList<MediumGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<MediumGround>, ArrayList<MediumPassage>> keyMap){
		if(keys.size()==1) {
			System.out.println(NO_REMOVE);
			return;
		}
		Token key=world.searchKeyTypes(LeggiInput.riga(INSERT_NAME_KEY));
		if(CheckValues.noElement(key, NO_KEY)) return;
		else keys.remove(key);
		applyKeepTrackKeys(groundSet, keys, keyMap);
		System.out.println(KEY_REMOVED);
	}
	
	/**
	 * Metodo che permette la modifica del limite di peso per le chiavi.
	 * @post il campo world.max_key_weight risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyKeyWeightLimit(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		if(CheckValues.isOverLimit(limit, world.getMax_transportable_keys_weight(), OVER_THE_LIMIT_WEIGHT)) return;
		else{
			world.setMax_key_weight(limit);
			System.out.println(WARNING_LIMIT);
			ArrayList<Token> keys =invalidKeysWeight(limit);
			for(Token k: keys) if(k.getWeight()>limit) k.setWeight(limit);
			System.out.println(MODIFY_OK);	
		}
	}
	
	/**
	 * Metodo che permette la modifica del numero di chiavi trasportabili.
	 * @post il campo world.max_transportable_keys_number risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyMaxTotalKeyNumber(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			world.setMax_transportable_keys_number(limit);
			System.out.println(MODIFY_OK);
		}
	}
	
	/**
	 * Metodo che permette di modificare il peso massimo trasportabile di chiavi.
	 * @post il campo world.max_transportable_keys_weight risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyMaxTotalKeyWeight(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		if(CheckValues.isOverLimit(world.getMax_key_weight(), limit, OVER_THE_LIMIT_WEIGHT)) return;
		else {
			world.setMax_transportable_keys_weight(limit);
			System.out.println(MODIFY_OK);
		}
	}
	

	

}
