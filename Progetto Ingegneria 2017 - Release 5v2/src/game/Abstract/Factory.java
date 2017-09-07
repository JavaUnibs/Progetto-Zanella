package game.Abstract;

/**
 * Classe astratta Factory, rappresenta l'abstract factory estendibile dalle varie tipologie di concrete factory.
 */
public abstract class Factory {
	
	public abstract World getWorld();
	
	public abstract Navigation getNavigation();
	
	public abstract ModifyWorld getModify();

}
