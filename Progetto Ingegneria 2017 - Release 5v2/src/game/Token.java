package game;

import java.io.Serializable;

public class Token implements Serializable{
	
	
	private int weight;
	private String name;
	private static final long serialVersionUID = 2L;
	
	public Token(int weight, String name) {
		super();
		this.weight = weight;
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String toString() {
		return name+" ("+weight+") \n ";
	}
	
	
	
	
	
}
