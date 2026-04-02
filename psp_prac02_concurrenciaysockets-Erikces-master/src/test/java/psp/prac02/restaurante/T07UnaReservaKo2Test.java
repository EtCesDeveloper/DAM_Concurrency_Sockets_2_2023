package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static psp.prac02.restaurante.Parametros.PUERTO_PUERTAS;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class T07UnaReservaKo2Test {

	private static final int NUM_SALONES = 1;
	private static final int NUM_PUERTAS = 1;

	@Test
	void ejecucionTest() throws IOException {
		PrintStream originalErr = System.err;
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		System.setErr(new PrintStream(err));
		
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 6700);
		deModa.abrir();
		try (Socket socketPuertas = new Socket("localhost", 6700);
				ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
			String nombre = "Pepe";
			int comensales = 3;
			Reservacion r = new Reservacion(nombre, comensales);
			oos.writeObject(r);
			System.out.println("Reserva enviada");
		} catch (UnknownHostException e) {
			fail();
		} catch (IOException e) {
			fail();
		}

		deModa.cerrar();
		
		String salida = err.toString();
		assertTrue(salida.contains("java.lang.ClassCastException: class psp.prac02.restaurante.Reservacion cannot be cast to class psp.prac02.restaurante.Reserva"));
				
		System.setErr(originalErr);
	}
	
	
	@AfterAll
	static void limpieza() {
		File fL = new File("logs.txt");
		fL.renameTo(new File("logs_T07.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T07.txt"));
		Salida.reset();
	}
}

class Reservacion implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int comensales;

	public Reservacion(String nombre, int comensales) {
		this.nombre = nombre;
		this.comensales = comensales;
	}
}