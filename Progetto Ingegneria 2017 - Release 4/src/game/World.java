package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.unibs.ing.myutility.RandomValues;

public class World implements Serializable{

	private static final long serialVersionUID = 2L;
	private final String NO_GROUND="E' stata immessa una stringa con una chiave inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String INCORRECT_STRING3="Le prove da mettere nei luoghi non sono definite in modo corretto";
	private ArrayList<Ground> grounds;
	private ArrayList<Passage> passages; 
	private ArrayList<Token> keys;
	private HashMap<String,String> quiz_storia;
	private HashMap<String,String> quiz_scienza;
	private HashMap<String,String> quiz_arte;
	private boolean depositata;
	private boolean prova_fatta;
	private Integer points;
	
	
	
	World(int height, int width, int depth){

		grounds= new ArrayList<Ground>();
		passages= new ArrayList<Passage>();
		keys= new ArrayList<Token>();
		
		quiz_storia = new HashMap<String,String>();
		quiz_scienza = new HashMap<String,String>();
		quiz_arte = new HashMap<String,String>();
		
		depositata=true;
		prova_fatta=false;
		points=10;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new Ground(h, w, d, "Stanza "+""+h+""+w+""+d));
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
		

	}
	
	
	
	
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
	
	int totWeight(){
		int weight=0;
		for(Token key: keys) weight=weight+key.getWeight();
		return weight;
	}
	
	boolean openPassages(String[] array){                     //apre i passaggi voluti partendo da un array di stringhe 

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				Ground g1= searchGround(h1, w1, d1);
				Ground g2= searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					Passage p= searchPassage(g1, g2);
					if (p!=null) {
						p.setOpen(true);
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING);
			return false;
		}
		
		return true;
	}
	
	
	
	boolean putKeyGrounds(String[] array){
		
		try{
			for(int i=0;i<array.length;i++){
				int h=Integer.parseInt(array[i].substring(0, 1));
				int w=Integer.parseInt(array[i].substring(1, 2));
				int d=Integer.parseInt(array[i].substring(2, 3));
				Token key= Token.valueOf(array[i].substring(4));
				Ground gtemp;
				
				if(key!=null){
					if((gtemp=searchGround(h, w, d))!=null){
						gtemp.setKey(key);
					} else{
						
						System.out.println(NO_KEY);
						return false;
					}
				}else{
					
					System.out.println(NO_GROUND);
					return false;
				}
			}
			
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	
	boolean putKeyPassages(String[] array){                      

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				Token key= Token.valueOf(array[i].substring(8));
				Ground g1= searchGround(h1, w1, d1);
				Ground g2= searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					Passage p= searchPassage(g1, g2);
					if (p!=null) {
						if(key!=null){
							p.setKey(key);
						}else {
							System.out.println(NO_KEY);
							return false;
						}
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	
	boolean putTrialsGrounds(String[] array){
		
		for(int i=0;i<array.length;i++){
			int h1=Integer.parseInt(array[i].substring(0, 1));
			int w1=Integer.parseInt(array[i].substring(1, 2));
			int d1=Integer.parseInt(array[i].substring(2, 3));
			Ground g1= searchGround(h1, w1, d1);
			String temp=array[i].substring(4);
			String domanda= temp.substring(0, temp.indexOf("-"));
			String risposta=temp.substring(temp.indexOf("-")+1, temp.lastIndexOf("-"));
			Trial trial= Trial.valueOf(temp.substring(temp.lastIndexOf("-")+1));
			
			if (trial!=null&&g1!=null){
				if(trial==Trial.Arte) quiz_arte.put(domanda, risposta);
				if(trial==Trial.Scienza) quiz_scienza.put(domanda, risposta);
				if(trial==Trial.Storia) quiz_storia.put(domanda, risposta);
				g1.setTrial(trial);
			}else {
				System.out.println(INCORRECT_STRING3);
				return false;
			}
		}
		
		return true;
	}
	
	
	String getQuestion(Trial trial){
		
		if(trial==Trial.Arte){
			int rnd=RandomValues.ranIntLimite(0, quiz_arte.size()-1);
			String[] array = new String[quiz_arte.size()];
			array=quiz_arte.keySet().toArray(array);
			return array[rnd];
			
		} else if(trial==Trial.Scienza){
			int rnd=RandomValues.ranIntLimite(0, quiz_scienza.size()-1);
			String[] array = new String[quiz_scienza.size()];
			array=quiz_scienza.keySet().toArray(array);
			return array[rnd];
		} else{
			int rnd=RandomValues.ranIntLimite(0, quiz_storia.size()-1);
			String[] array = new String[quiz_storia.size()];
			array=quiz_storia.keySet().toArray(array);
			return array[rnd];
		}
	}
	
	
	boolean getAnswer(Trial trial, String domanda, String risposta){
		HashMap<String,String> quiz;
		if(trial==Trial.Arte) quiz=quiz_arte;
		else if(trial==Trial.Scienza) quiz=quiz_scienza;
		else quiz=quiz_storia;
		
	
		if(quiz.get(domanda).equalsIgnoreCase(risposta)) return true;
		return false;
	}
	
	
	void updatePoint(Trial trial, boolean correct){
		int points=trial.getPoints();
		if(correct) this.points=this.points+points;
		else this.points=this.points-points;
		if(this.points<0) this.points=0;
	}
	


	public Integer getPoints() {
		return points;
	}




	public void setPoints(Integer points) {
		this.points = points;
	}




	public ArrayList<Token> getKeys() {
		return keys;
	}


	public void setKeys(ArrayList<Token> keys) {
		this.keys = keys;
	}




	public boolean isDepositata() {
		return depositata;
	}




	public void setDepositata(boolean depositata) {
		this.depositata = depositata;
	}




	public boolean isProva_fatta() {
		return prova_fatta;
	}




	public void setProva_fatta(boolean prova_fatta) {
		this.prova_fatta = prova_fatta;
	}
	
	
	
	

}
