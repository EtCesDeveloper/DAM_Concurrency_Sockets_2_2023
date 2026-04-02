package psp.prac02.restaurante;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
	private List<Reserva> reservas;

	public Agenda() {
		reservas = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Agenda [reservas=" + reservas + "]";
	}

	public void addReserva(Reserva r) {
		this.reservas.add(r);
	}
	public void eliminarReserva(Reserva reservaABorrar) {
		//Debo tener cuidado con los remove. Cuando intentas hacer un remove dentro del for que recorre la MISMA estructura
		//te salta un ConcurrentModificationException.
		//Lo suyo es en vez de eliminar, hacerme otra estructura nueva y luego la igualas:
		List<Reserva> reservasNuevas = new ArrayList<Reserva>();
		for(Reserva reservaActual : this.reservas) {
			if(!reservaActual.getNombre().equals(reservaABorrar.getNombre())) {
				reservasNuevas.add(reservaActual);
			}
		}
		this.reservas = reservasNuevas;
		//reservas.remove(0);
	}
	public List<Reserva> getReservas() {
		return reservas;
	}
	
}
