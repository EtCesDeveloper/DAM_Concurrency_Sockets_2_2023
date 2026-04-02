package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static psp.prac02.restaurante.Parametros.PUERTO_PUERTAS;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class T03UnaReservaGrandeTest {

	private static final int NUM_SALONES = 1;
	private static final int NUM_PUERTAS = 1;

	private static List<String> logsList;
	private static String logs;

	@BeforeAll
	static void ejecucion() throws IOException {
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 6300);
		deModa.abrir();
		try (Socket socketPuertas = new Socket("localhost", 6300);
				ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
			String nombre = "Pepe";
			int comensales = 11;
			Reserva r = new Reserva(nombre, comensales);
			oos.writeObject(r);
			System.out.println("Reserva enviada");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		deModa.cerrar();
		
		System.out.println("Recogiendo ficheros...");
		try (Scanner s = new Scanner(new File("logs.txt"))) {
			logsList = new ArrayList<>();
			logs = "";

			while (s.hasNextLine()) {
				String nextLine = s.nextLine();
				String[] partes = nextLine.split("\t");
				logsList.add(nextLine);
				logs += partes[2] + "\n";
			}
		}
	}
	
	@AfterAll
	static void limpieza() {
		File fL = new File("logs.txt");
		fL.renameTo(new File("logs_T03.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T03.txt"));
		Salida.reset();
	}
	
	@Test
	void testPuerta() {
		String expected = "Puerta 0 recibiendo Pepe para 11";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
		
	@Test
	void testAtendiendo() {
		String expected = "Salon 0 atendiendo a Pepe para 11";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertFalse(contains);
	}
	
	@Test
	void testSentandose() {
		String expected = "Pepe para 11 sentandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertFalse(contains);
	}
	
	@Test
	void testLevantandose() {
		String expected = "Pepe para 11 levantandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertFalse(contains);
	}
}
