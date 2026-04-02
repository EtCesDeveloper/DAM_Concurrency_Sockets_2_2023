package psp.prac02.restaurante;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Cartel extends Thread {
	
	private Restaurante restaurante;
	private static PrintStream pantalla;
//	private static final LocalDateTimme lT =new LocalDateTime(now());
	
	public Cartel(Restaurante restaurante) throws FileNotFoundException {
		super("Cartel");
		this.restaurante = restaurante;
		pantalla = new PrintStream(new File("cartel.txt"));
	}
	
	public static void imprime(String msg) {
        pantalla.println(msg);
    }
	
	public void run() {
        while(true) {
            try {
                pantalla.println(this);
                Thread.sleep(Parametros.REFRESCO_CARTEL*1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
	
	public String toString() {
        String result = this.restaurante.getAgenda() + "\n";
        for(Salon salon : this.restaurante.getSalones()) {
            result += salon + "\n";
            
        }
        return result;
        
    }

	public static void cerrar() {
		pantalla.close();
	}
}
