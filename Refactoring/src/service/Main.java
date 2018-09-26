package service;

import controllerMVC.*;
import handler.utenti.AccessoHandler;
import interfaces.ISavesManager;
import model.Archivio;
import model.Fruitore;
import model.Fruitori;
import model.Prestiti;
import model.Storico;
import myLib.MyMenu;

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
		ISavesManager gestoreSalvataggi = new GestoreSalvataggi();
		
		gestoreSalvataggi.checkFiles(fruitori, archivio, prestiti);
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = gestoreSalvataggi.caricaFruitori();
		archivio = gestoreSalvataggi.caricaArchivio();
		prestiti = gestoreSalvataggi.caricaPrestiti();		
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
		
		gestoreSalvataggi.salvaFruitori(fruitori);
		gestoreSalvataggi.salvaPrestiti(prestiti);
		
		MainFacade controller = new MainFacade();
		controller.begin(utenteLoggato,archivioController,fruitoriController,storicoController,prestitiController);
	}
	
	

//	public static void salvaArchivio()
//	{
//		gestoreSalvataggi.salvaArchivio(archivio);
//	}
//	public static void salvaFruitori()
//	{
//		gestoreSalvataggi.salvaFruitori(fruitori);
//	}
//	public static void salvaPrestiti()
//	{
//		gestoreSalvataggi.salvaPrestiti(prestiti);
//	}
}