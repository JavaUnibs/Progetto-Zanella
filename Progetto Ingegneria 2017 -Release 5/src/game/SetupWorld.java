package game;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.myutility.RandomValues;

public class SetupWorld {
	
	
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String INCORRECT_STRING3="Le prove da mettere nei luoghi non sono definite in modo corretto";
	private World mondo;
	
	
	SetupWorld(World mondo){
		this.mondo=mondo;
		
	}
	
	
	boolean openPassages(String[] array){                     //apre i passaggi voluti partendo da un array di stringhe 

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				Ground g1= mondo.searchGround(h1, w1, d1);
				Ground g2= mondo.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					Passage p= mondo.searchPassage(g1, g2);
					if (p!=null) {
						p.setOpen(true);
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING);
			return false;
		}
		
		return true;
	}
	
	
	boolean putTrialsGrounds(String[] array){                                       //Decide in modo random una prova da mettere nei luoghi definiti dall'array 
		try{
		for(int i=0;i<array.length;i++){
			int h1=Integer.parseInt(array[i].substring(0, 1));
			int w1=Integer.parseInt(array[i].substring(1, 2));
			int d1=Integer.parseInt(array[i].substring(2, 3));
			Ground g1= mondo.searchGround(h1, w1, d1);
			
			if (g1!=null){
				int rnd = RandomValues.ranIntLimite(0, mondo.getTrials().size()-1);
				g1.setTrial(mondo.getTrials().get(rnd));
			}else {
				System.out.println(NO_GROUND);
				return false;
			}
		}
		}
		catch(IllegalArgumentException e){
			System.out.println(INCORRECT_STRING3);
			return false;
		}
		return true;
	}
	
	void addTrials(String[] array){                                           //Aggiunge al mondo le prove definite dall'array (sintassi "punti-prova")
		for(int i=0;i<array.length;i++){


			String temp=array[i].substring(0, array[i].indexOf("-"));
			int points=Integer.parseInt(temp);
			String temp2=array[i].substring(temp.indexOf("-")+1);
			
			boolean exists=false;
			for(Trial a:mondo.getTrials()) {
				if (a.getName().equalsIgnoreCase(temp2)) {
					System.out.println("Questa prova esiste giï¿½");
					exists=true;
					break;
				}
				
				
			}
			if(!exists) mondo.getTrials().add(new Trial(points, temp2));
		
		}

	}
	
	void addQA(String[] qa){ //Aggiunge alla prova scelta (primo slot array)  le domande e risposte definite dall'array (sintassi domanda-risposta)
		String name=qa[0];
		for(Trial a: mondo.getTrials()){
			if (a.getName().equalsIgnoreCase(name)){
				for(int i=1;i<qa.length;i++){
					a.getQuiz().put(qa[i].substring(0, qa[i].indexOf("-")), qa[i].substring(qa[i].indexOf("-")+1));
				}
			}
		}
	}
	
	boolean putKeyGrounds(String[] array){                                  //Aggiunge ai luoghi selezionati le chiavi definite nell'array (sintassi luogo-chiave)
		
		try{
			for(int i=0;i<array.length;i++){
				int h=Integer.parseInt(array[i].substring(0, 1));
				int w=Integer.parseInt(array[i].substring(1, 2));
				int d=Integer.parseInt(array[i].substring(2, 3));
				String temp=array[i].substring(4);
				Ground gtemp;
				Token key=null;
				
				boolean exists=false;
				for(Token a:mondo.getKeytypes()) {
					if (a.getName().equalsIgnoreCase(temp)) {
						key=a;
						exists=true;
						break;
					}
					
				}
				
				if(!exists) {
					System.out.println("Questa chiave non esiste");
					return false;
				}
				
				
					if((gtemp=mondo.searchGround(h, w, d))!=null){
						gtemp.setKey(key);
					} else{
						
						System.out.println(NO_GROUND);
						return false;
				}	
			}
			
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	
	
	boolean putKeyPassages(String[] array){                               //Aggiunge ai passaggi le chiavi definite nell'array (sintassi: luogo-luogo-chiave)

		try{
			
			for(int i=0;i<array.length;i++){

				int h1=Integer.parseInt(array[i].substring(0, 1));
				int w1=Integer.parseInt(array[i].substring(1, 2));
				int d1=Integer.parseInt(array[i].substring(2, 3));
				int h2=Integer.parseInt(array[i].substring(4, 5));
				int w2=Integer.parseInt(array[i].substring(5, 6));
				int d2=Integer.parseInt(array[i].substring(6, 7));
				String temp=array[i].substring(8);
				Ground g1= mondo.searchGround(h1, w1, d1);
				Ground g2= mondo.searchGround(h2, w2, d2);
				Token key=null;
				
				
				boolean exists=false;
				for(Token a:mondo.getKeytypes()) {
					if (a.getName().equalsIgnoreCase(temp)) {
						key=a;
						exists=true;
						break;
					}
					
				}
				
				if(!exists) {
					System.out.println("Questa chiave non esiste");
					return false;
				}


				if((g1!=null)&&(g2!=null)){
					Passage p= mondo.searchPassage(g1, g2);
					if (p!=null) {
						if(key!=null){
							p.setKey(key);
						}else {
							System.out.println(NO_KEY);
							return false;
						}
					}
					else {
						System.out.println(NO_PASSAGE);
						return false;
					}
				}else {
					System.out.println(NO_GROUND);
					return false;
				}
				
			}
		
		}catch(StringIndexOutOfBoundsException e){
			System.out.println(INCORRECT_STRING2);
			return false;
		}
		
		return true;
	}
	
	void addKeys(String[] keys){                                                  //Aggiunge al mondo le chiavi definite nell'array (sintassi valore-nome)
		
		for(String s: keys){
			int value= Integer.parseInt(s.substring(0, s.indexOf("-")));
			String name= s.substring(s.indexOf("-")+1);
			
			boolean exists=false;
			for(Token b: mondo.getKeytypes()){ 
				if(b.getName().equalsIgnoreCase(name)) {
				System.out.println("Questa chiave esiste giï¿½");
				exists=true;
				break;
			}
				
			}
			if(!exists) mondo.getKeytypes().add(new Token(value, name));
		}
		
		
	}

	
	HashMap<Ground, ArrayList<Passage>> keepTrackKeys(){                              //Tiene traccia in un hashmap dei luoghi e dei passaggi con chiavi coincidenti per mantenere la raggiungibilitï¿½ del goal
		HashMap<Ground, ArrayList<Passage>> map = new HashMap<Ground, ArrayList<Passage>>();
		for(Ground g: mondo.getGrounds()){
			ArrayList<Passage> passages= new ArrayList<Passage>();
			Token key=g.getKey();
			if(key!=null){
				for(Passage p:mondo.getPassages())
					if(p.getKey()==key) passages.add(p);
					map.put(g, passages);
			}
			
		}
		
		return map;
		
	}
	
	ArrayList<Ground> keepTrackTrials(){													//tiene traccia dei luoghi in cui c'è una prova
		ArrayList<Ground> grounds=new ArrayList<Ground>();
		for (Ground g: mondo.getGrounds()) if(g.getTrial()!=null) grounds.add(g);
		return grounds;
	}
	
	ArrayList<Token> invalidKeysWeight(int max_weight){                    //Restituisce un arraylist contenente le chiavi che non rispettano il vincolo di peso massimo
		ArrayList<Token> temp= new ArrayList<Token>();
		for(Token a: mondo.getKeytypes())
			if(a.getWeight()>=max_weight) temp.add(a);
		return temp;
		
	}
	
	ArrayList<Trial> invalidTrialPoints(int max_points){                   //Restituisce un arraylist contenente le chiavi che non rispettano il vincolo di peso massimo
		ArrayList<Trial> temp= new ArrayList<Trial>();
		for(Trial a: mondo.getTrials())
			if(a.getPoints()>=max_points) temp.add(a);
		return temp;

	
	}


	public World getMondo() {
		return mondo;
	}


	public void setMondo(World mondo) {
		this.mondo = mondo;
	}
	

	
	

}
