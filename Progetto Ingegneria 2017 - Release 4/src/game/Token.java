package game;

import java.io.Serializable;

public enum Token implements Serializable{
	
	
	Rame(35), Oro(45), Argento(40), Ferro(20), Bronzo(30), Platino(50), Stagno(10), Acciaio(25), Piombo(5), Alluminio(15);
	



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
