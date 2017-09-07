package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta Navigation, rappresenta l'abstract product estendibile dalle sue varianti (concrete products).
 */
public abstract class Navigation implements Serializable{

	private static final long serialVersionUID = 2L;

	public abstract Ground navigate(Ground luogo_corrente);

}
