package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.unibs.ing.myutility.RandomValues;

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
	private int peso_max_trasportabile;
	private int numero_max_trasportabile;
	private int peso_max_chiave;
	private int punteggio_finale;
	private int punteggio_max_prova;
	
	
	
	World(int height, int width, int depth, int peso_max_trasportabile, int numero_max_trasportabile, int peso_max_chiave, int punteggio_finale, int punteggio_max_prova, int points){

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
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new Ground(h, w, d, "Ground "+""+h+""+w+""+d));
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
	
	
	
	
	Ground searchGround(int h, int w, int d){
		for(Ground a: grounds){
			if ((a.getHeight()==h) && (a.getWidth()==w) && (a.getLevel()==d)) return a;
		}
		return null;
	}
	
	
	
	
	Passage searchPassage(Ground a, Ground b){
		for (Passage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	
	Trial searchTrial(String name){
		for(Trial t:trials){
			if(t.getName().equalsIgnoreCase(name));
			return t;
		}
		 return null;
	}
	
	Token searchKeyTypes(String name){
		for(Token t:keytypes){
			if(t.getName().equalsIgnoreCase(name));
			return t;
		}
		 return null;
	}
	
	Token searchPlayerKeys(String name){
		for(Token t:playerkeys){
			if(t.getName().equalsIgnoreCase(name));
			return t;
		}
		 return null;
	}
	
	
	
	void updatePoint(Trial trial, boolean correct){
		int points=trial.getPoints();
		if(correct) this.points=this.points+points;
		else this.points=this.points-points;
		if(this.points<0) this.points=0;
	}




	public ArrayList<Ground> getGrounds() {
		return grounds;
	}




	public ArrayList<Passage> getPassages() {
		return passages;
	}




	public ArrayList<Trial> getTrials() {
		return trials;
	}




	public boolean isDepositata() {
		return depositata;
	}




	public boolean isProva_fatta() {
		return prova_fatta;
	}




	public Integer getPoints() {
		return points;
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




	public int getPunteggio_finale() {
		return punteggio_finale;
	}




	public int getPunteggio_max_prova() {
		return punteggio_max_prova;
	}




	public void setGrounds(ArrayList<Ground> grounds) {
		this.grounds = grounds;
	}




	public void setPassages(ArrayList<Passage> passages) {
		this.passages = passages;
	}




	public void setTrials(ArrayList<Trial> trials) {
		this.trials = trials;
	}




	public void setDepositata(boolean depositata) {
		this.depositata = depositata;
	}




	public void setProva_fatta(boolean prova_fatta) {
		this.prova_fatta = prova_fatta;
	}




	public void setPoints(Integer points) {
		this.points = points;
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




	public void setPunteggio_finale(int punteggio_finale) {
		this.punteggio_finale = punteggio_finale;
	}




	public void setPunteggio_max_prova(int punteggio_max_prova) {
		this.punteggio_max_prova = punteggio_max_prova;
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
	



	
	
	
	

}
