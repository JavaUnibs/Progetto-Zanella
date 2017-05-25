package game;

import java.util.ArrayList;
import java.util.Iterator;

public class MediumWorld extends World{
	
	private static final long serialVersionUID = 2L;
	private ArrayList<MediumGround> grounds;
	private ArrayList<MediumPassage> passages; 
	private ArrayList<Token> keytypes;
	private ArrayList<Token> playerkeys;
	private boolean depositata;
	private int peso_max_trasportabile;
	private int numero_max_trasportabile;
	private int peso_max_chiave;
	
	
	
	MediumWorld(int height, int width, int depth, int peso_max_trasportabile, int numero_max_trasportabile, int peso_max_chiave, String nome_luoghi){

		grounds= new ArrayList<MediumGround>();
		passages= new ArrayList<MediumPassage>();
		keytypes= new ArrayList<Token>();
		playerkeys= new ArrayList<Token>();
		depositata=true;
		this.peso_max_trasportabile=peso_max_trasportabile;
		this.numero_max_trasportabile=numero_max_trasportabile;
		this.peso_max_chiave=peso_max_chiave;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new MediumGround(h, w, d, nome_luoghi+" "+h+""+w+""+d));
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
	
	
	
	
	public MediumGround searchGround(int h, int w, int d){
		for(MediumGround a: grounds){
			if ((a.getHeight()==h) && (a.getWidth()==w) && (a.getLevel()==d)) return a;
		}
		return null;
	}
	
	
	
	
	public MediumPassage searchPassage(Ground a, Ground b){
		for (MediumPassage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	
	
	Token searchKeyTypes(String name){
		for(Token t:keytypes){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
	}
	
	Token searchPlayerKeys(String name){
		for(Token t:playerkeys){
			if(t.getName().equalsIgnoreCase(name))
			return t;
		}
		 return null;
	}
	
	
	int totWeight(){
		int weight=0;
		for(Token key: playerkeys) weight=weight+key.getWeight();
		return weight;
	}

	public ArrayList<MediumGround> getGrounds() {
		return grounds;
	}




	public ArrayList<MediumPassage> getPassages() {
		return passages;
	}



	public boolean isDepositata() {
		return depositata;
	}


	public int getPeso_max_trasportabile() {
		return peso_max_trasportabile;
	}




	public int getNumero_max_trasportabile() {
		return numero_max_trasportabile;
	}




	public int getPeso_max_chiave() {
		return peso_max_chiave;
	}





	public void setGrounds(ArrayList<MediumGround> grounds) {
		this.grounds = grounds;
	}




	public void setPassages(ArrayList<MediumPassage> passages) {
		this.passages = passages;
	}


	public void setDepositata(boolean depositata) {
		this.depositata = depositata;
	}


	public void setPeso_max_trasportabile(int peso_max_trasportabile) {
		this.peso_max_trasportabile = peso_max_trasportabile;
	}




	public void setNumero_max_trasportabile(int numero_max_trasportabile) {
		this.numero_max_trasportabile = numero_max_trasportabile;
	}




	public void setPeso_max_chiave(int peso_max_chiave) {
		this.peso_max_chiave = peso_max_chiave;
	}


	public ArrayList<Token> getKeytypes() {
		return keytypes;
	}


	public ArrayList<Token> getPlayerkeys() {
		return playerkeys;
	}




	public void setKeytypes(ArrayList<Token> keytypes) {
		this.keytypes = keytypes;
	}


	public void setPlayerkeys(ArrayList<Token> playerkeys) {
		this.playerkeys = playerkeys;
	}


	public MediumGround getStartGround() {
		for(MediumGround ground: grounds) if(ground.isStart()) return ground;
		return null;
	}

}
