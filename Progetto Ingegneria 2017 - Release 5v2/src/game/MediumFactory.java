package game;

import java.util.HashMap;

public class MediumFactory extends Factory{
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_STRING2="Le chiavi da mettere nei luoghi non sono definite in modo corretto";
	private final String NO_KEY="E' stata immessa una stringa con una chiave inesistente";
	private final String INCORRECT_FILE="Valori mancanti nel file di setup del mondo";
	
	private HashMap<String, String[]> values;
	private HashMap<String, String> common_string;
	private HashMap<String, String> local_string;
	private MediumWorld mondo;
	
	MediumFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		this.values=values;
		this.local_string=local_string;
		this.common_string=common_string;
	}

	
	public World getWorld() {
		try{
		int height= Integer.parseInt(values.get("ALTEZZA")[0]);
		int width= Integer.parseInt(values.get("LARGHEZZA")[0]);
		int depth= Integer.parseInt(values.get("PROFONDITA")[0]);
		int start_height=Integer.parseInt(values.get("START_H")[0]);
		int start_width=Integer.parseInt(values.get("START_W")[0]);
		int start_depth=Integer.parseInt(values.get("START_D")[0]);
		int end_height=Integer.parseInt(values.get("END_H")[0]);
		int end_width=Integer.parseInt(values.get("END_W")[0]);
		int end_depth=Integer.parseInt(values.get("END_D")[0]);
		int peso_max_trasportabile=Integer.parseInt(values.get("PESO_MAX_TRAS")[0]);
		int numero_max_trasportabile=Integer.parseInt(values.get("NUM_MAX_TRAS")[0]);
		int peso_max_chiave=Integer.parseInt(values.get("PESO_MAX_CHIAVE")[0]);
		String nome_luoghi=values.get("NOME_LUOGHI")[0];
		mondo=new MediumWorld(height, width, depth, peso_max_trasportabile, numero_max_trasportabile, peso_max_chiave, nome_luoghi);
		mondo.searchGround(start_height, start_width, start_depth).setStart(true);
		mondo.searchGround(end_height, end_width, end_depth).setEnd(true);
		
			if(!openPassages(values.get("PASSAGGI_APERTI"))) return null;
			addKeys(values.get("CHIAVI"));
			if(!putKeyGrounds(values.get("LUOGHI_CHIAVE"))) return null;
			if(!putKeyPassages(values.get("PASSAGGI_CHIAVE"))) return null;
		}catch(NullPointerException e){
			System.out.println(INCORRECT_FILE);
			return null;
		}
		return mondo;
		
	}

	
	public Navigation getNavigation() {
		MediumNavigation navigation= new MediumNavigation(mondo, local_string, common_string);
		return navigation;
	}

	
	public ModifyWorld getModify() {
		ModifyMediumWorld modify= new ModifyMediumWorld(mondo);
		return modify;
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
				MediumGround g1= mondo.searchGround(h1, w1, d1);
				MediumGround g2= mondo.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					MediumPassage p= mondo.searchPassage(g1, g2);
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
	
	boolean putKeyGrounds(String[] array){                                  //Aggiunge ai luoghi selezionati le chiavi definite nell'array (sintassi luogo-chiave)
		
		try{
			for(int i=0;i<array.length;i++){
				int h=Integer.parseInt(array[i].substring(0, 1));
				int w=Integer.parseInt(array[i].substring(1, 2));
				int d=Integer.parseInt(array[i].substring(2, 3));
				String temp=array[i].substring(4);
				MediumGround gtemp;
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
				MediumGround g1= mondo.searchGround(h1, w1, d1);
				MediumGround g2= mondo.searchGround(h2, w2, d2);
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
					MediumPassage p= mondo.searchPassage(g1, g2);
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
				System.out.println("Questa chiave esiste già");
				exists=true;
				break;
			}
				
			}
			if(!exists) mondo.getKeytypes().add(new Token(value, name));
		}
		
		
	}
	
}
