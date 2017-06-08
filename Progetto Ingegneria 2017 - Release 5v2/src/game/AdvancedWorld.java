package game;

import java.util.ArrayList;
import java.util.Iterator;

public class AdvancedWorld extends World{
	
	private static final long serialVersionUID = 2L;
	private ArrayList<AdvancedGround> grounds;
	private ArrayList<MediumPassage> passages; 
	private ArrayList<Token> keytypes;
	private ArrayList<Token> playerkeys;
	private ArrayList<Trial> trials;
	private boolean deposited;
	private boolean trial_done;
	private Integer points;
	private int max_transportable_keys_weight;
	private int max_transportable_keys_number;
	private int max_key_weight;
	private int final_score;
	private int max_trial_points;
	
	
	
	AdvancedWorld(int height, int width, int depth, int max_transportable_keys_weight, int max_transportable_keys_number, int max_key_weight, int final_score, int max_trial_points, int points, String _ground_name){

		grounds= new ArrayList<AdvancedGround>();
		passages= new ArrayList<MediumPassage>();
		keytypes= new ArrayList<Token>();
		playerkeys= new ArrayList<Token>();
		trials= new ArrayList<Trial>();
		deposited=true;
		trial_done=false;

		this.max_transportable_keys_weight=max_transportable_keys_weight;
		this.max_transportable_keys_number=max_transportable_keys_number;
		this.max_key_weight=max_key_weight;
		this.final_score=final_score;
		this.max_trial_points=max_trial_points;
		this.points=points;
		
		for(int h=0;h<height;h++){                                             //genera tutti luoghi combinando le max coordinate
			for(int w=0;w<width;w++){
				for(int d=0;d<depth;d++){
					
					grounds.add(new AdvancedGround(h, w, d, _ground_name+" "+h+""+w+""+d));
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
	
	
	
	
	public AdvancedGround searchGround(int h, int w, int d){
		for(AdvancedGround a: grounds){
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
	
	Trial searchTrial(String name){
		for(Trial t:trials){
			if(t.getName().equalsIgnoreCase(name))
			return t;
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
	
	
	
	void updatePoint(Trial trial, boolean correct){
		int points=trial.getPoints();
		if(correct) this.points=this.points+points;
		else this.points=this.points-points;
		if(this.points<0) this.points=0;
	}
	
	int totWeight(){
		int weight=0;
		for(Token key: playerkeys) weight=weight+key.getWeight();
		return weight;
	}




	public ArrayList<AdvancedGround> getGrounds() {
		return grounds;
	}




	public ArrayList<MediumPassage> getPassages() {
		return passages;
	}




	public ArrayList<Trial> getTrials() {
		return trials;
	}




	public boolean isDeposited() {
		return deposited;
	}




	public boolean isTrialDone() {
		return trial_done;
	}




	public Integer getPoints() {
		return points;
	}




	public int getMax_transportable_keys_weight() {
		return max_transportable_keys_weight;
	}




	public int getMax_transportable_keys_number() {
		return max_transportable_keys_number;
	}




	public int getMax_key_weight() {
		return max_key_weight;
	}




	public int getFinal_score() {
		return final_score;
	}




	public int getMax_trial_points() {
		return max_trial_points;
	}




	public void setGrounds(ArrayList<AdvancedGround> grounds) {
		this.grounds = grounds;
	}




	public void setPassages(ArrayList<MediumPassage> passages) {
		this.passages = passages;
	}




	public void setTrials(ArrayList<Trial> trials) {
		this.trials = trials;
	}




	public void setDeposited(boolean deposited) {
		this.deposited = deposited;
	}




	public void setTrial_done(boolean trial_done) {
		this.trial_done = trial_done;
	}




	public void setPoints(Integer points) {
		this.points = points;
	}




	public void setMax_transportable_keys_weight(int max_transportable_keys_weight) {
		this.max_transportable_keys_weight = max_transportable_keys_weight;
	}




	public void setMax_transportable_keys_number(int max_transportable_keys_number) {
		this.max_transportable_keys_number = max_transportable_keys_number;
	}




	public void setMax_key_weight(int max_key_weight) {
		this.max_key_weight = max_key_weight;
	}




	public void setFinal_score(int final_score) {
		this.final_score = final_score;
	}




	public void setMax_trial_points(int max_trial_points) {
		this.max_trial_points = max_trial_points;
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

	public AdvancedGround getStartGround() {
		for(AdvancedGround ground: grounds) if(ground.isStart()) return ground;
		return null;
	}

}
