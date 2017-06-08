package game;

import java.util.ArrayList;
import java.util.Iterator;

public class BasicWorld extends World{
	
	private static final long serialVersionUID = 2L;
	private ArrayList<BasicGround> grounds;
	private ArrayList<BasicPassage> passages; 
	private boolean deposited;
	private boolean trial_done;
	
	
	
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
	
	
	
	
	public BasicGround searchGround(int h, int w, int d){
		for(BasicGround a: grounds){
			if ((a.getHeight()==h) && (a.getWidth()==w) && (a.getLevel()==d)) return a;
		}
		return null;
	}
	
	
	
	
	public BasicPassage searchPassage(Ground a, Ground b){
		for (BasicPassage z: passages){
			if(z.getGrounda().equals(a)&&z.getGroundb().equals(b)) return z;
			if(z.getGrounda().equals(b)&&z.getGroundb().equals(a)) return z;
		}
		return null;
	}
	


	public ArrayList<BasicGround> getGrounds() {
		return grounds;
	}


	public ArrayList<BasicPassage> getPassages() {
		return passages;
	}
	
	public void setGrounds(ArrayList<BasicGround> grounds) {
		this.grounds = grounds;
	}
	
	public void setPassages(ArrayList<BasicPassage> passages) {
		this.passages = passages;
	}

	public boolean isDepositata() {
		return deposited;
	}


	public void setDepositata(boolean depositata) {
		this.deposited = depositata;
	}

	public boolean isProva_fatta() {
		return trial_done;
	}

	public void setProva_fatta(boolean prova_fatta) {
		this.trial_done = prova_fatta;
	}

	public BasicGround getStartGround() {
		for(BasicGround ground: grounds) if(ground.isStart()) return ground;
		return null;
	}


	
}
