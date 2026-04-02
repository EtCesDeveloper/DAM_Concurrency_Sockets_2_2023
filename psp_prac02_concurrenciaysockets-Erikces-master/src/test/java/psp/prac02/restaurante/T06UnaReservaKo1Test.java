package psp.prac02.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static psp.prac02.restaurante.Parametros.PUERTO_PUERTAS;

import java.io.File;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class T06UnaReservaKo1Test {

	private static final int NUM_SALONES = 1;
	private static final int NUM_PUERTAS = 1;

	@Test
	void ejecucionTest() {
		try {
			Restaurante deModa = new Restaurante(NUM_SALONES, NUM_PUERTAS, 6600);
			deModa.abrir();
			try (Socket socketPuertas = new Socket("localhost", 6600);
					ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
				Puerta p = new Puerta(deModa, 8, 6600);
				oos.writeObject(p);
				System.out.println("Reserva enviada");
			} catch (NotSerializableException nse) {
				assertEquals("java.io.NotSerializableException: psp.prac02.restaurante.Puerta", nse.toString());
			} catch (UnknownHostException e) {
				fail();
			} catch (IOException e) {
				fail();
			}

			deModa.cerrar();
		} catch (IOException e) {
			fail();
		}
		
	}
	
	@AfterAll
	static void limpieza() {
		File fL = new File("logs.txt");
		fL.renameTo(new File("logs_T06.txt"));
		File fC = new File("cartel.txt");
		fC.renameTo(new File("cartel_T06.txt"));
		Salida.reset();
	}
}
