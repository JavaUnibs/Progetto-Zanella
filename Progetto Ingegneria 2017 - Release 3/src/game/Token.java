package game;

import java.io.Serializable;

public enum Token implements Serializable{
	
	
	Rame(5), Oro(25), Argento(15), Ferro(10), Bronzo(15), Platino(25), Stagno(5), Acciaio(15), Piombo(15), Alluminio(10);
	



	private int weight;
	private static final long serialVersionUID = 2L;
	
	Token(int weight){
		this.weight=weight;
	}

	public int getWeight() {
		return weight;
	}
}
