package game;

import java.io.Serializable;

/**
 * Classe che rappresenta i passaggi tra duo luoghi
 */
public class Passage implements Serializable{
	
	private static final long serialVersionUID = 2L;
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	private Token key;
	
	/**
	 * Costruttore della classe Passage, crea un passaggio tra i due loghi forniti
	 * @param grounda
	 * @param groundb
	 * @pre i loghi non devono esistere
	 */
	Passage(Ground grounda, Ground groundb){
		this.grounda=grounda;
		this.groundb=groundb;
		key=null;
		id=""+grounda.getHeight()+""+grounda.getWidth()+""+grounda.getLevel()+"-"+groundb.getHeight()+""+groundb.getWidth()+""+groundb.getLevel();
		open=false;
		
	}

	public boolean isOpen() {
		return open;
	}

	public Ground getGrounda() {
		return grounda;
	}

	public Ground getGroundb() {
		return groundb;
	}

	public String getId() {
		return id;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void setKey(Token key){
		this.key=key;
	}
	
	public Token getKey(){
		return key;
	}

	

}
