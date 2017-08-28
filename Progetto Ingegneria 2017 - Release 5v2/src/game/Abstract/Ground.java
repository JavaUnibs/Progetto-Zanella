package game.Abstract;

import java.io.Serializable;

/**
 * Classe astratta Ground, rappresenta i luoghi di cui è costituito il mondo ed è l'abstract product esteso alle varianti di Ground .
 * @author vxrich
 *
 */
public abstract class Ground implements Serializable{
	

	private static final long serialVersionUID = 2L;

	public abstract int getLevel();
	
	public abstract int getHeight();

	public abstract int getWidth();

	public abstract boolean isStart();

	public abstract boolean isEnd();
	
	public abstract String getName();

	public abstract void setLevel(int level);
	
	public abstract void setHeight(int height);
	
	public abstract void setWidth(int width);
	
	public abstract void setStart(boolean start);

	public abstract void setEnd(boolean end);

	public abstract void setName(String name);
	

}
