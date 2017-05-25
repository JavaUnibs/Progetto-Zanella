package game;

import java.io.Serializable;

public abstract class Navigation implements Serializable{

	private static final long serialVersionUID = 2L;

	public abstract Ground navigate(Ground luogo_corrente);

}
