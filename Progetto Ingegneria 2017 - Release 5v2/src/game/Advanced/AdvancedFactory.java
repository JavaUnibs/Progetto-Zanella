package game.Advanced;

import java.io.Serializable;
import java.util.HashMap;

import game.Token;
import game.Trial;
import game.Abstract.Factory;
import game.Abstract.ModifyWorld;
import game.Abstract.Navigation;
import game.Abstract.World;
import game.Medium.MediumPassage;
import it.unibs.ing.myutility.RandomValues;

/**
 * Classe concreta che estende l'abstract product Factory. Ha il compito di costruire e restituire i concrete products relativi al tipo di mondo Advanced.
 */
public class AdvancedFactory extends Factory implements Serializable{

	private static final long serialVersionUID = 1L;
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_FILE="Valori mancanti nel file di setup del mondo";
	private final String INCORRECT_STRING3="Le prove da mettere nei luoghi non sono definite in modo corretto";
	
	private HashMap<String, String[]> values;
	private HashMap<String, String> common_string;
	private HashMap<String, String> local_string;
	private AdvancedWorld world;
	
	/**
	 * Costruttore della classe AdvancedFactory, legge i valori da un HashMap di ingresso e li salva nella HashMap locale.
	 * @param values
	 * @param common_string
	 * @param local_string
	 * @pre values, common_string e local_string non siano null
	 */
	public AdvancedFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		this.values=values;
		this.local_string=local_string;
		this.common_string=common_string;
	}

	/**
	 * Metodo che inizializza i valori del mondo con i valori contenuti nelle HashMap.
	 * @return world, oggetto di tipo AdvancedWorld o null
	 */
	public World getWorld() {
		try{
			int height= Integer.parseInt(values.get("ALTEZZA")[0]);
			int width= Integer.parseInt(values.get("LARGHEZZA")[0]);
			int depth= Integer.parseInt(values.get("PROFONDITA")[0]);
			int max_transportable_keys_weight=Integer.parseInt(values.get("PESO_MAX_TRAS")[0]);
			int max_transportable_keys_number=Integer.parseInt(values.get("NUM_MAX_TRAS")[0]);
			int max_key_weight=Integer.parseInt(values.get("PESO_MAX_CHIAVE")[0]);
			int	final_score =Integer.parseInt(values.get("PUNTI_FIN")[0]);
			int	max_trial_points =Integer.parseInt(values.get("PUNTI_MAX_PROVA")[0]);
			int	points=Integer.parseInt(values.get("PUNTI_INIZIALI")[0]);
			int start_height=Integer.parseInt(values.get("START_H")[0]);
			int start_width=Integer.parseInt(values.get("START_W")[0]);
			int start_depth=Integer.parseInt(values.get("START_D")[0]);
			int end_height=Integer.parseInt(values.get("END_H")[0]);
			int end_width=Integer.parseInt(values.get("END_W")[0]);
			int end_depth=Integer.parseInt(values.get("END_D")[0]);
			String ground_name=values.get("NOME_LUOGHI")[0];
			world=new AdvancedWorld(height, width, depth, max_transportable_keys_weight, max_transportable_keys_number, max_key_weight, final_score, max_trial_points, points, ground_name);
			world.searchGround(start_height, start_width, start_depth).setStart(true);
			world.searchGround(end_height, end_width, end_depth).setEnd(true);
			
			if(!openPassages(values.get("PASSAGGI_APERTI"))) return null;
			addKeys(values.get("CHIAVI"));
			if(!putKeyGrounds(values.get("LUOGHI_CHIAVE"))) return null;
			if(!putKeyPassages(values.get("PASSAGGI_CHIAVE"))) return null;
			addTrials(values.get("PROVE"));
			int i=1;
			do{
				if(values.get("QUIZ"+i)!=null) 
					addQA(values.get("QUIZ"+i));
				else break;
				i++;
			}while(true);
			if(!putTrialsGrounds(values.get("LUOGHI_PROVE"))) return null;
			
		}catch(NullPointerException e){
			System.out.println(INCORRECT_FILE);
			return null;
		}
		return world;
		
	}

	/**
	 * Metodo che crea un oggetto Navigation
	 * @pre values, common_string e local_string non siano null
	 * @return navigation, oggetto Navigation
	 */
	public Navigation getNavigation() {
		AdvancedNavigation navigation= new AdvancedNavigation(world, local_string, common_string);
		return navigation;
	}

	/**
	 * Metodo che crea un oggetto ModifyWorld.
	 * @pre il campo world non sia null
	 * @return modify, oggetto ModifyWorld
	 */
	public ModifyWorld getModify() {
		ModifyAdvancedWorld modify= new ModifyAdvancedWorld(world);
		return modify;
	}
	
	/**
	 * Metodo che apre i passaggi voluti partendo da un array di stringhe
	 * @param array
	 * @pre i luoghi e i passaggi specificati nell'array devono esistere nei campi mondo.grounds e mondo.passages
	 * @post il campo open dei passaggi specificati deve essere true nel caso non vi siano errori
	 * @return true o false
	 */
	boolean openPassages(String[] array){                     //apre i passaggi voluti partendo da un array di stringhe 

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				AdvancedGround g1= world.searchGround(h1, w1, d1);
				AdvancedGround g2= world.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					MediumPassage p= world.searchPassage(g1, g2);
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
	 * Metodo che aggiunge ai luoghi selezionati le chiavi definite nell'array (sintassi luogo-chiave)
	 * @param array
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
				AdvancedGround gtemp;
				Token key=null;
				
				boolean exists=false;
				for(Token a:world.getKeytypes()) {
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
				
				
					if((gtemp=world.searchGround(h, w, d))!=null){
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
	 * Metodo che aggiunge ai passaggi le chiavi definite nell'array (sintassi: luogo-luogo-chiave)
	 * @param array
	 * @pre i passaggi e le chiavi specificati nell'array devono esistere nei campi mondo.passages e mondo.keytypes
	 * @post il campo key dei passaggi specificati non deve essere null nel caso non vi siano errori
	 * @return teue o false
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
				AdvancedGround g1= world.searchGround(h1, w1, d1);
				AdvancedGround g2= world.searchGround(h2, w2, d2);
				Token key=null;
				
				
				boolean exists=false;
				for(Token a:world.getKeytypes()) {
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
					MediumPassage p= world.searchPassage(g1, g2);
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
	 * Metodo per aggiungere al mondo le chiavi definite nell'array (sintassi valore-nome)
	 * @param keys
	 * @pre keys non deve essere vuoto
	 * @post il campo mondo.keytypes non deve essere null nel caso non vi siano errori
	 */
	void addKeys(String[] keys){                                                  
		
		for(String s: keys){
			int value= Integer.parseInt(s.substring(0, s.indexOf("-")));
			String name= s.substring(s.indexOf("-")+1);
			
			boolean exists=false;
			for(Token b: world.getKeytypes()){ 
				if(b.getName().equalsIgnoreCase(name)) {
				System.out.println("Questa chiave esiste già");
				exists=true;
				break;
			}
				
			}
			if(!exists) world.getKeytypes().add(new Token(value, name));
		}
		
		
	}
	
	/**
	 * Metodo che aggiunge in modo random una prova da mettere nei luoghi definiti dall'array
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
			AdvancedGround g1= world.searchGround(h1, w1, d1);
			
			if (g1!=null){
				int rnd = RandomValues.ranIntLimite(0, world.getTrials().size()-1);
				g1.setTrial(world.getTrials().get(rnd));
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
	 * Metodo per aggiungere al mondo le prove definite nell'array (sintassi valore-nome)
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
			for(Trial a:world.getTrials()) {
				if (a.getName().equalsIgnoreCase(temp2)) {
					System.out.println("Questa prova esiste già");
					exists=true;
					break;
				}
				
				
			}
			if(!exists) world.getTrials().add(new Trial(points, temp2));
		
		}

	}
	
	/**
	 * Metodo che aggiunge alla prova scelta (primo slot array)  le domande e risposte definite dall'array (sintassi domanda-risposta)
	 * @param qa
	 * @pre le prove specificate nell'array devono esistere nel campo mondo.trials
	 * @post il campo quiz delle prove specificate non deve essere vuoto
	 */
	void addQA(String[] qa){ 
		String name=qa[0];
		for(Trial a: world.getTrials()){
			if (a.getName().equalsIgnoreCase(name)){
				for(int i=1;i<qa.length;i++){
					a.getQuiz().put(qa[i].substring(0, qa[i].indexOf("-")), qa[i].substring(qa[i].indexOf("-")+1));
				}
			}
		}
	}
}
