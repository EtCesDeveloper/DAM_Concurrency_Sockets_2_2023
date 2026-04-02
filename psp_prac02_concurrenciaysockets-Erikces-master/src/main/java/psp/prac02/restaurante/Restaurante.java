package psp.prac02.restaurante;

import static psp.prac02.restaurante.Parametros.EXTRA_SALON;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {

	private List<Salon> salones;
	private List<Puerta> puertas;
	private Agenda agenda;
	private Cartel cartel;
	private int capacidad = 10;

	public Restaurante(int numSalones, int numPuertas, int basePuerto) throws FileNotFoundException {
	    this.agenda= new Agenda();
        this.cartel = new Cartel(this);
        this.salones= new ArrayList<Salon>();
        this.puertas = new ArrayList<Puerta>();
        for (int i=0 ; i < numSalones; i++) {
            salones.add(new Salon(this, i,capacidad+EXTRA_SALON*i));
        }
        for(int x=0 ; x < numPuertas;x++ ) {
            puertas.add(new Puerta(this, x, basePuerto+x));
        }
	}

	public void abrir() {
	     Cartel.imprime("Abriendo puertas...");
        for (int x = 0; x < puertas.size(); x++) {
            puertas.get(x).start();
            Salida.escribe(LocalTime.now()+"\t\t" + "Puerta " + x + " abierta"+"\n");
        }
        Cartel.imprime(LocalTime.now() + "\t\t" + "Puertas Abiertas" + "\n");
        Cartel.imprime("Abriendo salones...");
	    for (int i = 0; i < salones.size(); i++) {
            salones.get(i).start();
        }
	    Cartel.imprime("Salones Abiertos");
       
        cartel.start();
	}

	public void cerrar() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    Cartel.imprime("Cerrando puertas...");
        for (int i = 0; i < puertas.size(); i++) {
            puertas.get(i).setTermina(true);
            Salida.escribe(LocalTime.now() + "\t\t" + "Puerta " + i + " cerrada" +"\n");
        }
        puertas.removeAll(puertas);
        Cartel.imprime("Puertas cerradas");
        Cartel.imprime("Cerrando salones...");
        for (int i = 0; i < salones.size(); i++) {
            salones.get(i).setTermina();
        }
        salones.removeAll(salones);
        Cartel.imprime("Salones cerrados");
        Cartel.imprime("RESTAURANTE CERRADO");
        
        Salida.cerrar();
	}
	public void addReserva(Reserva reserva) {
        for(Salon salonActual : this.salones) {
            //Ver si la reserva que queremos ubicar CABE en mi salon!!
            if(salonActual.getOcupacion() + reserva.getComensales() <= salonActual.getCapacidad()) {
            	//this.agenda.addReserva(reserva);
            	synchronized(this.agenda)
                {
                   this.agenda.addReserva(reserva);
                   this.agenda.notify();
                }
            	salonActual.addReserva(reserva);
            	reserva.vinculaSalon(salonActual);
                this.agenda.eliminarReserva(reserva);
                //salonActual.despierta();
                System.out.println("LLEGA HASTA AQUI");
                break; //Con esto salimos del bucle y no seguimos con los siguientes salones
            }
        }
    }

    public List<Salon> getSalones() {
        return salones;
    }

    public List<Puerta> getPuertas() {
        return puertas;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public Cartel getCartel() {
        return cartel;
    }

    public int getCapacidad() {
        return capacidad;
    }
	
}
