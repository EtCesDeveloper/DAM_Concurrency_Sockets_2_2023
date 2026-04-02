package psp.prac02.restaurante;

import java.util.ArrayList;
import java.util.List;

public class Salon extends Thread {

	private Restaurante restaurante;
	private int id;
	private int capacidad;
	private int ocupacion;
	private List<Reserva> comensales;
	private boolean hiloTermina;

	public Salon(Restaurante restaurante, int id, int capacidad) {
		super("Salon-" + id);
		this.restaurante = restaurante;
		this.id = id;
		this.capacidad = capacidad;
		this.ocupacion = 0;
		this.comensales = new ArrayList<>();
	}
	public synchronized void run(){System.out.println("--------------------HE ENTRADO AL RUN DE SALON-------------------");
	while(!hiloTermina) {
		synchronized (this.restaurante.getAgenda()) {
			  try {
				 System.out.println(this + "HILO");
				restaurante.getAgenda().wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		  }
			while(comensales.size()!=0) {
				Reserva reservaActual = comensales.get(0);
				try {
						Cartel.imprime("Salon " + id + ": " + ocupacion + "/" + capacidad + " " +comensales);
						Salida.escribe("\t\t"+"Salon " + id + ": " + ocupacion + "/" + capacidad + " " +comensales+"\n");
						Salida.escribe("\t\t"+"Salon "+id+" atendiendo a "+reservaActual+"\n");
						Salida.escribe("\t\t"+reservaActual.getNombre()+" para "+reservaActual.getComensales()+" sentandose..."+"\n");
						Salida.escribe("\t\t"+reservaActual.getNombre()+" para "+reservaActual.getComensales()+" levantandose..."+"\n");
					Thread.sleep(reservaActual.getTiempo());
					//ocupacion=ocupacion-this.restaurante.getAgenda().getReservas().get(i).getComensales();//Libero el espacio en el restaurante
				//Salida.escribe(reservaActual + " levantandose...");System.out.println(reservaActual + " levantandose...");
				//this.restaurante.getAgenda().eliminarReserva(this.restaurante.getAgenda().getReservas().get(0));//Elimino la reserva de la agenda
				comensales.remove(0);
				System.out.println(restaurante.getAgenda().getReservas());System.out.println(comensales);
				} catch (InterruptedException e) {
					
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public void setTermina() {
        this.hiloTermina = true;
    }
	@Override
	public String toString() {
        return "Salon " + id + ": " + ocupacion + "/" + capacidad + " " +comensales;
    }
	public void addReserva(Reserva reserva) {
        this.comensales.add(reserva);
        this.ocupacion+=reserva.getComensales();
    }
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public List<Reserva> getComensales() {
        return comensales;
    }

    public boolean isHiloTermina() {
        return hiloTermina;
    }
}