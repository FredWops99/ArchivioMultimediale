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
		Fruitori fruitori = new Fruitori();
		Archivio archivio = new Archivio();
		Prestiti prestiti = new Prestiti();
		Fruitore utenteLoggato = null;
		
//		factory?
		ISavesManager gestoreSalvataggi = new GestoreSalvataggi(fruitori, archivio, prestiti);
//		se non ci sono i file nel percorso li crea (vuoti) e salva all'interno l'oggetto corrispondente.
		gestoreSalvataggi.checkFiles();
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = gestoreSalvataggi.caricaFruitori();
		archivio = gestoreSalvataggi.caricaArchivio();
		prestiti = gestoreSalvataggi.caricaPrestiti();		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		prestiti.ricostruisciPrestiti(archivio);
		
		Storico storico = new Storico(prestiti, fruitori, archivio);
		
//		coordina i compiti
		MainFacade mainFacade = new MainFacade(utenteLoggato, archivio, fruitori, prestiti, storico, gestoreSalvataggi);
		mainFacade.start();
	}
}