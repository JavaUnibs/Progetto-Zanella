package game.Medium;

import java.util.ArrayList;
import java.util.Iterator;

import game.Token;
import game.Abstract.Ground;
import game.Abstract.Passage;
import game.Abstract.World;

/**
 * Classe concreta che estende l'abstract product World. Rappresenta il mondo di gioco.
 */
public class MediumWorld extends World{
	

	private static final long serialVersionUID = 1L;
	private ArrayList<MediumGround> grounds;
	private ArrayList<MediumPassage> passages; 
	private ArrayList<Token> keytypes;
	private ArrayList<Token> playerkeys;
	private boolean deposited;
	private int max_transportable_keys_weight;
	private int max_transportable_keys_number;
	private int max_key_weight;
	
	
	/**
	 * Costruttore della classe MediumWorld, che genera il mondo.
	 * @param height
	 * @param width
	 * @param depth
	 * @param max_transportable_keys_weight
	 * @param max_transportable_keys_number
	 * @param max_key_weight
	 * @param _ground_name
	 * @pre i parametri passati non siano null
	 */
	MediumWorld(int height, int width, int depth, int max_transportable_keys_weight, int max_transportable_keys_number, int max_key_weight, String _ground_name){

		grounds= new ArrayList<MediumGround>();
		passages= new ArrayList<MediumPassage>();
		keytypes= new ArrayList<Token>();
		playerkeys= new ArrayList<Token>();
		deposited=true;
		this.max_transportable_keys_weight=max_transportable_keys_weight;
		this.max_transportable_keys_number=max_transportable_keys_number;
		this.max_key_weight=max_key_weight;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new MediumGround(h, w, d, _ground_name+" "+h+""+w+""+d));
				}
			}
		}
		
		for(int i=0;i<grounds.size();i++){                                    //genera tutti i possibili passaggi (anche quelli non ammessi)
			for(int z=(i+1);z<grounds.size();z++){
				passages.add(new MediumPassage(grounds.get(i), grounds.get(z)));
			}
			
		}
		
		Iterator<MediumPassage> iter=passages.iterator();
		
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
	 * Metodo per la ricerca di un oggeto di tipo MediumGround, utilizzato per restituire la posizione del giocatore durante il gioco.
	 * 
	 * @param h maggiore di 0 e minore o uguale dell'altezza del mondo
	 * @param w maggiore di 0 e minore o uguale della larghezza del mondo
	 * @param d maggiore di 0 e minore o uguale della profondità del mondo
	 * @pre h w d non devono essere null
	 * @return a o null, oggetto di tipo MediumGround o nullo nel caso non sia stata trovata una corrispondenza
	 */
	public MediumGround searchGround(int h, int w, int d){
		for(MediumGround a: grounds){
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
	 * @return z o null, oggetto di tipo MediumPassage oppure nullo 
	 */
	public MediumPassage searchPassage(Ground a, Ground b){
		for (MediumPassage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	

	/**
	 * Metodo utilizzato per verificare l'esistenza di una chiave nel mondo.
	 * 
	 * @param name, nome della chiave da cercare
	 * @pre name non deve essere null
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
	 * @return t
	 */
	Token searchPlayerKeys(String name){
		for(Token t:playerkeys){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
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
	public ArrayList<MediumGround> getGrounds() {
		return grounds;
	}



	/**
	 * Metodo che ritorna un ArrayList di tutti i passaggi del mondo.
	 * @return passages
	 */
	public ArrayList<MediumPassage> getPassages() {
		return passages;
	}


	/**
	 * Metodo che verifica se la chiave è stata depositata nel mondo.
	 * @return deposited valore booleano
	 */
	public boolean isDeposited() {
		return deposited;
	}

	/**
	 * Metodo che ritorna il peso massimo trasportabile
	 * @return max_transportable_keys_weight
	 */
	public int getMax_transportable_keys_weight() {
		return max_transportable_keys_weight;
	}



	/**
	 * Metodo che ritorna il nuemro massimo di chiavi trasportabile
	 * @return max_transportable_keys_number
	 */
	public int getMax_transportable_keys_number() {
		return max_transportable_keys_number;
	}



	/**
	  * Metodo che ritorna il peso massimo per chiave
	  * @return max_key_weight
	  */
	public int getMax_key_weight() {
		return max_key_weight;
	}




	/**
	 * Metodo per settare i lugohi del mondo
	 * @param grounds
	 */
	public void setGrounds(ArrayList<MediumGround> grounds) {
		this.grounds = grounds;
	}


	/**
	 * Metodo per settare i passaggi del mondo
	 * @param passages
	 */
	public void setPassages(ArrayList<MediumPassage> passages) {
		this.passages = passages;
	}

	/**
	 * Metodo per definire quando una chiave è stata depositata
	 * @param deposited
	 */
	public void setDeposited(boolean depositata) {
		this.deposited = depositata;
	}

	/**
	 * Metodo che setta il massimo peso trasportabile
	 * @param max_transportable_keys_weight
	 */
	public void setMax_transportable_keys_weight(int max_transportable_keys_weight) {
		this.max_transportable_keys_weight = max_transportable_keys_weight;
	}



	/**
	 * Metodo che setta il massimo numero di chiavi trasportabile
	 * @param max_transportable_keys_number
	 */
	public void setMax_transportable_keys_number(int max_transportable_keys_number) {
		this.max_transportable_keys_number = max_transportable_keys_number;
	}



	/**
	 * Metodo che setta il peso massimo di una chiave
	 * @param max_key_weight
	 */
	public void setMax_key_weight(int max_key_weight) {
		this.max_key_weight = max_key_weight;
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
	 * @param keytypes
	 */
	public void setKeytypes(ArrayList<Token> keytypes) {
		this.keytypes = keytypes;
	}

	/**
	 * Metodo che setta l'ArrayList contenente i tipi di chiavi del giocatore
	 * @param playerkeys
	 */
	public void setPlayerkeys(ArrayList<Token> playerkeys) {
		this.playerkeys = playerkeys;
	}


	/**
	 * Metodo che ritorna il luogo di partenza
	 * @post ground non deve essere nullo
	 * @return ground
	 */
	public MediumGround getStartGround() {
		for(MediumGround ground: grounds) if(ground.isStart()) return ground;
		return null;
	}

}
