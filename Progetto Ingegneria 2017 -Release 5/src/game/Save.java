package game;
import java.io.Serializable;

public class Save implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private World mondo;
	private Ground luogo_corrente;
	
	Save(World mondo, Ground luogo){
		this.mondo=mondo;
		luogo_corrente=luogo;
		
	}

	public World getMondo() {
		return mondo;
	}

	public Ground getLuogo_corrente() {
		return luogo_corrente;
	}

	
	

}
