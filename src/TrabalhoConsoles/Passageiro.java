package TrabalhoConsoles;

import java.security.PublicKey;


public class Passageiro extends Thread {

	public boolean is_next;
	public int dest_floor;
	public int current_floor;
	
	public Predio predio;
	public Elevador elevador;
	
	public boolean arrived;
	public boolean in_elevator;
	
	public Passageiro(int dest_floor, int star_floor, Predio predio) {
		this.is_next = false;
		this.dest_floor = dest_floor;
		this.current_floor = star_floor;
		this.predio = predio;
		this.elevador = predio.elevador;
		
	}
	
	public void init() {
		
		this.start();
		this.arrived=false;
	}
	
	public void fin() {
		this.arrived = true;
	}
	
	@Override
	public void run() {
		super.run();
		
		while(!arrived) {
			esperando_elevador();
			chamar_elevador();
			entrar_em_elevador();
			elevador.visitar_andar(dest_floor);
			sair_de_elevador();
			sair_da_fila();
			fin();
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			elevador.in_transit=false;
		}
		
	}
	
	public void esperando_elevador() {
		while (this.is_next==false || elevador.is_running==false){
				try {
					Thread.sleep(2000);
					System.out.println(String.valueOf(this.threadId())+" is waiting");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void chamar_elevador() {
		try {
			elevador.sinal.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elevador.visitar_andar(this.current_floor);
		
	}
	
	public void entrar_em_elevador() {
		elevador.receber_passageiro(this);
	}
	
	public void sair_de_elevador() {
		elevador.soltar_passageiro(this);
		elevador.sinal.release();
	}
	
	public void sair_da_fila() {
		this.predio.next=null;
	}
	
	
}
	
	
	

