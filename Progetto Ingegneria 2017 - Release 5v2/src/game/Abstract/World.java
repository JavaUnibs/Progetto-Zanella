package game.Abstract;

import java.io.Serializable;

public abstract class World implements Serializable{

	private static final long serialVersionUID = 2L;

	public abstract Ground searchGround(int h, int w, int d);

	public abstract Passage searchPassage(Ground a, Ground b);

	public abstract Ground getStartGround();
}
