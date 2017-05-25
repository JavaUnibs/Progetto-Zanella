package game;

import java.util.HashMap;

public class BasicFactory extends Factory{
	private final String NO_GROUND="E' stata immessa una stringa con un luogo inesistente";
	private final String NO_PASSAGE="E' stata immessa una stringa con un passaggio inesistente";
	private final String INCORRECT_STRING="I passaggi aperti non sono definiti in modo corretto";
	private final String INCORRECT_FILE="Valori mancanti nel file di setup del mondo";
	private HashMap<String, String[]> values;
	private HashMap<String, String> common_string;
	private HashMap<String, String> local_string;
	private BasicWorld mondo;
	
	BasicFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
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
		String nome_luoghi=values.get("NOME_LUOGHI")[0];
		mondo=new BasicWorld(height, width, depth, nome_luoghi);
		mondo.searchGround(start_height, start_width, start_depth).setStart(true);
		mondo.searchGround(end_height, end_width, end_depth).setEnd(true);
		
			if(!openPassages(values.get("PASSAGGI_APERTI"))) return null;
		}catch(NullPointerException e){
			System.out.println(INCORRECT_FILE);
			return null;
		}
		
		
		return mondo;
	}

	
	public Navigation getNavigation() {
		BasicNavigation navigation= new BasicNavigation(mondo, local_string, common_string);
		return navigation;
	}

	
	public ModifyWorld getModify() {
		ModifyBasicWorld modify= new ModifyBasicWorld();
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
				BasicGround g1= mondo.searchGround(h1, w1, d1);
				BasicGround g2= mondo.searchGround(h2, w2, d2);



				if((g1!=null)&&(g2!=null)){
					BasicPassage p= mondo.searchPassage(g1, g2);
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
}
