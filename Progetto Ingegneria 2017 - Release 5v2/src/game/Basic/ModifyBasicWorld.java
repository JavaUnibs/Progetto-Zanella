package game.Basic;

import java.io.Serializable;

import game.Abstract.ModifyWorld;

/**
 * Classe concreta che estende l'abstract product ModifyWorld. In questo caso non si può modificare il mondo.
 * @author vxrich
 *
 */
public class ModifyBasicWorld extends ModifyWorld implements Serializable{


	private static final long serialVersionUID = 1L;

	/**
	 * Metodo che ritorna l'impossibilità di modificare il mondo di base.
	 */
	public void initialize() {
		System.out.println("Questo mondo non ha parametri modificabili.");
		
	}

}
