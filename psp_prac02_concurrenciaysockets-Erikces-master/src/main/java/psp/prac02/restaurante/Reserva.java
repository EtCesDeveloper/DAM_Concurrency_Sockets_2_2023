package psp.prac02.restaurante;

import static psp.prac02.restaurante.Parametros.BASE_MESA;
import static psp.prac02.restaurante.Parametros.EXTRA_X_PERS;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Reserva extends Thread implements Serializable{

	private static final long serialVersionUID = 6659359496663270063L;
	private String nombre;
	private int comensales;
	private Salon salon;
	private boolean noPara;
	private int tiempo=BASE_MESA + (comensales *  EXTRA_X_PERS);
	
	public Reserva(String nombre, int comensales) {
		super();
		this.nombre = nombre;
		this.comensales = comensales;
		this.setName("Reserva-" + nombre);
	}

	@Override
	public String toString() {
		return nombre + " para " + comensales + ((tiempo > 0) ? ("(" + tiempo + ")") : "") ;
	}
	@Override
	public void run() {
		//salida.write(levantandose.)
		while(noPara) {
			Reserva reserva = new Reserva(this.nombre,this.comensales);
			try(Socket cliente = new Socket()) {
			OutputStream os = cliente.getOutputStream();
			ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
			salida.writeObject(reserva);
			System.out.println("RESERVA ENVIADA");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	public void vinculaSalon(Salon salon) {
		this.salon=salon;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getComensales() {
		return comensales;
	}

	public void setComensales(int comensales) {
		this.comensales = comensales;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

}
