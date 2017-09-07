package game;

import java.io.Serializable;
import java.util.HashMap;

import game.Abstract.Factory;
import game.Advanced.AdvancedFactory;
import game.Basic.BasicFactory;
import game.Medium.MediumFactory;

/**
 * Classe SetupGame, ha il compito di fungere da punto di controllo per la decisione del tipo di Factory da usare
 */
public class SetupGame implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo che in base ai valori delle HashMap di ingresso ritorna l'oggetto factory dei mondi Advanced, Medium o Basic.
	 * @param values
	 * @param common_string
	 * @param local_string
	 * @return factory del mondo corrispondente
	 */
	public static Factory getFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		if((values.get("PASSAGGI_APERTI")!=null)&&(values.get("CHIAVI")!=null)&&(values.get("PROVE")!=null)) return new AdvancedFactory(values, common_string, local_string);
		else if((values.get("PASSAGGI_APERTI")!=null)&&(values.get("CHIAVI")!=null)) return new MediumFactory(values, common_string, local_string);
		else if(values.get("PASSAGGI_APERTI")!=null) return new BasicFactory(values, common_string, local_string);
		else System.out.println("Tipo di mondo sconosciuto");
		return null;
	}

}
