package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class T01SinReservasTest {
	private static final String FF = ">>  >>"; // fastforward

	private static final int NUM_SALONES = 3;
	private static final int NUM_PUERTAS = 3;
	private static final int DURACION = 10;

	private static List<String> cartelList;
	private static String cartel;
	private static List<String> logsList;
	private static String logs;

	@BeforeAll
	static void ejecucion() throws IOException {
		String[] args = { Integer.toString(NUM_SALONES), Integer.toString(NUM_PUERTAS), Integer.toString(DURACION), Integer.toString(6000) };
		Sistema.main(args);

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
		fL.renameTo(new File("logs_T01.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T01.txt"));
		Salida.reset();
	}

	@Test
	void testPuertas() {
		List<String> puertas = Arrays.asList(FF, 
				"Abriendo puertas...", FF, 
				"Cerrando puertas...", FF,
				"Puertas cerradas", FF);
		assertLinesMatch(puertas, cartelList);
	}

	@Test
	void testPuertasAbrir() {
		for (int i = 0; i < NUM_PUERTAS; i++) {
			String expected = "Puerta " + i + " abierta";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
	}

	@Test
	void testPuertasCerrar() {
		for (int i = 0; i < NUM_PUERTAS; i++) {
			String expected = "Puerta " + i + " cerrada";
			boolean contains = logs.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
	}
	
	@Test
	void testSalones() {
		List<String> puertas = Arrays.asList(FF, 
				"Abriendo salones...", FF, 
				"Cerrando salones...", FF,
				"Salones cerrados", FF);
		assertLinesMatch(puertas, cartelList);
	}
	
	@Test
	void testSalonesCapacidad() {
		for (int i = 0; i < NUM_SALONES; i++) {
			String expected = "Salon " + i + ": 0/" + (10 + 5*i);
			boolean contains = cartel.contains(expected);
			System.out.println(expected + "? " + contains);
			assertTrue(contains);
		}
	}
	
	@Test
	void testSalonesRepeticiones() {
		int[] cont = new int[NUM_SALONES];
		for (int i = 0; i < NUM_SALONES; i++) {
			cont[i]++;
		}
		for (int i = 1; i < NUM_SALONES; i++) {
			assertEquals(cont[0], cont[1]);
		}
	}
	
	@Test
	void testAgenda() {
		String expected = "Agenda [reservas=[]]";
		boolean contains = cartel.contains(expected);
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
