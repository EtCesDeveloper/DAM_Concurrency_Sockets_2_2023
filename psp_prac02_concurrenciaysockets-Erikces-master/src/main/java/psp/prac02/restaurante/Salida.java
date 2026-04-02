package psp.prac02.restaurante;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalTime;

public class Salida  {
	protected static FileWriter salida;
	
	static { 
		try {
			salida = new FileWriter("logs.txt");
		} catch (IOException e) {
			System.err.println("No se ha podido preparar el fichero logs.txt. " + e);
		}
	}
	
	public static void reset() {
		try {
			salida = new FileWriter("logs.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void escribe(String msg) {
	    try {
	    	salida.write(msg);
			System.err.println(msg);
			salida.flush();
		} catch (IOException e) {
			System.err.println("IOException en clase Salida Metodo ESCRIBE");
			//System.out.println(e.getMessage());
		}
	}
	public static void cerrar() {
	    try {
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
