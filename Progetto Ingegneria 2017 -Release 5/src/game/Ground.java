package game;

import java.io.Serializable;

public class Ground implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int level;
	private int height;
	private int width;
	private boolean start;
	private boolean end;
	private String name;
	private Token key;
	private Trial trial;
	
	Ground(int height, int width, int level, String name){
		this.level=level;
		this.name=name;
		this.height=height;
		this.width=width;
		key=null;
		trial=null;
		start=false;
		end=false;
	}

	public int getLevel() {
		return level;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isStart() {
		return start;
	}

	public boolean isEnd() {
		return end;
	}

	public String getName() {
		return name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setKey(Token key){
		this.key=key;
	}
	
	public Token getKey(){
		return key;
	}

	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	
	
	
	
	


}
