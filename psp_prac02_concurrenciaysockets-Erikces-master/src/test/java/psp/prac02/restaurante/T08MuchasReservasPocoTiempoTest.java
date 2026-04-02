package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

class T08MuchasReservasPocoTiempoTest {

	private static final int NUM_SALONES = 1;
	private static final int NUM_PUERTAS = 10;
	
	private static final String SI = "abc";
	private static final String NO = "def";
	private static final String NOMBRES = SI + NO;
	private static final int COMENSALES = 3;

	private static List<String> cartelList;
	private static String cartel;
	private static List<String> logsList;
	private static String logs;

	@BeforeAll
	static void ejecucion() throws IOException {
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 6800);
		deModa.abrir();
		
		for (char c : NOMBRES.toCharArray()) {
			try (Socket socketPuertas = new Socket("localhost", 6800);
					ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
				Reserva r = new Reserva(c + "", COMENSALES);
				oos.writeObject(r);
				System.out.println("Reserva enviada " + c);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(MIN * 10);
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
		fL.renameTo(new File("logs_T08.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T08.txt"));
		Salida.reset();
	}

	@Test
	void testAtendiendo() {
		for (char c : SI.toCharArray()) {
			String expected = "atendiendo a " + c + " para " + COMENSALES;
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
		for (char c : NO.toCharArray()) {
			String expected = "atendiendo a " + c + " para " + COMENSALES;
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertFalse(contains);
		}
	}
	
	@Test
	void testSentandose() {
		for (char c : SI.toCharArray()) {
			String expected = c + " para " + COMENSALES + " sentandose...";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
		for (char c : NO.toCharArray()) {
			String expected = c + " para " + COMENSALES + " sentandose...";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertFalse(contains);
		}
	}
	
	@Test
	void testLevantandose() {
		for (char c : SI.toCharArray()) {
			String expected = c + " para " + COMENSALES + " levantandose...";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
		for (char c : NO.toCharArray()) {
			String expected = c + " para " + COMENSALES + " levantandose...";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertFalse(contains);
		}
	}

	@Test
	void testRestauranteCerrado() {
		String expected = "RESTAURANTE CERRADO";
		boolean contains = cartel.endsWith(expected + "\n");
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
}
