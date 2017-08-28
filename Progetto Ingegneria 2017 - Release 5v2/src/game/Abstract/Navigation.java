package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta Navigation, rappresenta l'abstract product estendibile dai vari mondi.
 */
public abstract class Navigation implements Serializable{

	private static final long serialVersionUID = 2L;

	public abstract Ground navigate(Ground luogo_corrente);

}
