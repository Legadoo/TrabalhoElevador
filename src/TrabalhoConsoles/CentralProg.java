package TrabalhoConsoles;

import java.util.*;


public class CentralProg {
	public static Predio predio;

	public CentralProg(int N, int andares) {
		
        int num_andares = andares;
		
		predio = new Predio(num_andares);
	
	
	
	Random rand = new Random();
	
	
	for (int i = 0; i < N;i++) {
		int curr_floor = rand.nextInt(num_andares);
		int dest_floor = 0;
		
		do{
			dest_floor = rand.nextInt(num_andares);	
		}while(dest_floor==curr_floor);
		
		Passageiro passageiro = new Passageiro(curr_floor, dest_floor, predio);
		predio.Andares.get(curr_floor).add(passageiro);
		predio.passageiros.add(passageiro);
	
	
	}
	predio.run();

	

}



}
