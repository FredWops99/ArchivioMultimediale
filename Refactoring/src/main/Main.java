package main;

import controller.*;
import menus.utenti.MenuAccesso;
import model.Archivio;
import model.Fruitore;
import model.Fruitori;
import model.Prestiti;
import model.Storico;
import myLib.GestoreSalvataggi;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	private static Fruitori fruitori = new Fruitori();
	private static Archivio archivio = new Archivio();
	private static Prestiti prestiti = new Prestiti();
	
	private static Fruitore utenteLoggato;
	
	public static void main(String[] args)
	{
		GestoreSalvataggi.checkFiles(fruitori, archivio, prestiti);
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = GestoreSalvataggi.caricaFruitori();
		archivio = GestoreSalvataggi.caricaArchivio();
		prestiti = GestoreSalvataggi.caricaPrestiti();		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		prestiti.ricostruisciPrestiti(archivio);
		
		Storico storico = new Storico(prestiti, fruitori, archivio);
//		controllers per interazione utente-vista-model. presenti nelle operazioni dei casi d'uso (interazione utente-sistema) 
//		gli passo archivio appena caricato. associa model e view.
//		si potrà creare un unico gestore dei controller?
		ArchivioController archivioController = new ArchivioController(archivio);
		FruitoriController fruitoriController = new FruitoriController(fruitori);
		StoricoController storicoController = new StoricoController(storico);
		PrestitiController prestitiController = new PrestitiController(prestiti); 

		
//		segna come "decadute" le iscrizioni dei fruitori che sono scadute.
//		rimuove i prestiti di tali utenti scaduti
		prestitiController.terminaTuttiPrestitiDi(fruitoriController.controlloIscrizioni());
		prestitiController.controlloPrestitiScaduti();
		
		GestoreSalvataggi.salvaFruitori(fruitori);
		GestoreSalvataggi.salvaPrestiti(prestiti);
		
		MenuAccesso.show(utenteLoggato, archivioController, fruitoriController, storicoController, prestitiController);
	}
	
	public static void salvaArchivio()
	{
		GestoreSalvataggi.salvaArchivio(archivio);
	}
	public static void salvaFruitori()
	{
		GestoreSalvataggi.salvaFruitori(fruitori);
	}
	public static void salvaPrestiti()
	{
		GestoreSalvataggi.salvaPrestiti(prestiti);
	}
}