package game;
import java.io.Serializable;
import java.util.HashMap;

public class Save implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private World mondo;
	private Ground luogo_corrente;
	private HashMap<String, String> local_string;
	
	Save(World mondo, Ground luogo, HashMap<String, String> map){
		this.mondo=mondo;
		luogo_corrente=luogo;
		local_string=map;
		
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
	
	

}
