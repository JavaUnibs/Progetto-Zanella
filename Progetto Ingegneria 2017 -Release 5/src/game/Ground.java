package game;

import java.io.Serializable;

/**
 * Classe che rappresenta ogni luogo di cui è composto il mondo
 */
public class Ground implements Serializable{
	
	private static final long serialVersionUID = 2L;
	private int level;
	private int height;
	private int width;
	private boolean start;
	private boolean end;
	private String name;
	private Token key;
	private Trial trial;
	
	/**
	 * Costruttore della classe Ground, inizializza le variabili con i valori forniti
	 * @param height
	 * @param width
	 * @param level
	 * @param name
	 * @pre i valori non devono essere nulli
	 */
	Ground(int height, int width, int level, String name){
		this.level=level;
		this.name=name;
		this.height=height;
		this.width=width;
		key=null;
		trial=null;
		start=false;
		end=false;
	}

	/**
	 * Fornisce il livello
	 * @return level valore del livello
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Metodo che ritorna la coordinata y
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Metodo che ritorna la coordianta x
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Metodo che verifica se siamo sul punto di partenza
	 * @return start true o false
	 */
	public boolean isStart() {
		return start;
	}
	
	/** 
	 * Metodo che verifica se abbiamo raggiunto la fine del gioco
	 * @return end true o false
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * Metodo che ritorna il nome del luogo
	 * @return name stringa
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Metodo che setta il livello
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Metodo che setta la coordinata y
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Metodo che setta la coordinata x
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Metodo che setta il punto di partenza
	 * @param start
	 */
	public void setStart(boolean start) {
		this.start = start;
	}

	/**
	 * Metodo che setta il punto di arrivo
	 * @param end
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	/**
	 * Metodo che setta il nome del luogo
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Metodo che setta la chiave presente nel luogo
	 * @param key
	 */
	public void setKey(Token key){
		this.key=key;
	}
	
	/**
	 * Metodo che ritorna l'oggetto chiave
	 * @return key oggetto Token
	 */
	public Token getKey(){
		return key;
	}

	/** 
	 * Metodo che ritorna l'ggetto prova
	 * @return trial oggetto Trial
	 */
	public Trial getTrial() {
		return trial;
	}

	/**
	 * Metodo che setta la prova relativa ad un luogo
	 * @param trial
	 */
	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	
	
	
	
	


}
