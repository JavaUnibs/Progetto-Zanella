package game.Basic;

import java.io.Serializable;

import game.Abstract.ModifyWorld;

public class ModifyBasicWorld extends ModifyWorld implements Serializable{


	private static final long serialVersionUID = 1L;

	public void initialize() {
		System.out.println("Questo mondo non ha parametri modificabili.");
		
	}

}
