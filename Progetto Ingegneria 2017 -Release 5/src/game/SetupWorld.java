package game;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.myutility.RandomValues;

/**
 * Classe che permette la costruzione del mondo con i valori di default presi dai file di configurazione.
 */
public class SetupWorld {
	
	
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String INCORRECT_STRING3="Le prove da mettere nei luoghi non sono definite in modo corretto";
	private World mondo;
	private HashMap<String,String[]> values;
	
	/**
	 * Costruttore della classe, riceve in ingresso una HashMap con tutti i valori caratterizzanti del mondo e li assegna alle variabili corrispondenti.
	 * 
	 * @pre map deve contenere valori validi corrispondenti agli argomenti del costruttore di World
	 * @post il campo mondo non deve essere null
	 * @param map HashMap di String, il nome, e String[], i valori corrispondenti
	 */
	SetupWorld(HashMap<String,String[]> map){
		values=map;
		int height= Integer.parseInt(values.get("ALTEZZA")[0]);
		int width= Integer.parseInt(values.get("LARGHEZZA")[0]);
		int depth= Integer.parseInt(values.get("PROFONDITA")[0]);
		int peso_max_trasportabile=Integer.parseInt(values.get("PESO_MAX_TRAS")[0]);
		int numero_max_trasportabile=Integer.parseInt(values.get("NUM_MAX_TRAS")[0]);
		int peso_max_chiave=Integer.parseInt(values.get("PESO_MAX_CHIAVE")[0]);
		int	punteggio_finale =Integer.parseInt(values.get("PUNTI_FIN")[0]);
		int	punteggio_max_prova =Integer.parseInt(values.get("PUNTI_MAX_PROVA")[0]);
		int	points=Integer.parseInt(values.get("PUNTI_INIZIALI")[0]);
		String nome_luoghi=values.get("NOME_LUOGHI")[0];
		mondo=new World(height, width, depth, peso_max_trasportabile, numero_max_trasportabile, peso_max_chiave, punteggio_finale, punteggio_max_prova, points, nome_luoghi);
		
		
	}
	
