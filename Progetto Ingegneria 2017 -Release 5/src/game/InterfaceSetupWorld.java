package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import it.unibs.ing.myutility.LeggiInput;
import it.unibs.ing.myutility.Menu;
import it.unibs.ing.myutility.RandomValues;

public class InterfaceSetupWorld {
	
	private SetupWorld setup;
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
	private final String NO_TRIALS="Questo mondo non utilizza prove";
	private final String NO_KEYS="Questo mondo non utilizza chiavi";
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
	
	InterfaceSetupWorld(SetupWorld setup){
		this.setup=setup;
		
	}
	
	public void initialize(){
		
		boolean emptyTrials=setup.getMondo().getTrials().isEmpty();
		boolean emptyKeys=setup.getMondo().getKeytypes().isEmpty();
		
		HashMap<Ground, ArrayList<Passage>> keyMap=setup.keepTrackKeys();
		Set<Ground> groundSet= keyMap.keySet();
		ArrayList<Ground> trialSet=setup.keepTrackTrials();
		
		String start="Questo mondo ha i seguenti parametri: \n";
		String tipi_chiavi="Tipi di chiavi con relativi pesi: \n"+setup.getMondo().getKeytypes().toString();
		String peso_max_chiave="Limite superiore del peso di una chiave: "+setup.getMondo().getPeso_max_chiave()+"\n";
		String num_max_chiavi="Numero massimo di chiavi trasportabili: "+setup.getMondo().getNumero_max_trasportabile()+"\n";
		String peso_max_trasp="Peso massimo trasportabile: "+setup.getMondo().getPeso_max_trasportabile()+"\n";
		String tipi_prove="Tipi di prove con relativi punteggi: \n"+setup.getMondo().getTrials().toString();
		String punteggio_max_prova="Limite superiore del valore di una prova: "+setup.getMondo().getPunteggio_max_prova()+"\n";
		String punti_start="Punti iniziali assegnati: "+setup.getMondo().getPoints()+"\n";
		String punti_max_fin="Punti da raggiungere per vincere: "+setup.getMondo().getPunteggio_finale()+"\n";
		String modifica="Scegli i parametri da modificare";
		System.out.println(start+tipi_chiavi+peso_max_chiave+num_max_chiavi+peso_max_trasp+tipi_prove+punteggio_max_prova+punti_start+punti_max_fin+modifica);
		
		
		
		Menu menu_modifica= new Menu(menu);
		int scelta;
		do{
			scelta=menu_modifica.stampaMenu();
			
			switch(scelta){
			
			case 1:{         
				
				if(emptyKeys) {
					System.out.println(NO_KEYS);
					break;
				}			
																													//modifica dei pesi delle chiavi
				System.out.println("Tipi di chiavi con relativi pesi: \n"+setup.getMondo().getKeytypes().toString());
				LeggiInput.terminaRiga();
				String name=LeggiInput.riga(INSERT_NAME_KEY);
				Token key=setup.getMondo().searchKeyTypes(name);
				if(key==null) {
					System.out.println(NO_KEY);
					break;
				}
				else {
					int weight=Integer.parseInt(LeggiInput.riga(INSERT_WEIGHT_KEY));
					if(weight<0) {
						System.out.println(NEGATIVE_VALUE);
						break;
					}
					else if (weight > setup.getMondo().getPeso_max_chiave()){
						System.out.println(OVER_THE_LIMIT);
						break;
					}
					else{
						key.setWeight(weight);
						System.out.println(OK_MODIFY);
						
					}
				}
				
			}
			break;
			
			case 2:{	
				
			if(emptyKeys) {
					System.out.println(NO_KEYS);
					break;
			}		
																													//modifica dei tipi di chiave
			System.out.println("Tipi di chiavi con relativi pesi: \n"+setup.getMondo().getKeytypes().toString());
			System.out.println(ADDREMOVEKEY);
			Menu sottomenu= new Menu(menu_addremove);
			ArrayList<Token> keys= setup.getMondo().getKeytypes();
			
			int scelta_2=sottomenu.stampaSottoMenu();
				switch(scelta_2){
				
				case 1:{  		
					boolean exists=false;
					LeggiInput.terminaRiga();
					String name= LeggiInput.riga(INSERT_NAME_KEY);
					
					for(Token t: keys) if(t.getName().equalsIgnoreCase(name)){
						System.out.println(EXISTS_KEY);
						exists=true;
						
					}
					if(exists) break;
					int weight=LeggiInput.intero(INSERT_WEIGHT_KEY);
					if(weight<0) {
						System.out.println(NEGATIVE_VALUE);
						break;
					}
					else if (weight > setup.getMondo().getPeso_max_chiave()){
						System.out.println(OVER_THE_LIMIT);
						break;
					}
					else {
					
						keys.add(new Token(weight, name));              							//usando la disposizione del mondo di default mette un tipo di chiave random
																									//nei luoghi e nei passaggi del mondo che avevano in comune lo stesso tipo di chiave
						for(Ground g: groundSet){
							int rnd=RandomValues.ranIntLimite(0, keys.size()-1);
							Token tempkey=keys.get(rnd);
							g.setKey(tempkey);
							for(Passage p: keyMap.get(g)) {
								p.setKey(tempkey);
								p.setOpen(false);							//non c'è il controllo dell'array list vuoto perché è appena stata aggiunta una chiave
							}
					
						}
						System.out.println(KEY_ADDED);
						
					}
					
					
					
				}
				break;
				
				case 2:{															//rimozione
					LeggiInput.terminaRiga();
					String name= LeggiInput.riga(INSERT_NAME_KEY);
					Token key=setup.getMondo().searchKeyTypes(name);
					
					if(key==null){
						System.out.println(NO_KEY);
						break;
					}else keys.remove(key);
					
					Token tempkey=null;
					
					for(Ground g: groundSet){
						if(!keys.isEmpty()){
							int rnd=RandomValues.ranIntLimite(0, keys.size()-1);
							tempkey=keys.get(rnd);
						}
						g.setKey(tempkey);
						for(Passage p: keyMap.get(g)) {
							p.setKey(tempkey);
							p.setOpen(true);
						}
				
					}
					
					System.out.println(KEY_REMOVED);
					
					
				}
				break;
				
				case 0: break;
				default: break;
				}
			
			}
			break;
			
			case 3:{                  
				
				if(emptyKeys) {
					System.out.println(NO_KEYS);
					break;
				}	
																												//limite superiore del peso di una chiave
				System.out.println("Limite superiore del peso di una chiave: "+setup.getMondo().getPeso_max_chiave()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else{
					setup.getMondo().setPeso_max_chiave(limit);
					System.out.println(WARNING_LIMIT);
					ArrayList<Token>keys =setup.invalidKeysWeight(limit);
					for(Token k: keys) if(k.getWeight()>limit) k.setWeight(limit);
					System.out.println(MODIFY_OK);
					
				}
				
				
			}
			break;
			
			case 4:{
				
				if(emptyKeys) {
					System.out.println(NO_KEYS);
					break;
				}																								//numero max di chiavi trasportabili
				System.out.println("Numero massimo di chiavi trasportabili: "+setup.getMondo().getNumero_max_trasportabile()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else {
					setup.getMondo().setNumero_max_trasportabile(limit);
					System.out.println(MODIFY_OK);
				}
			}
			break;
			
			case 5:{
				
				if(emptyKeys) {
					System.out.println(NO_KEYS);
					break;
				}																											
				System.out.println("Peso massimo trasportabile: "+setup.getMondo().getPeso_max_trasportabile()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else {
					setup.getMondo().setPeso_max_trasportabile(limit);
					System.out.println(MODIFY_OK);
				}
			}
			break;
			
			case 6:{
				
				if(emptyTrials) {
					System.out.println(NO_TRIALS);
					break;
				}
				
				System.out.println("Tipi di prove con relativi punteggi: \n"+setup.getMondo().getTrials().toString());
				System.out.println(ADDREMOVETRIAL);
				Menu sottomenu= new Menu(menu_addremove);
				int scelta2= sottomenu.stampaSottoMenu();
				ArrayList<Trial> trials=setup.getMondo().getTrials();
				
				//----------
				
				
				switch(scelta2){
				
				case 1: {																						//aggiunta prova
					
					boolean exists=false;
					LeggiInput.terminaRiga();
					String name=LeggiInput.riga(INSERT_NAME_TRIAL);
					for(Trial t: trials) {
						if(t.getName().equalsIgnoreCase(name)) {
							exists=true;
							System.out.println(EXISTS_TRIAL);
						}
					}
					
					if(exists) break;
					
					int points=LeggiInput.intero(INSERT_POINTS_TRIAL);
					if(points<0) {
						System.out.println(NEGATIVE_VALUE);
						break;
					}
					
					else if (points > setup.getMondo().getPunteggio_max_prova()){
						System.out.println(OVER_THE_LIMIT_TRIAL);
						break;
					}
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
					
					for(Ground g: trialSet) {                          //mette una nuova prova scelta a random nei luoghi in cui c'erano prima
						
							int rnd=RandomValues.ranIntLimite(0, trials.size()-1);
							g.setTrial(trials.get(rnd));
						
					}
					
					System.out.println(TRIAL_ADDED);
					
					
				}
				break;
				
				case 2:{																	//rimozione prova
					LeggiInput.terminaRiga();
					String name=LeggiInput.riga(INSERT_NAME_TRIAL);
					Trial trial=setup.getMondo().searchTrial(name);
					
					if(trial==null){
						System.out.println(NO_TRIAL);
						break;
					}else trials.remove(trial);
					
					Trial tempTrial=null;
					
					for(Ground g: trialSet) {
						
							if(!trials.isEmpty()){
								int rnd=RandomValues.ranIntLimite(0, trials.size()-1);
								tempTrial=trials.get(rnd);
							}
							g.setTrial(tempTrial);
							
						
					}
					
					System.out.println(TRIAL_REMOVED);
				}
				break;
				
				case 0: break;
				default: break;
				}
				
			}break;
			
			case 7:{
				
				if(emptyTrials) {
					System.out.println(NO_TRIALS);
					break;
				}
				
				System.out.println("Tipi di prove con relativi punteggi: \n"+setup.getMondo().getTrials().toString());
				LeggiInput.terminaRiga();
				String name=LeggiInput.riga(INSERT_NAME_TRIAL);
				Trial trial=setup.getMondo().searchTrial(name);
				if(trial==null) {
					System.out.println(NO_TRIAL);
					break;
				}
				else {
					int points=Integer.parseInt(LeggiInput.riga(INSERT_POINTS_TRIAL));
					if(points<0) {
						System.out.println(NEGATIVE_VALUE);
						break;
					}
					else if (points > setup.getMondo().getPunteggio_max_prova()){
						System.out.println(OVER_THE_LIMIT_TRIAL);
						break;
					}
					else{
						trial.setPoints(points);
						System.out.println(OK_MODIFY);
						
					}
				}
				
			}
			break;
			
			
			case 8:{
				
				if(emptyTrials) {
					System.out.println(NO_TRIALS);
					break;
				}
				
				System.out.println("Limite superiore del valore di una prova: "+setup.getMondo().getPunteggio_max_prova()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else{
					setup.getMondo().setPunteggio_max_prova(limit);
					System.out.println(WARNING_LIMIT);
					ArrayList<Trial> trials =setup.invalidTrialPoints(limit);
					for(Trial t: trials) if(t.getPoints()>limit) t.setPoints(limit);
					System.out.println(MODIFY_OK);
					
				}
			}
			break;
			
			case 9:{
				
				if(emptyTrials) {
					System.out.println(NO_TRIALS);
					break;
				}
				
				System.out.println("Punti iniziali assegnati: "+setup.getMondo().getPoints()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else {
					setup.getMondo().setPoints(limit);
					System.out.println(MODIFY_OK);
				}
			}
			break;
			
			
			case 10:{
				
				if(emptyTrials) {
					System.out.println(NO_TRIALS);
					break;
				}
				
				System.out.println("Punti da raggiungere per vincere: "+setup.getMondo().getPunteggio_finale()+"\n");
				int limit=LeggiInput.intero(LIMIT);
				if(limit<0) {
					System.out.println(NEGATIVE_VALUE);
					break;
				}
				else {
					setup.getMondo().setPunteggio_finale(limit);
					System.out.println(MODIFY_OK);
				}
			}
			break;
			
			case 0: break;
			default: break;
			}
			
			
		}while(scelta>0);
		
		
	}
	
	
	
	
	
	
	
	

}
