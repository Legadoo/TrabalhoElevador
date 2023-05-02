package TrabalhoConsoles;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

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
		this.elevador = new Elevador(ThreadLocalRandom.current().nextInt(0, Andares.size()), this);
		
		
		
	}
	
	private void get_next() {
		HashMap<Passageiro, Integer> line_dict = new HashMap<Passageiro, Integer>();
		
		for(Passageiro passageiro : Andares.get(elevador.current_floor)) {
			if(passageiro.arrived==true) {
				continue;
			}
			int distance = Math.abs(passageiro.dest_floor-passageiro.current_floor);
			line_dict.put(passageiro,distance);
			
		}
		
		List<Map.Entry<Passageiro, Integer> > list =
	           new LinkedList<Map.Entry<Passageiro, Integer> >(line_dict.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Passageiro, Integer> >(){
			public int compare(Map.Entry<Passageiro, Integer> o1,
					Map.Entry<Passageiro, Integer> o2) 
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
		      HashMap<Passageiro, Integer> temp = new LinkedHashMap<Passageiro, Integer>();
            for (Map.Entry<Passageiro, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
		    this.next = temp.entrySet().iterator().next().getKey();
		  elevador.is_running=true;
		  elevador.in_transit = true;
		   next.is_next=true;
		
	}
	
	private int fullest_floor() {
		int ret_val = 0;
		int biggest_floor = 0;
		int counter = 0;
		for (List<Passageiro> andar: this.Andares) {
			if (andar.size() > biggest_floor) {
				biggest_floor = andar.size();
				ret_val=counter;
			}
			counter+=1;
		}
		
		return ret_val;
		
	}
	

	public void run() {
		// TODO Auto-generated method stub
		
		for (Passageiro passageiro : this.passageiros) {
			passageiro.start();
		}
		List<Boolean> chegadas = new ArrayList<Boolean>();
		for (Passageiro passageiro: passageiros) {
			chegadas.add(passageiro.arrived);
		}
		
		if (this.Andares.get(elevador.current_floor).size()==0) {
			elevador.visitar_andar(fullest_floor());
		}
		
		elevador.start();
		get_next();
		
		while (chegadas.contains(false)) {
			if (elevador.in_transit){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				get_next();
			}
			chegadas = new ArrayList<Boolean>();
			for (Passageiro passageiro: passageiros) {
				chegadas.add(passageiro.arrived);
			}
			
		}
		elevador.should_stop=true;
		
	}
	

}