	/**
	 * Metodo che dal mondo creato apre i passaggi passati con array.
	 * 
	 * @param array contenente i passaggi
	 * @pre i luoghi e i passaggi specificati nell'array devono esistere nei campi mondo.grounds e mondo.passages
	 * @post il campo open dei passaggi specificati deve essere true nel caso non vi siano errori
	 * @return false o true 
	 */
	boolean openPassages(String[] array){                     

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				Ground g1= mondo.searchGround(h1, w1, d1);
				Ground g2= mondo.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					Passage p= mondo.searchPassage(g1, g2);
					if (p!=null) {
						p.setOpen(true);
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Metodo che decide di posizionare una prova random nei luoghi passati per array
	 * 
	 * @param array
	 * @pre i luoghi e le prove specificati nell'array devono esistere nei campi mondo.grounds e mondo.trials
	 * @post il campo trial dei luoghi specificati non deve essere null nel caso non vi siano errori
	 * @return true o false 
	 */
	boolean putTrialsGrounds(String[] array){                                      
		try{
		for(int i=0;i<array.length;i++){
			int h1=Integer.parseInt(array[i].substring(0, 1));
			int w1=Integer.parseInt(array[i].substring(1, 2));
			int d1=Integer.parseInt(array[i].substring(2, 3));
			Ground g1= mondo.searchGround(h1, w1, d1);
			
			if (g1!=null){
				int rnd = RandomValues.ranIntLimite(0, mondo.getTrials().size()-1);
				g1.setTrial(mondo.getTrials().get(rnd));
			}else {
				System.out.println(NO_GROUND);
				return false;
			}
		}
		}
		catch(IllegalArgumentException e){
			System.out.println(INCORRECT_STRING3);
			return false;
		}
		return true;
	}
	
	/**
	 * Metodo che aggiunge al mondo le prove definite nell'array.
	 * 
	 * @param array
	 * @pre array non deve essere vuoto
	 * @post il campo mondo.keytypes non deve essere null nel caso non vi siano errori
	 */
	void addTrials(String[] array){                                          
		for(int i=0;i<array.length;i++){


			String temp=array[i].substring(0, array[i].indexOf("-"));
			int points=Integer.parseInt(temp);
			String temp2=array[i].substring(array[i].indexOf("-")+1);
			
			boolean exists=false;
			for(Trial a:mondo.getTrials()) {
				if (a.getName().equalsIgnoreCase(temp2)) {
					System.out.println("Questa prova esiste già");
					exists=true;
					break;
				}
				
				
			}
			if(!exists) mondo.getTrials().add(new Trial(points, temp2));
		
		}

	}
	
	/**
	 * Metodo che aggiunge alla prova scelta le domande e le corrispondenti risposte
	 * 
	 * @param qa array di domande e risposte
	 * @pre le prove specificate nell'array devono esistere nel campo mondo.trials
	 * @post il campo quiz delle prove specificate non deve essere vuoto
	 */
	void addQA(String[] qa){ //Aggiunge alla prova scelta (primo slot array)  le domande e risposte definite dall'array (sintassi domanda-risposta)
		String name=qa[0];
		for(Trial a: mondo.getTrials()){
			if (a.getName().equalsIgnoreCase(name)){
				for(int i=1;i<qa.length;i++){
					a.getQuiz().put(qa[i].substring(0, qa[i].indexOf("-")), qa[i].substring(qa[i].indexOf("-")+1));
				}
			}
		}
	}
	
	/**
	 * Metodo che aggiunge ai luoghi selezionati le chiavi definite nell'array
	 * 
	 * @param array array di luogo e chiave
	 * @pre i luoghi e le chiavi specificati nell'array devono esistere nei campi mondo.grounds e mondo.keytypes
	 * @post il campo key dei luoghi specificati non deve essere null nel caso non vi siano errori
	 * @return true o false 
	 */
	boolean putKeyGrounds(String[] array){                                  
		
		try{
			for(int i=0;i<array.length;i++){
				int h=Integer.parseInt(array[i].substring(0, 1));
				int w=Integer.parseInt(array[i].substring(1, 2));
				int d=Integer.parseInt(array[i].substring(2, 3));
				String temp=array[i].substring(4);
				Ground gtemp;
				Token key=null;
				
				boolean exists=false;
				for(Token a:mondo.getKeytypes()) {
					if (a.getName().equalsIgnoreCase(temp)) {
						key=a;
						exists=true;
						break;
					}
					
				}
				
				if(!exists) {
					System.out.println("Questa chiave non esiste");
					return false;
				}
				
				
					if((gtemp=mondo.searchGround(h, w, d))!=null){
						gtemp.setKey(key);
					} else{
						
						System.out.println(NO_GROUND);
						return false;
				}	
			}
			
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Metodo che aggiunge ai passaggi le chiavi definite nell'array 
	 * 
	 * @param array di luogo-luogo-chiave
	 * @pre i passaggi e le chiavi specificati nell'array devono esistere nei campi mondo.passages e mondo.keytypes
	 * @post il campo key dei passaggi specificati non deve essere null nel caso non vi siano errori
	 * @return true o false se è riuscito ad aggiungere correttamente la chiave legata al relativo passaggio o meno
	 */
	boolean putKeyPassages(String[] array){                               
		
		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				String temp=array[i].substring(8);
				Ground g1= mondo.searchGround(h1, w1, d1);
				Ground g2= mondo.searchGround(h2, w2, d2);
				Token key=null;
				
				
				boolean exists=false;
				for(Token a:mondo.getKeytypes()) {
					if (a.getName().equalsIgnoreCase(temp)) {
						key=a;
						exists=true;
						break;
					}
					
				}
				
				if(!exists) {
					System.out.println("Questa chiave non esiste");
					return false;
				}


				if((g1!=null)&&(g2!=null)){
					Passage p= mondo.searchPassage(g1, g2);
					if (p!=null) {
						if(key!=null){
							p.setKey(key);
						}else {
							System.out.println(NO_KEY);
							return false;
						}
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Metodo che aggiunge al mondo le chiavi definite nell'array.
	 * 
	 * @param keys array rappresentativo delle chiavi definite con valore e nome
	 * @pre keys non deve essere vuoto
	 * @post il campo mondo.keytypes non deve essere null nel caso non vi siano errori
	 */
	void addKeys(String[] keys){                                                  
		
		for(String s: keys){
			int value= Integer.parseInt(s.substring(0, s.indexOf("-")));
			String name= s.substring(s.indexOf("-")+1);
			
			boolean exists=false;
			for(Token b: mondo.getKeytypes()){ 
				if(b.getName().equalsIgnoreCase(name)) {
				System.out.println("Questa chiave esiste già");
				exists=true;
				break;
			}
				
			}
			if(!exists) mondo.getKeytypes().add(new Token(value, name));
		}
		
		
	}

	/**
	 * Metodo che mantiene traccia in un HashMap dei luoghi e dei passaggi con chiavi coincidenti per mantenere la raggiungibilità del goal
	 * @pre i campi mondo.grounds, mondo.passages non siano null
	 * @return map HashMap di che associa un ArrayList di Ground ad un ArrayList di Passage
	 */
	HashMap<ArrayList<Ground>, ArrayList<Passage>> keepTrackKeys(){                              
		HashMap<ArrayList<Ground>, ArrayList<Passage>> map = new HashMap<ArrayList<Ground>, ArrayList<Passage>>();
		for(Token t: mondo.getKeytypes()){
			ArrayList<Ground> grounds= new ArrayList<Ground>();
			ArrayList<Passage> passages= new ArrayList<Passage>();
			
			for(Ground g: mondo.getGrounds()){
				if(g.getKey()==t) grounds.add(g);
			}
			
			for(Passage p:mondo.getPassages()){
				if(p.getKey()==t) passages.add(p);
			}
			
			map.put(grounds, passages);
			
		}
		
		return map;
		
	}
	
	/**
	 * Metodo che tiene traccia in un ArrayList di Ground i luoghi in cui c'è una prova
	 * @pre il campo mondo.grounds non deve essere null
	 * @return grounds, ArrayList di luoghi contenenti una prova
	 */
	ArrayList<Ground> keepTrackTrials(){													
		ArrayList<Ground> grounds=new ArrayList<Ground>();
		for (Ground g: mondo.getGrounds()) if(g.getTrial()!=null) grounds.add(g);
		return grounds;
	}
	
	/**
	 * Metodo che restituisce un ArrayList contenente le chiavi che non rispettano il vincolo di peso massimo
	 * 
	 * @param max_weight
	 * @pre max_weight deve essere > 0
	 * @return temp ArrayList di chiavi che non rispettano i vincoli di peso massimo
	 */
	ArrayList<Token> invalidKeysWeight(int max_weight){                   
		ArrayList<Token> temp= new ArrayList<Token>();
		for(Token a: mondo.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	/**
	 * Metodo che restituisce un ArrayList contenente le prove che non rispettano il vincolo di punteggio massimo
	 * 
	 * @param max_points
	 * @pre max_points deve essere > 0
	 * @return temp ArrayList di prove che non rispettano i vincoli di punteggiomassimo
	 */
	ArrayList<Trial> invalidTrialPoints(int max_points){                   //Restituisce un arraylist contenente le prove che non rispettano il vincolo di punteggio massimo
		ArrayList<Trial> temp= new ArrayList<Trial>();
		for(Trial a: mondo.getTrials())
			if(a.getPoints()>=max_points) temp.add(a);
		return temp;

	
	}


	public World getMondo() {
		return mondo;
	}


	public void setMondo(World mondo) {
		this.mondo = mondo;
	}
	
	/**
	 * Metodo che inizializza il mondo con i valori definiti dagli array di configurazione.
	 * @return result true o false se è riuscito o meno ad inizializzare tutti gli oggetti del mondo.
	 */
	public boolean initialize(){
		boolean result=true;
		result=result&&openPassages(values.get("PASSAGGI_APERTI"));
		if(values.get("CHIAVI")!=null) 
			addKeys(values.get("CHIAVI"));
		if(values.get("LUOGHI_CHIAVE")!=null) 
			result=result&&putKeyGrounds(values.get("LUOGHI_CHIAVE"));
		if(values.get("PASSAGGI_CHIAVE")!=null) 
			result=result&&putKeyPassages(values.get("PASSAGGI_CHIAVE"));
		if(values.get("PROVE")!=null) 
				addTrials(values.get("PROVE"));
			int i=1;
			do{
				if(values.get("QUIZ"+i)!=null) 
					addQA(values.get("QUIZ"+i));
				else break;
				i++;
			}while(true);
			if(values.get("LUOGHI_PROVE")!=null) result=result&&putTrialsGrounds((values.get("LUOGHI_PROVE")));
			return result;
		
	}
	
	

}
