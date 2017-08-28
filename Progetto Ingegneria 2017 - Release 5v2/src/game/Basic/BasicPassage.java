package game.Basic;

import game.Abstract.Ground;
import game.Abstract.Passage;

/**
 * Classe concreta che estemde l'abstract product Passage. Rappresenta i passaggi tra due luoghi del mondo di base.
 * @author vxrich
 *
 */
public class BasicPassage extends Passage {
	
	private static final long serialVersionUID = 1L;
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	
	/**
	 * Costruttore della classe BasicPassage, crea un passaggio tra i due luoghi forniti
	 * @param grounda
	 * @param groundb
	 * @pre i luoghi non devono esistere
	 */
	BasicPassage(Ground grounda, Ground groundb){
		this.grounda=grounda;
		this.groundb=groundb;
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
	

}
