package game;

import java.util.ArrayList;
import java.util.Iterator;

public class World {
	
	private ArrayList<Ground> grounds;
	private ArrayList<Passage> passages; 
	
	World(int height, int width, int depth){

		grounds= new ArrayList<Ground>();
		passages= new ArrayList<Passage>();
		
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
		
		//for(Ground a: grounds) System.out.println(a.getName());
		//for(Passage a: passages) System.out.println(a.getId());


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
				//System.out.println(g1.getName());
				//System.out.println(g2.getName());


				if((g1!=null)&&(g2!=null)){
					Passage p= searchPassage(g1, g2);
					if (p!=null) {
						p.setOpen(true);
						//System.out.println(p.getId());
					}
					else {
						System.out.println("E' stata immessa una stringa con un passaggio inesistente");
						return false;
					}
				}else {
					System.out.println("E' stata immessa una stringa con un luogo inesistente");
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println("I passaggi aperti non sono definiti in modo corretto");
			return false;
		}
		
		return true;
	}
	
	

}
