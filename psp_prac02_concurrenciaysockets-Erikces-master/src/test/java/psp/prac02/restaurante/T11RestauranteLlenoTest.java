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

class T11RestauranteLlenoTest {

	private static final int NUM_SALONES = 2;
	private static final int NUM_PUERTAS = 2;
	
	private static final String NOMBRES = "abcde";
	private static final int COMENSALES = 5;

	private static List<String> cartelList;
	private static String cartel;
	private static List<String> logsList;

	@BeforeAll
	static void ejecucion() throws IOException {
		Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 7100);
		deModa.abrir();
		
		for (char c : NOMBRES.toCharArray()) {
			try (Socket socketPuertas = new Socket("localhost", 7100);
					ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
				Reserva r = new Reserva(c + "", COMENSALES);
				oos.writeObject(r);
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
		fL.renameTo(new File("logs_T11.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T11.txt"));
		Salida.reset();
	}

	@Test
	void testLleno() {
		boolean llenoEncontrado = false;
		for (int i = 0; i < cartelList.size() && !llenoEncontrado; i++) {
			String reservas = cartelList.get(i);
			if (reservas.equals("Agenda [reservas=[]]")) {
				String salon0 = cartelList.get(++i);
				String salon1 = cartelList.get(++i);
				boolean salon0Lleno = salon0.startsWith("Salon 0: 10/10");
				boolean salon1Lleno = salon1.startsWith("Salon 1: 15/15");
				llenoEncontrado = salon0Lleno && salon1Lleno;
			}
		}
		assertTrue(llenoEncontrado);
	}

	@Test
	void testRestauranteCerrado() {
		String expected = "RESTAURANTE CERRADO";
		boolean contains = cartel.endsWith(expected + "\n");
		System.out.println(expected + "? " + contains);
		assertTrue(contains);
	}
}
