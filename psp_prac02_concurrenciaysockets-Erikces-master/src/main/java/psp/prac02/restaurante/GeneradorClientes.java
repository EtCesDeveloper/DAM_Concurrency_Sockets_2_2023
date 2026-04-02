package psp.prac02.restaurante;

import static psp.prac02.restaurante.Parametros.NUM_PUERTAS;
import static psp.prac02.restaurante.Parametros.PUERTO_PUERTAS;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GeneradorClientes {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				int puerta = (int) (Math.random() * NUM_PUERTAS);
				try (Socket socketPuertas = new Socket("localhost", PUERTO_PUERTAS + puerta);
						ObjectOutputStream oos = new ObjectOutputStream(socketPuertas.getOutputStream())) {
					System.out.println("Nueva reserva");
					System.out.println("A nombre de: ");
					String nombre = sc.nextLine();
					System.out.println("Para (comensales):");
					int comensales = Integer.parseInt(sc.nextLine());
					Reserva r = new Reserva(nombre, comensales);
					oos.writeObject(r);
					System.out.println("Reserva enviada");
				} catch (UnknownHostException e) {
					System.err.println("Parece que el restaurante no está inaugurado. " + e);
				} catch (IOException e) {
					System.err.println("O no logramos conectarnos, no la reserva no está bien. " + e);
				}
			}
		}
	}

}
