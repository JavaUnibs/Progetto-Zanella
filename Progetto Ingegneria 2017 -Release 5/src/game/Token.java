package game;

import java.io.Serializable;

/**
 * Classe che rappresenta le chiavi per l'apertura dei passaggi chiusi.
 *
 */

public class Token implements Serializable{
	
	
	private int weight;
	private String name;
	private static final long serialVersionUID = 2L;
	
	/**
	 * Costruttore della classe, inizializza le chiavi con i valori di peso e nome con il quale viene chiamato.
	 * @param weight
	 * @param name
	 * @pre weight > 0
	 */
	public Token(int weight, String name) {
		super();
		this.weight = weight;
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String toString() {
		return name+" ("+weight+") \n ";
	}
	
	
	
	
	
}
