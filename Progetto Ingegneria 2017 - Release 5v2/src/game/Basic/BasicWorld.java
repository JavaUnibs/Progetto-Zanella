package game.Basic;

import java.util.ArrayList;
import java.util.Iterator;

import game.Abstract.Ground;
import game.Abstract.Passage;
import game.Abstract.World;

/**
 * Classe concreta che estende l'abstract product World. Rappresenta il mondo di gioco.
 */
public class BasicWorld extends World{
	

	private static final long serialVersionUID = 1L;
	private ArrayList<BasicGround> grounds;
	private ArrayList<BasicPassage> passages; 
	private boolean deposited;
	private boolean trial_done;
	
	
	/**
	 * Costruttore della classe BasicWorld, che genera il mondo.
	 * @param height
	 * @param width
	 * @param depth
	 * @param ground_name
	 */
	BasicWorld(int height, int width, int depth, String ground_name){

		grounds= new ArrayList<BasicGround>();
		passages= new ArrayList<BasicPassage>();
		deposited=true;
		trial_done=false;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new BasicGround(h, w, d, ground_name+" "+h+""+w+""+d));
				}
			}
		}
		
		for(int i=0;i<grounds.size();i++){                                    //genera tutti i possibili passaggi (anche quelli non ammessi)
			for(int z=(i+1);z<grounds.size();z++){
				passages.add(new BasicPassage(grounds.get(i), grounds.get(z)));
			}
			
		}
		
		Iterator<BasicPassage> iter=passages.iterator();
		
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
	 * Metodo per la ricerca di un oggeto di tipo BasicGround, utilizzato per restituire la posizione del giocatore durante il gioco.
	 * 
	 * @param h maggiore di 0 e minore o uguale dell'altezza del mondo
	 * @param w maggiore di 0 e minore o uguale della larghezza del mondo
	 * @param d maggiore di 0 e minore o uguale della profondità del mondo
	 * @pre h w d non devono essere null
	 * @post valore ritornato non null
	 * @return a o null, oggetto di tipo BasicGround o nullo nel caso non sia stata trovata una corrispondenza
	 */
	public BasicGround searchGround(int h, int w, int d){
		for(BasicGround a: grounds){
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
	 * @return z o null, oggetto di tipo BasicPassage oppure nullo 
	 */
	public BasicPassage searchPassage(Ground a, Ground b){
		for (BasicPassage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	

	/**
	 * Metodo che ritorna un ArrayList di tutti i luoghi del mondo.
	 * @return grounds
	 */
	public ArrayList<BasicGround> getGrounds() {
		return grounds;
	}

	/**
	 * Metodo che ritorna un ArrayList di tutti i passaggi del mondo.
	 * @return passages
	 */
	public ArrayList<BasicPassage> getPassages() {
		return passages;
	}
	
	/**
	 * Metodo per settare i lugohi del mondo
	 * @param grounds
	 */
	public void setGrounds(ArrayList<BasicGround> grounds) {
		this.grounds = grounds;
	}
	
	/**
	 * Metodo per settare i passaggi del mondo
	 * @param passages
	 */
	public void setPassages(ArrayList<BasicPassage> passages) {
		this.passages = passages;
	}

	/**
	 * Metodo che ritorna il luogo di partenza
	 * @post ground non deve essere nullo
	 * @return ground
	 */
	public BasicGround getStartGround() {
		for(BasicGround ground: grounds) if(ground.isStart()) return ground;
		return null;
	}


	
}
