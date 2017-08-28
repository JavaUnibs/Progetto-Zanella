package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta ModifyWorld, rappresenta l'abstract product estendibile dai vari mondi.
 *
 */
public abstract class ModifyWorld implements Serializable{


	private static final long serialVersionUID = 1L;

	public abstract void initialize();
}
