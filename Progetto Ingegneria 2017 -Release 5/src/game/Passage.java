package game;

import java.io.Serializable;

/**
 * Classe che rappresenta i passaggi tra due luoghi
 */
public class Passage implements Serializable{
	
	private static final long serialVersionUID = 2L;
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	private Token key;
	
	/**
	 * Costruttore della classe Passage, crea un passaggio tra i due luoghi forniti.
	 * @param grounda
	 * @param groundb
	 * @pre i luoghi devono esistere
	 */
	Passage(Ground grounda, Ground groundb){
		this.grounda=grounda;
		this.groundb=groundb;
		key=null;
		id=""+grounda.getHeight()+""+grounda.getWidth()+""+grounda.getLevel()+"-"+groundb.getHeight()+""+groundb.getWidth()+""+groundb.getLevel();
		open=false;
		
	}

	/**
	 * Metodo che verifica se il passaggio è aperto
	 * @return open boolean
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Metodo che ritorna il luogo a associato al passaggio
	 * @return grounda
	 */
	public Ground getGrounda() {
		return grounda;
	}

	/**
	 * Metodo che ritorna il luogo b associato al passaggio
	 * @return groundb
	 */
	public Ground getGroundb() {
		return groundb;
	}

	/**
	 * Metodo che ritorna l'id del passaggio
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metodo per settare il passaggio come aperto
	 * @param open
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	/**
	 * Metodo per settare la tipologia di chiave per aprire il passaggio
	 * @param key oggetto Token
	 */
	public void setKey(Token key){
		this.key=key;
	}
	
	/**
	 * Metodo che ritorna la chiave per aprire il passaggio
	 * @return
	 */
	public Token getKey(){
		return key;
	}

	

}
