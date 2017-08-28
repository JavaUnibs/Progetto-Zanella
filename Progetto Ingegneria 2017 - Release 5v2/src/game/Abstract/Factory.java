package game.Abstract;

/**
 * Classe astratta Factory, rappresenta l'abstract product estendibile dalle varie modalità di mondo.
 */
public abstract class Factory {
	
	public abstract World getWorld();
	
	public abstract Navigation getNavigation();
	
	public abstract ModifyWorld getModify();

}
