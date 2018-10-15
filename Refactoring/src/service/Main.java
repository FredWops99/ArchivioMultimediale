package service;

import interfaces.ISavesManager;
import model.*;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	public static void main(String[] args)
	{		
//		factory?
		ISavesManager gestoreSalvataggi = new GestoreSalvataggi();
		
		Fruitori fruitori = gestoreSalvataggi.getFruitori();
		Risorse risorse = gestoreSalvataggi.getRisorse();
		Prestiti prestiti = gestoreSalvataggi.getPrestiti();
	
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		prestiti.ricostruisciPrestiti(risorse);
		
		Storico storico = new Storico(prestiti, fruitori, risorse);
		
//		coordina i compiti
		MainFacade mainFacade = new MainFacade(risorse, fruitori, prestiti, storico, gestoreSalvataggi);
		mainFacade.start();
	}
}