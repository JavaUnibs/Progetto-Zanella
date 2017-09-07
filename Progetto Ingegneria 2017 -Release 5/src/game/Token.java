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
	 * @pre weight > 0, name diverso da null
	 */
	public Token(int weight, String name) {
		super();
		this.weight = weight;
		this.name = name;
	}

	/**
	 * Metodo che ritorna il peso della chiave
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * Metodo che ritorna il nome associato alla chiave
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Metodo che permette di settare il peso della chiave
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Metodo che permette di settare il nome associato alla chiave
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Metodo che ritorna una  stringa con nome e peso della chiave
	 * @return name + weight della chiave
	 */
	public String toString() {
		return name+" ("+weight+") \n ";
	}
	
	
	
	
	
}
