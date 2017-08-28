package game.Medium;

import java.io.Serializable;
import java.util.HashMap;

import game.Token;
import game.Abstract.Factory;
import game.Abstract.ModifyWorld;
import game.Abstract.Navigation;
import game.Abstract.World;

/**
 * Classe concreta che estende l'abstract product Factory. Ha il compito di costruire e restituire un oggetto MediumWorld.
 */
public class MediumFactory extends Factory implements Serializable{

	private static final long serialVersionUID = 1L;
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_FILE="Valori mancanti nel file di setup del mondo";
	
	private HashMap<String, String[]> values;
	private HashMap<String, String> common_string;
	private HashMap<String, String> local_string;
	private MediumWorld world;
	
	/**
	 * Costruttore della classe MediumFactory, legge i valori da un HashMap di ingresso e li salva nella HashMap locale.
	 * @param values
	 * @param common_string
	 * @param local_string
	 */
	public MediumFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		this.values=values;
		this.local_string=local_string;
		this.common_string=common_string;
	}

	/**
	 * Metodo che inizializza i valori del mondo con i valori contenuti nelle HashMap.
	 * @return world, oggetto di tipo MediumWorld
	 */
	public World getWorld() {
		try{
		int height= Integer.parseInt(values.get("ALTEZZA")[0]);
		int width= Integer.parseInt(values.get("LARGHEZZA")[0]);
		int depth= Integer.parseInt(values.get("PROFONDITA")[0]);
		int start_height=Integer.parseInt(values.get("START_H")[0]);
		int start_width=Integer.parseInt(values.get("START_W")[0]);
		int start_depth=Integer.parseInt(values.get("START_D")[0]);
		int end_height=Integer.parseInt(values.get("END_H")[0]);
		int end_width=Integer.parseInt(values.get("END_W")[0]);
		int end_depth=Integer.parseInt(values.get("END_D")[0]);
		int max_transportable_keys_weight=Integer.parseInt(values.get("PESO_MAX_TRAS")[0]);
		int max_transportable_keys_number=Integer.parseInt(values.get("NUM_MAX_TRAS")[0]);
		int max_key_weight=Integer.parseInt(values.get("PESO_MAX_CHIAVE")[0]);
		String ground_name=values.get("NOME_LUOGHI")[0];
		world=new MediumWorld(height, width, depth, max_transportable_keys_weight, max_transportable_keys_number, max_key_weight, ground_name);
		world.searchGround(start_height, start_width, start_depth).setStart(true);
		world.searchGround(end_height, end_width, end_depth).setEnd(true);
		
			if(!openPassages(values.get("PASSAGGI_APERTI"))) return null;
			addKeys(values.get("CHIAVI"));
			if(!putKeyGrounds(values.get("LUOGHI_CHIAVE"))) return null;
			if(!putKeyPassages(values.get("PASSAGGI_CHIAVE"))) return null;
		}catch(NullPointerException e){
			System.out.println(INCORRECT_FILE);
			return null;
		}
		return world;
		
	}

	/**
	 * Metodo che crea un oggetto Navigation
	 * return navigation, oggetto Navigation
	 */
	public Navigation getNavigation() {
		MediumNavigation navigation= new MediumNavigation(world, local_string, common_string);
		return navigation;
	}

	/**
	 * Metodo che crea un oggetto ModifyWorld.
	 * return modify, oggetto ModifyWorld
	 */
	public ModifyWorld getModify() {
		ModifyMediumWorld modify= new ModifyMediumWorld(world);
		return modify;
	}
	
	/**
	 * Metodo che apre i passaggi voluti partendo da un array di stringhe
	 * @param array
	 * @pre array non deve essere nullo
	 * @post valore ritornato true, non vi sono stati errori
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
				MediumGround g1= world.searchGround(h1, w1, d1);
				MediumGround g2= world.searchGround(h2, w2, d2);



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
	 * @pre array non deve essere nullo
	 * @post valore ritornato true
	 * @return true o false
	 */
	boolean putKeyGrounds(String[] array){                                 
		
		try{
			for(int i=0;i<array.length;i++){
				int h=Integer.parseInt(array[i].substring(0, 1));
				int w=Integer.parseInt(array[i].substring(1, 2));
				int d=Integer.parseInt(array[i].substring(2, 3));
				String temp=array[i].substring(4);
				MediumGround gtemp;
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
	 * @pre array non deve essere nullo
	 * @post valore ritornato true
	 * @return true o false
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
				MediumGround g1= world.searchGround(h1, w1, d1);
				MediumGround g2= world.searchGround(h2, w2, d2);
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
	 * @pre keys non deve essere nullo
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
	
}
