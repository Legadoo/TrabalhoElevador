package TrabalhoConsoles;
import java.util.ArrayList;
import java.util.List;

public class Predio {
	
	public final int num_andares;
	public List<List<Passageiro>> Andares = new ArrayList<List<Passageiro>>();
	public final Elevador elevador;
	public List<Passageiro> passageiros = new ArrayList<Passageiro>();
	public Passageiro next;
	
	public Predio(int num_andares) {
		this.num_andares=num_andares;
		
		for(int i=0; i<this.num_andares;i++) {
			List<Passageiro> andar = new ArrayList<Passageiro>();
			this.Andares.add(andar);
		}
		this.elevador = new Elevador();
		
		
		
	}
	

}
