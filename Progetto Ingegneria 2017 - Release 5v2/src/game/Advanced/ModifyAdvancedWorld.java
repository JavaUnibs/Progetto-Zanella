package game.Advanced;
import it.unibs.ing.myutility.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import game.Token;
import game.Trial;
import game.Abstract.ModifyWorld;
import game.Medium.MediumPassage;

/**
 * Classe concreta che estende l'abstract product ModifyWorld.
 */
public class ModifyAdvancedWorld extends ModifyWorld implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private final String[] menu={"Pesi delle chiavi","Tipi di chiavi", "Limite superiore del peso di una chiave", "Numero massimo di chiavi trasportabili", 
				"Peso massimo di chiavi trasportabili", "Tipi di prove", "Punteggi delle prove", "Limite superiore del valore di una prova",
				"Punteggio iniziale", "Punteggio di soglia per la vittoria"};
	private final String[] menu_addremove={"Aggiungi", "Rimuovi"};
	private final String INSERT_NAME_KEY="Inserire il nome della chiave";
	private final String INSERT_NAME_TRIAL="Inserire il nome della prova";
	private final String INSERT_POINTS_TRIAL="Inserire il punteggio della prova";
	private final String INSERT_QUESTION="Inserire un quiz relativo alla prova";
	private final String INSERT_ANSWER="Inserire la risposta corretta al quiz";
	private final String CONTINUE="Aggiungere altri quiz?";
	private final String INSERT_WEIGHT_KEY="Inserire il peso della chiave";
	private final String OK_MODIFY="Modifica effettuata";
	private final String NO_KEY="Non esistono chiavi con il nome inserito";
	private final String NO_TRIAL="Non esistono prove con il nome inserito";
	private final String NEGATIVE_VALUE="Il valore inserito non è valido";
	private final String OVER_THE_LIMIT="Il valore inserito è maggiore del limite superiore del peso di una chiave";
	private final String OVER_THE_LIMIT_TRIAL="Il valore inserito è maggiore del limite superiore del punteggio di una prova";
	private final String ADDREMOVEKEY="Vuoi aggiungere o rimuovere una chiave?";
	private final String ADDREMOVETRIAL="Vuoi aggiungere o rimuovere una prova?";
	private final String KEY_ADDED="Chiave aggiunta";
	private final String TRIAL_ADDED="Prova aggiunta";
	private final String KEY_REMOVED="Chiave rimossa";
	private final String TRIAL_REMOVED="Prova rimossa";
	private final String EXISTS_KEY="Esiste già una chiave con lo stesso nome";
	private final String EXISTS_TRIAL="Esiste già una prova con lo stesso nome";
	private final String LIMIT="Inserire il nuovo limite";
	private final String WARNING_LIMIT="I valori superiori al limite verranno troncati";
	private final String MODIFY_OK="Modifica effettuata";
	private final String NO_REMOVE="Impossibile rimuovere ulteriori elementi";
	private final String OVER_THE_LIMIT_WEIGHT="Il valore inserito viola i vincoli di integrità tra\nil limite superiore del peso di una chiave e peso massimo trasportabile";
	private AdvancedWorld world;

	/**
	 * Costruttore della classe ModifyAdvancedWorld.
	 * @param world
	 */
	public ModifyAdvancedWorld(AdvancedWorld world){
		this.world=world;
	}
	

	/**
	 * Metodo che permette la modifica del parametri dell'oggetto world.
	 * @pre la variabile world e tutti i suoi campi non siano null
	 */
	public void initialize() {
		HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap=keepTrackKeys();
		Set<ArrayList<AdvancedGround>> groundSet = keyMap.keySet();
		ArrayList<AdvancedGround> trialSet=keepTrackTrials();
		String start="Questo mondo ha i seguenti parametri: \n";
		String key_types="Tipi di chiavi con relativi pesi: \n"+world.getKeytypes().toString();
		String max_key_weight="Limite superiore del peso di una chiave: "+world.getMax_key_weight()+"\n";
		String max_transportable_keys_number="Numero massimo di chiavi trasportabili: "+world.getMax_transportable_keys_number()+"\n";
		String max_transportable_keys_weight="Peso massimo trasportabile: "+world.getMax_transportable_keys_weight()+"\n";
		String trial_types="Tipi di prove con relativi punteggi: \n"+world.getTrials().toString();
		String max_trial_points="Limite superiore del valore di una prova: "+world.getMax_trial_points()+"\n";
		String start_points="Punti iniziali assegnati: "+world.getPoints()+"\n";
		String final_score="Punti da raggiungere per vincere: "+world.getFinal_score()+"\n";
		String modify_choice="Scegli i parametri da modificare";
		System.out.println(start+key_types+max_key_weight+max_transportable_keys_number+max_transportable_keys_weight+trial_types+max_trial_points+start_points+final_score+modify_choice);
		
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
			
			case 6:{
				
				System.out.println("Tipi di prove con relativi punteggi: \n"+world.getTrials().toString());
				System.out.println(ADDREMOVETRIAL);
				Menu sottomenu= new Menu(menu_addremove);
				int scelta2= sottomenu.stampaSottoMenu();
				ArrayList<Trial> trials=world.getTrials();
				
				//----------
				
				
				switch(scelta2){
				
				case 1: {																						//aggiunta prova
					
					LeggiInput.terminaRiga();
					addTrial(trials, trialSet);
				}
				break;
				
				case 2:{																	//rimozione prova
					LeggiInput.terminaRiga();
					removeTrial(trials, trialSet);
				}
				break;
				
				case 0: break;
				default: break;
				}
				
			}break;
			
			case 7:{

				System.out.println("Tipi di prove con relativi punteggi: \n"+world.getTrials().toString());
				LeggiInput.terminaRiga();
				modifyTrialPoints();
			}
			break;
			
			case 8:{

				System.out.println("Limite superiore del valore di una prova: "+world.getMax_trial_points()+"\n");
				modifyTrialPointsLimit();
				
			}
			break;
			
			case 9:{
				
				System.out.println("Punti iniziali assegnati: "+world.getPoints()+"\n");
				modifyStartingPoints();
			}
			break;
			
			
			case 10:{
				
				System.out.println("Punti da raggiungere per vincere: "+world.getFinal_score()+"\n");
				modifyFinalPoints();
			}
			break;
			
			case 0: break;
			default: break;
		}
		}while(choice!=0);
		
		
	}

	/**
	 * Metodo che permette la raggiungibilità delle chiavi.
	 * @pre i campi mondo.grounds, mondo.passages non siano null
	 * @return map HashMap di che associa un ArrayList di Ground ad un ArrayList di Passage
	 */
	private HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keepTrackKeys(){   
			
			HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> map = new HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>>();
			for(Token t: world.getKeytypes()){
				ArrayList<AdvancedGround> grounds= new ArrayList<AdvancedGround>();
				ArrayList<MediumPassage> passages= new ArrayList<MediumPassage>();
				for(AdvancedGround g: world.getGrounds()){
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
	 * Metodo che permette la raggiungibilità delle prove.
	 * @pre il campo world.grounds non sia null
	 * @return grounds, ArrayList di luoghi contenenti una prova
	 */
	private ArrayList<AdvancedGround> keepTrackTrials(){													
			ArrayList<AdvancedGround> grounds=new ArrayList<AdvancedGround>();
			for (AdvancedGround g: world.getGrounds()) if(g.getTrial()!=null) grounds.add(g);
			return grounds;
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
	private void applyKeepTrackKeys(Set<ArrayList<AdvancedGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap){
		Token tempkey=null;
		for(ArrayList<AdvancedGround> array: groundSet){
			if(!keys.isEmpty()){
				int rnd=RandomValues.ranIntLimite(0, keys.size()-1);
				tempkey=keys.get(rnd);
			}
			
			for(AdvancedGround g: array) g.setKey(tempkey);
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
	 * Metodo che ritorna una lista di prove che non rispettano il vincolo di punteggio massimo.
	 * @pre max_points deve essere > 0
	 * @param max_points
	 * @return temp, ArrayList di oggetti Trial.
	 */
	ArrayList<Trial> invalidTrialPoints(int max_points){                   
		ArrayList<Trial> temp= new ArrayList<Trial>();
		for(Trial a: world.getTrials())
			if(a.getPoints()>=max_points) temp.add(a);
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
	private void addKey(Set<ArrayList<AdvancedGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap){
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
	private void removeKey(Set<ArrayList<AdvancedGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap){
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
	 * Metodo che permette di modificare il limite superiore del peso di una chiave
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
	 * 
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
	 * Metodo che permette la modifica del limite di peso trasportabile per le chiavi.
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
	
	/**
	 * Metodo che aggiorna la lista delle prove nel mondo per permettere la raggiungibilità.
	 * @param trialSet
	 * @param trials
	 * @pre trialSet e trials non siano null, e i luoghi e le prove specificati esistano in world.trials e world.grounds
	 * @post il campo trial dei luoghi specificati non sia null 
	 */
	private	void applyKeepTrackTrials(ArrayList<AdvancedGround> trialSet, ArrayList<Trial> trials){
		Trial tempTrial=null;
		for(AdvancedGround g: trialSet) {
			
			if(!trials.isEmpty()){
				int rnd=RandomValues.ranIntLimite(0, trials.size()-1);
				tempTrial=trials.get(rnd);
			}
			g.setTrial(tempTrial);
		}
	}
	
	/**
	 * Metodo che permette di aggiungere prove.
	 * @param trials
	 * @param trialSet
	 * @pre trialSet e trials non siano null, e i luoghi e le prove specificati esistano in world.trials e world.grounds
	 * @post il nuovo oggetto di tipo Trial risulti in world.trials
	 */
	private void addTrial(ArrayList<Trial> trials, ArrayList<AdvancedGround> trialSet){
		String name=LeggiInput.riga(INSERT_NAME_TRIAL);
		if(CheckValues.existsElement(world.searchTrial(name), EXISTS_TRIAL)) return;
		int points=LeggiInput.intero(INSERT_POINTS_TRIAL);
		if(CheckValues.isNegative(points, NEGATIVE_VALUE)) return;
		else if (CheckValues.isOverLimit(points, world.getMax_trial_points(), OVER_THE_LIMIT_TRIAL)) return;
		else{
			
			Trial trial= new Trial(points, name);
			trials.add(trial);
			boolean end=false;
			do{
				LeggiInput.terminaRiga();
				String question=LeggiInput.riga(INSERT_QUESTION);
				String answer=LeggiInput.riga(INSERT_ANSWER);
				trial.getQuiz().put(question, answer);
				end=LeggiInput.doppiaScelta(CONTINUE);

			}while(end);
		
			
		}
		applyKeepTrackTrials(trialSet, trials);
		System.out.println(TRIAL_ADDED);
	}
	
	/**
	 * Metodo che permette di rimuovere prove
	 * @param trials
	 * @param trialSet
	 * @pre trialSet e trials non siano null, e i luoghi e le prove specificati esistano in world.trials e world.grounds
	 * @post la prova specificata sia assente da world.trials ( se esistente)
	 */
	private void removeTrial(ArrayList<Trial> trials, ArrayList<AdvancedGround> trialSet){
		if(trials.size()==1) {
			System.out.println(NO_REMOVE);
			return;
		}
		String name=LeggiInput.riga(INSERT_NAME_TRIAL);
		Trial trial=world.searchTrial(name);
		if(CheckValues.noElement(trial, NO_TRIAL)) return;
		else trials.remove(trial);
		applyKeepTrackTrials(trialSet, trials);
		System.out.println(TRIAL_REMOVED);
	}
	
	/**
	 * Metodo che permette la modifica del punteggio delle prove.
	 * @post il campo points della prova specificata risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyTrialPoints(){
		Trial trial=world.searchTrial(LeggiInput.riga(INSERT_NAME_TRIAL));
		if(CheckValues.noElement(trial, NO_TRIAL)) return;
		else {
			int points=Integer.parseInt(LeggiInput.riga(INSERT_POINTS_TRIAL));
			if(CheckValues.isNegative(points, NEGATIVE_VALUE)) return;
			else if (CheckValues.isOverLimit(points, world.getMax_trial_points(), OVER_THE_LIMIT_TRIAL)) return;
			else{
				trial.setPoints(points);
				System.out.println(OK_MODIFY);
				
			}
		}
	}
	
	/**
	 * Metodo che permette di modificare il limite del punteggio massimo di una prova
	 * @post il campo world.max_trial_points risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyTrialPointsLimit(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else{
			world.setMax_trial_points(limit);
			System.out.println(WARNING_LIMIT);
			ArrayList<Trial> trials =invalidTrialPoints(limit);
			for(Trial t: trials) if(t.getPoints()>limit) t.setPoints(limit);
			System.out.println(MODIFY_OK);
			
		}
	}
	
	/**
	 * Metodo che permette di moficiare il punteggio iniziale
	 * @post il campo world.points risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyStartingPoints(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			world.setPoints(limit);
			System.out.println(MODIFY_OK);
		}
	}
	
	/**
	 * Metodo che permette di modificare il punteggio finale di obbiettivo.
	 * @post il campo world.Final_score risulti aggiornato con il nuovo valore (se valido)
	 */
	private void modifyFinalPoints(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			world.setFinal_score(limit);
			System.out.println(MODIFY_OK);
		}
	}
}
