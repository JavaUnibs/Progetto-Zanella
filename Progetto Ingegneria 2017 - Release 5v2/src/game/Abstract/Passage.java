package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta Passage, rappresenta l'abstract product estendibile dalle sue varianti (concrete products).
 */
public abstract class Passage implements Serializable{

	private static final long serialVersionUID = 2L;
	
	public abstract boolean isOpen();

	public abstract Ground getGrounda();

	public abstract Ground getGroundb();

	public abstract String getId();

	public abstract void setOpen(boolean open);
	


}
