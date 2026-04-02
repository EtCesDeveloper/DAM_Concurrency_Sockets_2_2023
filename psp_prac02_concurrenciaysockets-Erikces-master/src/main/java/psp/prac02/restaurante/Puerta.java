package psp.prac02.restaurante;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import psp.prac02.restaurante.Restaurante;

public class Puerta extends Thread {

	private Restaurante restaurante;
	private int id;
	private int basePuerto;
	private ServerSocket servidor;
	private boolean hiloTermina;
	
	public Puerta(Restaurante restaurante, int id, int basePuerto) {
		super("Puerta-" + id);
		this.restaurante = restaurante;
		this.id = id;
		this.basePuerto = basePuerto;
		this.hiloTermina = false;
	}
	
	@Override
    public void run() {      
        try {
            System.out.println("Puerta escuchando en " + this.basePuerto);
            this.servidor = new ServerSocket(this.basePuerto);
           while (!hiloTermina) {
               
                Socket cliente = servidor.accept();
                toString();
                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                Reserva reserva = (Reserva)in.readObject();System.out.println("Tengo la reserva");
                Salida.escribe(LocalDate.now()+"\t"+"Puerta "+ id+"\t"+"Puerta "+ id+" recibiendo "+ reserva.getNombre()+ " para "+reserva.getComensales());
                Salida.escribe(this.toString()+" recibiendo "+ reserva.getNombre()+ " para "+reserva.getComensales()+"\n");
                
                Salida.escribe("Puerta "+ id+" recibiendo "+ reserva.getNombre()+ " para "+reserva.getComensales());
                System.out.println("Puerta "+ id+" recibiendo "+ reserva.getNombre()+ " para "+reserva.getComensales());
                this.restaurante.addReserva(reserva);
                if(reserva!=null) {
                	synchronized(restaurante.getAgenda()) {
                    	this.restaurante.getAgenda().notifyAll();

                	}
                }
                System.out.println("RESERVA ANIADIDA A LA AGENDA");
                //cliente.close();
                this.restaurante.getAgenda().addReserva(reserva);
           }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION EN CLASE PUERTA" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound EXCEPTION EN CLASE PUERTA");
        }   
        
    }
	@Override
	public String toString() {
		return "Puerta " + id;
	}

	public void setTermina(boolean hiloTermina) {
	    this.hiloTermina = hiloTermina;
	}
}
