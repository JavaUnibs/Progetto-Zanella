package game;

public abstract class Factory {
	
	public abstract World getWorld();
	
	public abstract Navigation getNavigation();
	
	public abstract ModifyWorld getModify();

}
