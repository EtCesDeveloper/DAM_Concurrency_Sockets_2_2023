package psp.prac02.restaurante;

public class Parametros {
	/**
	 * Número de puertas del restaurante por defecto.
	 */
	public static final int NUM_PUERTAS = 3;
	/**
	 * Número de salones del restaurante por defecto.
	 */
	public static final int NUM_SALONES = 3;
	
	/** 
	 * Duración simulada de un minuto (para acelerar o ralentizar el sistema).
	 */
	public static final int MIN = 1000; // 1 seg por minuto 

	/**
	 * "Minutos" de apertura de puertas por defecto.
	 */
	public static final int TIEMPO_SERVICIO = 100;
	
	/** 
	 * Tiempo de espera (entre la apertura de puertas y la de salones, entre atención a un cliente y otro
	 * o entre comprobación y comprobación de si ya podemos cerrar, por ejemplo)
	 */
	public static final int ESPERA = 2 * MIN;
	
	/**
	 * Tiempo mínimo de duración de cada mesa.
	 */
	public static final int BASE_MESA = 5; //15;
	
	/**
	 * Tiempo extra por comensal en cada mesa.
	 */
	public static final int EXTRA_X_PERS = 2; //6;
	
	/**
	 * Frecuencia (en "minutos") de reimpresión del cartel del restaurante
	 */
	public static final int REFRESCO_CARTEL = 2; //5;
	
	/**
	 * Número de comensales del salón 0
	 */
	public static final int BASE_SALON = 10;
	/** 
	 * Incremento de comensales por cada salón.
	 */
	public static final int EXTRA_SALON = 5;
	
	/**
	 * Puerto del socket de la puerta 0, el resto deberán ir sumando su id.
	 */
	public static final int PUERTO_PUERTAS = 6000;
	
}
