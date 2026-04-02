package psp.prac02.restaurante;
import static psp.prac02.restaurante.Parametros.*;

import java.io.IOException;

public class Sistema {

	public static void main(String[] args) throws IOException {
		int numSalones = args.length > 0 ? Integer.parseInt(args[0]) : NUM_SALONES;
		int numPuertas = args.length > 1 ? Integer.parseInt(args[1]) : NUM_PUERTAS;
		int tiempo = args.length > 2 ? Integer.parseInt(args[2]) : TIEMPO_SERVICIO;
		int basePuerto = args.length > 3 ? Integer.parseInt(args[3]) : PUERTO_PUERTAS;
		Restaurante deModa = new Restaurante(numSalones, numPuertas, basePuerto);
		deModa.abrir();
		while (tiempo > 0) {
			try {
				tiempo--;
				Thread.sleep(MIN);
			} catch (InterruptedException ignored) {
			}
		}
		System.err.println("El restaurante va a cerrar...");
		deModa.cerrar();
	}

}
