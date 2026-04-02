package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class T10MuchasReservasOrdenTest {

	private static final int NUM_SALONES = 3;
	private static final int NUM_PUERTAS = 2;
	
	private static final String NOMBRES = "abcdefghij";
	private static final int COMENSALES = 3;

	private static List<String> cartelList;
	private static String cartel;
	private static List<String> logsList;

	@BeforeAll
	static void ejecucion() throws IOException {
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 7000);
		deModa.abrir();
		
		for (char c : NOMBRES.toCharArray()) {
			try (Socket socketPuertas = new Socket("localhost", 7000);
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
			Thread.sleep(MIN * 20);
		} catch (InterruptedException ignored) {
		}

		deModa.cerrar();
		
		System.out.println("Recogiendo ficheros...");
		try (Scanner s = new Scanner(new File("logs.txt"))) {
			logsList = new ArrayList<>();

			while (s.hasNextLine()) {
				String nextLine = s.nextLine();
				logsList.add(nextLine);
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
		fL.renameTo(new File("logs_T10.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T10.txt"));
		Salida.reset();
	}

	@Test
	void testOrden() {
		for (char c : NOMBRES.toCharArray()) {
			String comensal = c + " para " + COMENSALES;
			List<String> logsComensal = new ArrayList<>();
			
			for (String log : logsList) {
				if (log.contains(comensal)) {
					logsComensal.add(log);
				}
			}
			System.out.println(logsComensal);
			assertEquals(5, logsComensal.size());
			assertTrue(logsComensal.get(0).contains("recibiendo"));
			assertTrue(logsComensal.get(1).contains("atendiendo"));
			assertTrue(logsComensal.get(2).contains("sentandose"));
			assertTrue(logsComensal.get(3).contains("levantandose"));
			assertTrue(logsComensal.get(4).contains("despidiendo"));
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
