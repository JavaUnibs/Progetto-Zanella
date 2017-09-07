package game;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.ing.myutility.RandomValues;

/**
 * Classe che rappresenta l'oggetto Trial, ovvero le prove che il giocatore dovrà sostenere.
 */
public class Trial implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private int points;
	private String name;
	private HashMap<String,String> quiz;
	
	/**
	 * Costruttore della classe Trial, inizializza ogni prova attribuendo un punteggio e un nome.
	 * @pre points >=0, name diverso da null
	 * @param points
	 * @param name
	 */
	Trial(int points, String name){
		this.points=points;
		this.name=name;
		quiz= new HashMap<String,String>();
	}

	/**
	 * Metodo che ritorna i punti associati ad una prova
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Metodo che ritorna il nome della prova
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo che ritorna un'HashMap contente le prove
	 * @return quiz
	 */
	public HashMap<String, String> getQuiz() {
		return quiz;
	}

	/**
	 * Metodo che permette di settare i punti relativia ad un prova
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Metodo che permette di settare il nome di una prova
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Metodo che permette di settare l'HashMap delle prove 
	 * @param quiz
	 */
	public void setQuiz(HashMap<String, String> quiz) {
		this.quiz = quiz;
	}

	/**
	 * Metodo che ritorna casualmente una prova.
	 * 
	 * @pre il campo quiz non sia vuoto
	 * @return oggetto Trial presente nell'HashMap quiz 
	 */
	String getQuestion(){
		
			int rnd=RandomValues.ranIntLimite(0, quiz.size()-1);
			String[] array = new String[quiz.size()];
			array=quiz.keySet().toArray(array);
			return array[rnd];
			
	}
	
	/**
	 * Metodo che ritorna true se la risposta alla domanda passata è corretta, altrimenti false
	 * @pre domanda e risposta non siano null
	 * @param domanda nome della domanda passata
	 * @param risposta
	 * @return true se risposta corretta, false se risposta è errata
	 */
	boolean getAnswer(String domanda, String risposta){
		
		if(quiz.get(domanda).equalsIgnoreCase(risposta)) return true;
		return false;
	}

	/**
	 * Metodo che sovrascrive toString permettendo di ritornare nome epunteggio della prova
	 * @return stringa
	 */
	@Override
	public String toString() {
		return name+" Punti: "+points+"\n";
	}
	
	
	

	
	
	
	
	

}
