package model;

import java.util.Vector;
import controller.ArchivioController;
import controller.FruitoriController;
import menus.utenti.MenuAccesso;

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
		ricostruisciPrestiti();
		
//		controllers per interazione utente-vista-model. presenti nelle operazioni dei casi d'uso (interazione utente-sistema) 
//		gli passo archivio appena caricato. associa model e view.
//		si potrà creare un unico gestore dei controller?
		ArchivioController archivioController = new ArchivioController(archivio);
		FruitoriController fruitoriController = new FruitoriController(fruitori);
		
//		segna come "decadute" le iscrizioni dei fruitori in archivio che sono scadute.
		Vector<Fruitore>utentiScaduti = fruitoriController.controlloIscrizioni();
//		rimuove i prestiti di tali utenti scaduti
		prestiti.terminaTuttiPrestitiDi(utentiScaduti);
		prestiti.controlloPrestitiScaduti();
		
		GestoreSalvataggi.salvaFruitori(fruitori);
		GestoreSalvataggi.salvaPrestiti(prestiti);
		
		MenuAccesso.show(prestiti, archivioController, fruitoriController);
	}
	
	/**
	 * quando salvo oggetti in un file e poi li ricarico, i libri di "Prestiti" non corrispondono più a quelli in "Libri" (verificato con hashcode che cambia, da 
	 * uguale prima del caricamento diventa diverso dopo il caricamento): Risorse e Prestiti vengono salvati in posti diversi e poi caricati come "diversi".
	 * Quando invece viene creato il prestito, la sua risorsa e quella in archivio sono lo stesso oggetto. Salvando e caricando in due posti diversi è come se "si sdoppiasse".
	 * Per non dover salvare tutto in unico file, in questo metodo ricollego gli elementi in modo da farli riferire allo stesso oggetto (tramite ID univoco):
	 * quando dico che il libro in "Prestito" torna dal prestito, si aggiornano anche le copie disponibili in "Libri"
	 */
	public static void ricostruisciPrestiti()
	{
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.getRisorsa() instanceof Libro)
			{
				for(Libro libro : archivio.getLibri().getLibri())
				{
					if(prestito.getRisorsa().equals(libro))
					{
						prestito.setRisorsa(libro);
					}
				}
			}
			else if(prestito.getRisorsa() instanceof Film)
			{
				for(Film film : archivio.getFilms().getFilms())
				{
					if(prestito.getRisorsa().equals(film))
					{
						prestito.setRisorsa(film);
					}
				}
			}
//			else if(altra categoria)
//			{
//				...
//			}
			
		}
	}

	public static Fruitore getUtenteLoggato() 
	{
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Fruitore _utenteLoggato) 
	{
		utenteLoggato = _utenteLoggato;
	}	
	
	public static void salvaArchivio()
	{
		GestoreSalvataggi.salvaArchivio(archivio);
	}
	public static void salvaFruitori()
	{
		GestoreSalvataggi.salvaFruitori(fruitori);
	}
}