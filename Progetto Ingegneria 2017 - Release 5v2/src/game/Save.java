package game;

import java.io.Serializable;
import java.util.HashMap;

import game.Abstract.Ground;
import game.Abstract.Navigation;
import game.Abstract.World;

public class Save implements Serializable{

private static final long serialVersionUID = 2L;
	
	private World mondo;
	private Ground luogo_corrente;
	private HashMap<String, String> local_string;
	private HashMap<String, String[]> values;
	private Navigation navigation;
	
	Save(World mondo, Ground luogo, HashMap<String, String> map, HashMap<String, String[]> map1, Navigation navigation){
		this.mondo=mondo;
		luogo_corrente=luogo;
		local_string=map;
		values=map1;
		this.navigation=navigation;
	}

	public World getMondo() {
		return mondo;
	}

	public Ground getLuogo_corrente() {
		return luogo_corrente;
	}

	public HashMap<String, String> getLocalString(){
		return local_string;
	}
	
	public HashMap<String, String[]> getValues(){
		return values;
	}
	
	public Navigation getNavigation(){
		return navigation;
	}
}
