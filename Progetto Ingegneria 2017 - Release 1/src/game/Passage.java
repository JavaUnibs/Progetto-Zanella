package game;

public class Passage {
	
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	
	Passage(Ground grounda, Ground groundb){
		this.grounda=grounda;
		this.groundb=groundb;
		id=""+grounda.getHeight()+""+grounda.getWidth()+""+grounda.getLevel()+"-"+groundb.getHeight()+""+groundb.getWidth()+""+groundb.getLevel();
		open=false;
		
	}

	public boolean isOpen() {
		return open;
	}

	public Ground getGrounda() {
		return grounda;
	}

	public Ground getGroundb() {
		return groundb;
	}

	public String getId() {
		return id;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	

}
