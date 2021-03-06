package game.Medium;

import game.Token;
import game.Abstract.Ground;
import game.Abstract.Passage;

/**
 * Classe concrreta che estende l'abstract product Passage. Rappresenta il passaggio tra due luoghi.
 */
public class MediumPassage extends Passage {
	

	private static final long serialVersionUID = 1L;
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	private Token key;
	
	/**
	 * Costruttore della classe MediumPassage, crea un passaggio tra i due luoghi forniti.
	 * @param grounda
	 * @param groundb
	 * @pre i luoghi non devono esistere
	 */
	public MediumPassage(Ground grounda, Ground groundb){
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
