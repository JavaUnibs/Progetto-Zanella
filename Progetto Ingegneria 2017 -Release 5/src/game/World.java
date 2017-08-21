package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.unibs.ing.myutility.RandomValues;

/**
 * Classe che rappresenta il mondo di gioco caratterizzato da Ground, Passage, Token, Trial
 */
public class World implements Serializable{

	private static final long serialVersionUID = 2L;
	private ArrayList<Ground> grounds;
	private ArrayList<Passage> passages; 
	private ArrayList<Token> keytypes;
	private ArrayList<Token> playerkeys;
	private ArrayList<Trial> trials;
	private boolean depositata;
	private boolean prova_fatta;
	private Integer points;
	private String nome_luoghi;
	private int peso_max_trasportabile;
	private int numero_max_trasportabile;
	private int peso_max_chiave;
	private int punteggio_finale;
	private int punteggio_max_prova;
	
	/**
	 * Costruttore per la classe World genera il mondo, che permetterà al giocatore di giocare, con i relativi parametri assegnati, che riguardano sia la dimensione del mondo 
	 * che le caratteristiche relative alla partita, come il punteggio, e al giocatore.
	 * 
	 * @param height
	 * @param width
	 * @param depth
	 * @param peso_max_trasportabile
	 * @param numero_max_trasportabile
	 * @param peso_max_chiave
	 * @param punteggio_finale
	 * @param punteggio_max_prova
	 * @param points 
	 * @param nome_luoghi
 	 */
	
