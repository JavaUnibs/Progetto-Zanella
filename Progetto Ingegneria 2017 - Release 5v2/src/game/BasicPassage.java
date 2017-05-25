package game;

public class BasicPassage extends Passage {
	
	private static final long serialVersionUID = 2L;
	private boolean open;
	private Ground grounda;
	private Ground groundb;
	private String id;
	
	BasicPassage(Ground grounda, Ground groundb){
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