package game;
import it.unibs.ing.myutility.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ModifyAdvancedWorld extends ModifyWorld
{
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
	private AdvancedWorld mondo;

	ModifyAdvancedWorld(AdvancedWorld mondo){
		this.mondo=mondo;
	}
	public void initialize() {
		HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap=keepTrackKeys();
		Set<ArrayList<AdvancedGround>> groundSet = keyMap.keySet();
		ArrayList<AdvancedGround> trialSet=keepTrackTrials();
		String start="Questo mondo ha i seguenti parametri: \n";
		String tipi_chiavi="Tipi di chiavi con relativi pesi: \n"+mondo.getKeytypes().toString();
		String peso_max_chiave="Limite superiore del peso di una chiave: "+mondo.getPeso_max_chiave()+"\n";
		String num_max_chiavi="Numero massimo di chiavi trasportabili: "+mondo.getNumero_max_trasportabile()+"\n";
		String peso_max_trasp="Peso massimo trasportabile: "+mondo.getPeso_max_trasportabile()+"\n";
		String tipi_prove="Tipi di prove con relativi punteggi: \n"+mondo.getTrials().toString();
		String punteggio_max_prova="Limite superiore del valore di una prova: "+mondo.getPunteggio_max_prova()+"\n";
		String punti_start="Punti iniziali assegnati: "+mondo.getPoints()+"\n";
		String punti_max_fin="Punti da raggiungere per vincere: "+mondo.getPunteggio_finale()+"\n";
		String modifica="Scegli i parametri da modificare";
		System.out.println(start+tipi_chiavi+peso_max_chiave+num_max_chiavi+peso_max_trasp+tipi_prove+punteggio_max_prova+punti_start+punti_max_fin+modifica);
		
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
			
			case 6:{
				
				System.out.println("Tipi di prove con relativi punteggi: \n"+mondo.getTrials().toString());
				System.out.println(ADDREMOVETRIAL);
				Menu sottomenu= new Menu(menu_addremove);
				int scelta2= sottomenu.stampaSottoMenu();
				ArrayList<Trial> trials=mondo.getTrials();
				
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

				System.out.println("Tipi di prove con relativi punteggi: \n"+mondo.getTrials().toString());
				LeggiInput.terminaRiga();
				modifyTrialPoints();
			}
			break;
			
			case 8:{

				System.out.println("Limite superiore del valore di una prova: "+mondo.getPunteggio_max_prova()+"\n");
				modifyTrialPointsLimit();
				
			}
			break;
			
			case 9:{
				
				System.out.println("Punti iniziali assegnati: "+mondo.getPoints()+"\n");
				modifyStartingPoints();
			}
			break;
			
			
			case 10:{
				
				System.out.println("Punti da raggiungere per vincere: "+mondo.getPunteggio_finale()+"\n");
				modifyFinalPoints();
			}
			break;
			
			case 0: break;
			default: break;
		}
		}while(scelta!=0);
		
		
	}

	private HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keepTrackKeys(){   
			
			HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> map = new HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>>();
			for(Token t: mondo.getKeytypes()){
				ArrayList<AdvancedGround> grounds= new ArrayList<AdvancedGround>();
				ArrayList<MediumPassage> passages= new ArrayList<MediumPassage>();
				for(AdvancedGround g: mondo.getGrounds()){
					if(g.getKey()==t) grounds.add(g);
				}
				for(MediumPassage p:mondo.getPassages()){
					if(p.getKey()==t) passages.add(p);
				}
				map.put(grounds, passages);
			}
			return map;	
		}
	
	private ArrayList<AdvancedGround> keepTrackTrials(){													
			ArrayList<AdvancedGround> grounds=new ArrayList<AdvancedGround>();
			for (AdvancedGround g: mondo.getGrounds()) if(g.getTrial()!=null) grounds.add(g);
			return grounds;
		}

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
	
	private ArrayList<Token> invalidKeysWeight(int max_weight){                    
		ArrayList<Token> temp= new ArrayList<Token>();
		for(Token a: mondo.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	ArrayList<Trial> invalidTrialPoints(int max_points){                   //Restituisce un arraylist contenente le prove che non rispettano il vincolo di punteggio massimo
		ArrayList<Trial> temp= new ArrayList<Trial>();
		for(Trial a: mondo.getTrials())
			if(a.getPoints()>=max_points) temp.add(a);
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
	
	private void addKey(Set<ArrayList<AdvancedGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap){
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
	
	private void removeKey(Set<ArrayList<AdvancedGround>> groundSet, ArrayList<Token> keys, HashMap<ArrayList<AdvancedGround>, ArrayList<MediumPassage>> keyMap){
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
	
	private void addTrial(ArrayList<Trial> trials, ArrayList<AdvancedGround> trialSet){
		String name=LeggiInput.riga(INSERT_NAME_TRIAL);
		if(CheckValues.existsElement(mondo.searchTrial(name), EXISTS_TRIAL)) return;
		int points=LeggiInput.intero(INSERT_POINTS_TRIAL);
		if(CheckValues.isNegative(points, NEGATIVE_VALUE)) return;
		else if (CheckValues.isOverLimit(points, mondo.getPunteggio_max_prova(), OVER_THE_LIMIT_TRIAL)) return;
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
	
	private void removeTrial(ArrayList<Trial> trials, ArrayList<AdvancedGround> trialSet){
		String name=LeggiInput.riga(INSERT_NAME_TRIAL);
		Trial trial=mondo.searchTrial(name);
		if(CheckValues.noElement(trial, NO_TRIAL)) return;
		else trials.remove(trial);
		applyKeepTrackTrials(trialSet, trials);
		System.out.println(TRIAL_REMOVED);
	}
	
	
	private void modifyTrialPoints(){
		Trial trial=mondo.searchTrial(LeggiInput.riga(INSERT_NAME_TRIAL));
		if(CheckValues.noElement(trial, NO_TRIAL)) return;
		else {
			int points=Integer.parseInt(LeggiInput.riga(INSERT_POINTS_TRIAL));
			if(CheckValues.isNegative(points, NEGATIVE_VALUE)) return;
			else if (CheckValues.isOverLimit(points, mondo.getPunteggio_max_prova(), OVER_THE_LIMIT_TRIAL)) return;
			else{
				trial.setPoints(points);
				System.out.println(OK_MODIFY);
				
			}
		}
	}
	
	private void modifyTrialPointsLimit(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else{
			mondo.setPunteggio_max_prova(limit);
			System.out.println(WARNING_LIMIT);
			ArrayList<Trial> trials =invalidTrialPoints(limit);
			for(Trial t: trials) if(t.getPoints()>limit) t.setPoints(limit);
			System.out.println(MODIFY_OK);
			
		}
	}
	
	private void modifyStartingPoints(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			mondo.setPoints(limit);
			System.out.println(MODIFY_OK);
		}
	}
	
	private void modifyFinalPoints(){
		int limit=LeggiInput.intero(LIMIT);
		if(CheckValues.isNegative(limit, NEGATIVE_VALUE)) return;
		else {
			mondo.setPunteggio_finale(limit);
			System.out.println(MODIFY_OK);
		}
	}
}
