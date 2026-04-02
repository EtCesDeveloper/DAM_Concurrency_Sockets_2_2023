package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static psp.prac02.restaurante.Parametros.MIN;
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

class T04DosReservasTest {

	private static final int NUM_SALONES = 1;
	private static final int NUM_PUERTAS = 1;

	private static List<String> cartelList;
	private static String cartel;
	private static List<String> logsList;
	private static String logs;

	@BeforeAll
	static void ejecucion() throws IOException {
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 6400);
		deModa.abrir();
		try (Socket socketPuertas = new Socket("localhost", 6400);
				ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
			String nombre = "Pepe";
			int comensales = 3;
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

		try (Socket socketPuertas = new Socket("localhost", 6400);
				ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
			String nombre2 = "Ana";
			int comensales2 = 5;
			Reserva r2 = new Reserva(nombre2, comensales2);
			oos.writeObject(r2);
			System.out.println("Reserva 2 enviada");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(MIN * 5);
		} catch (InterruptedException ignored) {
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
		try (Scanner s = new Scanner(new File("cartel.txt"))) {
			cartelList = new ArrayList<>();
			cartel = "";

			while (s.hasNextLine()) {
				String nextLine = s.nextLine();
				cartelList.add(nextLine);
				cartel += nextLine + "\n";
			}
		}
	}
	
	@AfterAll
	static void limpieza() {
		File fL = new File("logs.txt");
		fL.renameTo(new File("logs_T04.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T04.txt"));
		Salida.reset();
	}
	
	@Test
	void testSalon() {
		String expectedPepe = "Salon 0: 3/10 [Pepe para 3(";
		boolean containsPepe = cartel.contains(expectedPepe);
		System.out.println(expectedPepe + "? " + containsPepe);
		
		String expectedAna = "Salon 0: 5/10 [Ana para 5(";
		boolean containsAna = cartel.contains(expectedAna);
		System.out.println(expectedPepe + "? " + containsAna);
		
		
		String expected2 = "Salon 0: 8/10";
		boolean contains2 = cartel.contains(expected2);
		System.out.println(expected2 + "? " + contains2);
		
		assertTrue(containsPepe || containsAna);
		assertTrue(contains2);
	}
		
	@Test
	void testPepeAtendiendo() {
		String expected = "Salon 0 atendiendo a Pepe para 3";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testPepeSentandose() {
		String expected = "Pepe para 3 sentandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testPepeLevantandose() {
		String expected = "Pepe para 3 levantandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testAnaAtendiendo() {
		String expected = "Salon 0 atendiendo a Ana para 5";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testAnaSentandose() {
		String expected = "Ana para 5 sentandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testAnaLevantandose() {
		String expected = "Ana para 5 levantandose...";
		boolean contains = logs.contains(expected);
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
	
	@Test
	void testRestauranteCerrado() {
		String expected = "RESTAURANTE CERRADO";
		boolean contains = cartel.endsWith(expected + "\n");
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
}
