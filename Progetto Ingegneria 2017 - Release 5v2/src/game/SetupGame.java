package game;

import java.util.HashMap;

public class SetupGame {
	
	
	public static Factory getFactory(HashMap<String, String[]> values, HashMap<String, String> common_string, HashMap<String, String> local_string){
		if((values.get("PASSAGGI_APERTI")!=null)&&(values.get("CHIAVI")!=null)&&(values.get("PROVE")!=null)) return new AdvancedFactory(values, common_string, local_string);
		else if((values.get("PASSAGGI_APERTI")!=null)&&(values.get("CHIAVI")!=null)) return new MediumFactory(values, common_string, local_string);
		else if(values.get("PASSAGGI_APERTI")!=null) return new BasicFactory(values, common_string, local_string);
		else System.out.println("Tipo di mondo sconosciuto");
		return null;
	}

}
