package game;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.ing.myutility.RandomValues;

public class Trial implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private int points;
	private String name;
	private HashMap<String,String> quiz;
	
	Trial(int points, String name){
		this.points=points;
		this.name=name;
		quiz= new HashMap<String,String>();
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public HashMap<String, String> getQuiz() {
		return quiz;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuiz(HashMap<String, String> quiz) {
		this.quiz = quiz;
	}


	String getQuestion(){
		
			int rnd=RandomValues.ranIntLimite(0, quiz.size()-1);
			String[] array = new String[quiz.size()];
			array=quiz.keySet().toArray(array);
			return array[rnd];
			
	}
	
	boolean getAnswer(String domanda, String risposta){
		
		if(quiz.get(domanda).equals(risposta)) return true;
		return false;
	}

	@Override
	public String toString() {
		return "Nome: "+name+" Punti: "+points+"\n";
	}
	
	
	

	
	
	
	
	

}
