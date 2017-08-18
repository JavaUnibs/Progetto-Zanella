package game;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe che permette il salvataggio e il caricamento del mondo
 *
 */
public class Save implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private World mondo;
	private Ground luogo_corrente;
	private HashMap<String, String> local_string;
	private HashMap<String, String[]> values;
	
	/**
	 * Costruttore della classe Save, crea un oggetto contenente i dati relativi al mondo e alla posizione del giocatore
	 * 
	 * @param mondo
	 * @param luogo
	 * @param map
	 * @param map1
	 * @pre i valori non devono essere nulli
	 */
	Save(World mondo, Ground luogo, HashMap<String, String> map, HashMap<String, String[]> map1){
		this.mondo=mondo;
		luogo_corrente=luogo;
		local_string=map;
		values=map1;
	}

	public World getMondo() {
		return mondo;
	}

	public Ground getLuogo_corrente() {
		return luogo_corrente;
	}

	public HashMap<String, String> getLocalString(){
		return local_string;
	}
	
	public HashMap<String, String[]> getValues(){
		return values;
	}

}