	World(int height, int width, int depth, int peso_max_trasportabile, int numero_max_trasportabile, int peso_max_chiave, int punteggio_finale, int punteggio_max_prova, int points, String nome_luoghi){

		grounds= new ArrayList<Ground>();
		passages= new ArrayList<Passage>();
		keytypes= new ArrayList<Token>();
		playerkeys= new ArrayList<Token>();
		trials= new ArrayList<Trial>();
		depositata=true;
		prova_fatta=false;

		this.peso_max_trasportabile=peso_max_trasportabile;
		this.numero_max_trasportabile=numero_max_trasportabile;
		this.peso_max_chiave=peso_max_chiave;
		this.punteggio_finale=punteggio_finale;
		this.punteggio_max_prova=punteggio_max_prova;
		this.points=points;
		this.nome_luoghi=nome_luoghi;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new Ground(h, w, d, nome_luoghi+" "+h+""+w+""+d));
				}
			}
		}
		
		for(int i=0;i<grounds.size();i++){                                    //genera tutti i possibili passaggi (anche quelli non ammessi)
			for(int z=(i+1);z<grounds.size();z++){
				passages.add(new Passage(grounds.get(i), grounds.get(z)));
			}
			
		}
		
		Iterator<Passage> iter=passages.iterator();
		
		while(iter.hasNext()){                                                //rimuove i passaggi impossibili nel mondo
			
			Passage next=iter.next();
			int ha=next.getGrounda().getHeight();
			int hb=next.getGroundb().getHeight();
			int wa=next.getGrounda().getWidth();
			int wb=next.getGroundb().getWidth();
			int da=next.getGrounda().getLevel();
			int db=next.getGroundb().getLevel();
			
			if(!(((hb==ha+1||hb==ha-1)&&wb==wa&&da==db)||((wb==wa+1||wb==wa-1)&&hb==ha&&da==db)||((db==da+1||db==da-1)&&wb==wa&&ha==hb))){
			iter.remove();
			}
			
		}
		

	}//Fine costruttore
	
	
	
	/**
	 * Metodo per la ricerca di un oggeto di tipo Ground, utilizzato per restituire la posizione del giocatore durante il gioco.
	 * 
	 * @param h maggiore di 0 e minore o uguale dell'altezza del mondo
	 * @param w maggiore di 0 e minore o uguale della larghezza del mondo
	 * @param d maggiore di 0 e minore o uguale della profondità del mondo
	 * @pre h w d non devono essere null
	 * @post valore ritornato non null
	 * @return a o null, oggetto di tipo Ground o nullo nel caso non sia stata trovata una corrispondenza
	 */
	Ground searchGround(int h, int w, int d){
		for(Ground a: grounds){
			if ((a.getHeight()==h) && (a.getWidth()==w) && (a.getLevel()==d)) return a;
		}
		return null;
	}
	
	
	
	/**
	 * Metodo per determinare l'esistenza o meno del passaggio tra due luoghi a e b 
	 * 
	 * @param a luogo corrente
	 * @param b luogo futuro
	 * @pre a e b non devono essere null
	 * @post valore ritornato non null
	 * @return z o null, oggetto di tipo Passage oppure nullo 
	 */
	Passage searchPassage(Ground a, Ground b){
		for (Passage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	
	/**
	 * Metodo utilizzato per verificare l'esistenza di una prova nel mondo.
	 * 
	 * @param name, nome della prova da cercare
	 * @pre name non deve essere null
	 * @post valore ritornato non null
	 * @return t o null, oggetto Trial o null 
	 */
	Trial searchTrial(String name){
		for(Trial t:trials){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
	}
	
	/**
	 * Metodo utilizzato per verificare l'esistenza di una chiave nel mondo.
	 * 
	 * @param name, nome della chiave da cercare
	 * @pre name non deve essere null
	 * @post valore ritornato non null
	 * @return t o null, oggetto Token o null 
	 */
	Token searchKeyTypes(String name){
		for(Token t:keytypes){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
	}
	
	/**
	 * Metodo che cerca una chiave tra le chiavi del giocatore.
	 * @param name
	 * @pre name non deve essere null
	 * @post valore ritornato non null
	 * @return
	 */
	Token searchPlayerKeys(String name){
		for(Token t:playerkeys){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
	}
	
	
	/**
	 * Metodo per l'aggiornamento dei punti in base alla risposta data alla prova.
	 * 
	 * @param trial, oggetto Trial che identifica la prova sostenuta
	 * @param correct valore booleano che contiene il risultato della risposta, true o false.
	 */
	void updatePoint(Trial trial, boolean correct){
		int points=trial.getPoints();
		if(correct) this.points=this.points+points;
		else this.points=this.points-points;
		if(this.points<0) this.points=0;
	}
	
	/**
	 * Metodo che ritorna il peso totale delle chiavi
	 * 
	 * @post weight >= 0
	 * @return weight peso totale delle chiavi trasportate dal giocatore
	 */
	int totWeight(){
		int weight=0;
		for(Token key: playerkeys) weight=weight+key.getWeight();
		return weight;
	}

	/**
	 * Metodo che ritorna un ArrayList di tutti i luoghi del mondo.
	 * @return grounds
	 */
	public ArrayList<Ground> getGrounds() {
		return grounds;
	}

	/**
	 * Metodo che ritorna un ArrayList di tutti i passaggi del mondo.
	 * @return passages
	 */
	public ArrayList<Passage> getPassages() {
		return passages;
	}

	/**
	 * Metodo che ritorna un ArrayList di tutte le prove del mondo.
	 * @return trials
	 */
	public ArrayList<Trial> getTrials() {
		return trials;
	}

	/**
	 * Metodo che verifica se la chiave è stata depositata nel mondo.
	 * @return depositata valore booleano
	 */
	public boolean isDepositata() {
		return depositata;
	}

	/**
	 * Metodo che ritorna true se è stata sostenuta la prova
	 * @return prova_fatta
	 */
	public boolean isProva_fatta() {
		return prova_fatta;
	}

	/**
	 * Metodo che ritorna i punti di una prova
	 * @return points
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * Metodo che ritorna il peso massimo trasportabile
	 * @return peso_max_trasportabile
	 */
	public int getPeso_max_trasportabile() {
		return peso_max_trasportabile;
	}

	/**
	 * Metodo che ritorna il nuemro massimo di chiavi trasportabile
	 * @return nmero_max_trasportabile
	 */
	public int getNumero_max_trasportabile() {
		return numero_max_trasportabile;
	}

	 /**
	  * Metodo che ritorna il peso massimo per chiave
	  * @return peso_max_chiave
	  */
	public int getPeso_max_chiave() {
		return peso_max_chiave;
	}

	/**
	 * Metodo ceh ritorna il punteggio finale 
	 * @return punteggio_finale
	 */
	public int getPunteggio_finale() {
		return punteggio_finale;
	}

	/**
	 * Metodo che ritorna il punteggio massimo di una prova
	 * @return punteggio_max_prova
	 */
	public int getPunteggio_max_prova() {
		return punteggio_max_prova;
	}

	/**
	 * Metodo per settare i lugohi del mondo
	 * @param grounds
	 */
	public void setGrounds(ArrayList<Ground> grounds) {
		this.grounds = grounds;
	}

	/**
	 * Metodo per settare i passaggi del mondo
	 * @param passages
	 */
	public void setPassages(ArrayList<Passage> passages) {
		this.passages = passages;
	}

	/**
	 * Metodo per settare le prove del mondo
	 * @param trials
	 */
	public void setTrials(ArrayList<Trial> trials) {
		this.trials = trials;
	}

	/**
	 * Metodo per definire quando una chiave è stata depositata
	 * @param depositata
	 */
	public void setDepositata(boolean depositata) {
		this.depositata = depositata;
	}

	/**
	 * Metodo che setta se una prova è stata sostenuta
	 * @param prova_fatta
	 */
	public void setProva_fatta(boolean prova_fatta) {
		this.prova_fatta = prova_fatta;
	}

	/**
	 * Metodo che setta il punteggio del giocatore
	 * @param points
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}

	/**
	 * Metodo che setta il massimo peso trasportabile
	 * @param peso_max_trasportabile
	 */
	public void setPeso_max_trasportabile(int peso_max_trasportabile) {
		this.peso_max_trasportabile = peso_max_trasportabile;
	}

	/**
	 * Metodo che setta il massimo numero di chiavi trasportabile
	 * @param peso_max_trasportabile
	 */
	public void setNumero_max_trasportabile(int numero_max_trasportabile) {
		this.numero_max_trasportabile = numero_max_trasportabile;
	}

	/**
	 * Metodo che setta il peso massimo di una chiave
	 * @param peso_max_chiave
	 */
	public void setPeso_max_chiave(int peso_max_chiave) {
		this.peso_max_chiave = peso_max_chiave;
	}

	/**
	 * Metodo che setta il punteggio finale
	 * @param punteggio_finale
	 */
	public void setPunteggio_finale(int punteggio_finale) {
		this.punteggio_finale = punteggio_finale;
	}

	/**
	 * Metodo che setta il punteggio massimo di una prova
	 * @param punteggio_max_prova
	 */
	public void setPunteggio_max_prova(int punteggio_max_prova) {
		this.punteggio_max_prova = punteggio_max_prova;
	}

	/**
	 * Metodo che ritorna un ArrayList contenente i tipi di tutte le chiavi
	 * @return keytypes
	 */
	public ArrayList<Token> getKeytypes() {
		return keytypes;
	}

	/**
	 * Metodo che ritorna un ArrayList contenente i tipi di tutte le chiavi possedute dal giocatore
	 * @return playertypes
	 */
	public ArrayList<Token> getPlayerkeys() {
		return playerkeys;
	}

	/**
	 * Metodo che setta l'ArrayList contenente i tipi delle chiavi
	 * @return keytypes
	 */
	public void setKeytypes(ArrayList<Token> keytypes) {
		this.keytypes = keytypes;
	}

	/**
	 * Metodo che setta l'ArrayList contenente i tipi di chiavi del giocatore
	 * @return keytypes
	 */
	public void setPlayerkeys(ArrayList<Token> playerkeys) {
		this.playerkeys = playerkeys;
	}
	



	
	
	
	

}
