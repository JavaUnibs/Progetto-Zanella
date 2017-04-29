package game;

import java.io.Serializable;

public enum Trial implements Serializable{
	
	
	Storia(30), Scienza(10), Arte(20);
	
	private static final long serialVersionUID = 2L;
	
	private int points;
	
	Trial(int points){
		this.points=points;
	}

	public int getPoints() {
		return points;
	}
	
	
	

	
	
	
	
	

}
