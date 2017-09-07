package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta World, rappresenta l'abstract product estendibile dalle sue varianti (concrete products).
 */
public abstract class World implements Serializable{

	private static final long serialVersionUID = 2L;

	/**
	 * Classe astratta searchGround
	 * @param h > 0
	 * @param w > 0
	 * @param d > 0
	 * @return oggetto di tipo ground
	 */
	public abstract Ground searchGround(int h, int w, int d);

	public abstract Passage searchPassage(Ground a, Ground b);

	public abstract Ground getStartGround();
}
