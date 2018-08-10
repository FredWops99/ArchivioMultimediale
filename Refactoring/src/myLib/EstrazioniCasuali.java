package myLib;

import java.util.Random;

/**
 * Classe di utilità generale per la creazione di tipi primitivi oggetti utili.
 * 
 * @author Stefano Prandini
 */

public class EstrazioniCasuali {

/**
 * Metodo che genera un numero intero casuale.
 * @param min numero intero minimo generabile casualmente.
 * @param max numero intero massimo generabile casualmente.
 * @return numero intero casuale compreso tra min e max.
 */
	
	private static Random rand = new Random();
	
	public static int estraiIntero(int min, int max) 
	{
		  
		  int numRandom = rand.nextInt((max - min) + 1) + min;
		  return numRandom;	
	}
	
	public static double estraiDouble(double min, double max)
	{
	 double range = max - min;
	 double casual = rand.nextDouble();
	 
	 double posEstratto = range*casual;
	 
	 return posEstratto + min;
	}
	
	public static String estraiCifre(int numeroCifre)
	{
		String cifre = "";
		for(int i = 0; i < numeroCifre; i++)
		{
			cifre = cifre + estraiIntero(0, 9);
		}
		
		return cifre;
	}
}


