package game;
import java.io.Serializable;

public class Save implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private World mondo;
	private Ground luogo_corrente;
	private int peso_attuale;
	private int numero_chiavi_attuale;
	
	Save(World mondo, Ground luogo, int peso, int numero){
		this.mondo = mondo;
		luogo_corrente = luogo;
		this.peso_attuale = peso;
		this.numero_chiavi_attuale = numero;
	}

	public World getMondo() {
		return mondo;
	}

	public Ground getLuogo_corrente() {
		return luogo_corrente;
	}
	
	public int getPeso_corrente() {
		return peso_attuale;
	}
	
	public int getNumero_attuale() {
		return numero_chiavi_attuale;
	}

	
	

}
