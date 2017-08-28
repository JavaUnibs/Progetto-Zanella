package game.Basic;

import java.io.Serializable;
import java.util.HashMap;

import game.Abstract.Factory;
import game.Abstract.ModifyWorld;
import game.Abstract.Navigation;
import game.Abstract.World;

/**
 * Classe concreta che estende l'abstract product Factory. Ha il compito di costruire e restituire un oggetto BasicWorld.
 */
public class BasicFactory extends Factory implements Serializable{

	private static final long serialVersionUID = 1L;
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_FILE="Valori mancanti nel file di setup del mondo";
	private HashMap<String, String[]> values;
	private HashMap<String, String> common_string;
	private HashMap<String, String> local_string;
	private BasicWorld world;
	
	/**
	 * Costruttore della classe BasicFactory, legge i valori da un HashMap di ingresso e li salva nella HashMap locale.
	 * @param values
	 * @param common_string
	 * @param local_string
	 */
	public BasicFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		this.values=values;
		this.local_string=local_string;
		this.common_string=common_string;
	}
	
	/**
	 * Metodo che inizializza i valori del mondo con i valori contenuti nelle HashMap.
	 * @return world, oggetto di tipo BasicWorld
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
		String ground_name=values.get("NOME_LUOGHI")[0];
		world=new BasicWorld(height, width, depth, ground_name);
		world.searchGround(start_height, start_width, start_depth).setStart(true);
		world.searchGround(end_height, end_width, end_depth).setEnd(true);
		
			if(!openPassages(values.get("PASSAGGI_APERTI"))) return null;
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
		BasicNavigation navigation= new BasicNavigation(world, local_string, common_string);
		return navigation;
	}

	/**
	 * Metodo che crea un oggetto ModifyWorld.
	 * return modify, oggetto ModifyWorld
	 */
	public ModifyWorld getModify() {
		ModifyBasicWorld modify= new ModifyBasicWorld();
		return modify;
	}

	/**
	 * Metodo che apre i passaggi voluti partendo da un array di stringhe
	 * @param array
	 * @pre array non deve essere nullo
	 * @post valore ritornato true, non vi sono stati errori
	 * @return true o false
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
				BasicGround g1= world.searchGround(h1, w1, d1);
				BasicGround g2= world.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					BasicPassage p= world.searchPassage(g1, g2);
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
}
