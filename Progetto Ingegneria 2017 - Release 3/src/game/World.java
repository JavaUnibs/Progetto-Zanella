package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class World implements Serializable{

	private static final long serialVersionUID = 2L;
	private final String NO_GROUND="E' stata immessa una stringa con una chiave inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private ArrayList<Ground> grounds;
	private ArrayList<Passage> passages; 
	private ArrayList<Token> keytypes;
	private ArrayList<Token> playerkeys;
	private boolean depositata;
	
	
	
	World(int height, int width, int depth){

		grounds= new ArrayList<Ground>();
		passages= new ArrayList<Passage>();
		keytypes= new ArrayList<Token>();
		playerkeys= new ArrayList<Token>();
		depositata=true;
		
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
	

	public boolean isDepositata() {
		return depositata;
	}




	public void setDepositata(boolean depositata) {
		this.depositata = depositata;
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
