package game.Abstract;

/**
 * Classe astratta Factory
 */
public abstract class Factory {
	
	public abstract World getWorld();
	
	public abstract Navigation getNavigation();
	
	public abstract ModifyWorld getModify();

}
