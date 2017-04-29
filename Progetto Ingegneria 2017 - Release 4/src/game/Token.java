package game;

import java.io.Serializable;

public enum Token implements Serializable{
	
	
	Rame(5), Oro(30), Argento(40), Ferro(20), Bronzo(10), Platino(20), Stagno(10), Acciaio(25), Piombo(30), Alluminio(20);
	



	private int weight;
	private static final long serialVersionUID = 2L;
	
	Token(int weight){
		this.weight=weight;
	}

	public int getWeight() {
		return weight;
	}
	
	public String toString(){
		return this.name() + "(" + this.weight + ")";
	}
}
