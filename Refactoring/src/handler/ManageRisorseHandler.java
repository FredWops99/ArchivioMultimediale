package handler;

import java.util.Vector;

import controllerMVC.ArchivioController;
import controllerMVC.FilmsController;
import controllerMVC.LibriController;
import model.Film;
import model.Libro;
import model.Risorsa;

public class ManageRisorseHandler 
{	
	public static void aggiungiRisorsa(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se creare un libro o un film
			archivioController.addRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public static void cercaRisorsa(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se CERCARE un libro o un film
			archivioController.cercaRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public static String rimuoviRisorsa(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{			
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			return archivioController.rimuoviRisorsa(categoria);		
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
	
	public static Film scegliFilm(int scelta, FilmsController filmController) 
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa> filmsFiltrati = filmController.menuFiltraFilm(true);
				
				return filmController.selezionaFilm(filmsFiltrati);
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>filmPrestabili = filmController.filmsPrestabili();
				
				return filmController.selezionaFilm(filmPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	public static Libro scegliLibro(int scelta, LibriController libriController)
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa> libriFiltrati = libriController.menuFiltraLibri(true);
				
				return libriController.selezionaLibro(libriFiltrati);
			}
				
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>libriPrestabili = libriController.libriPrestabili();
				
				return libriController.selezionaLibro(libriPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	public static void visualizzaRisorse(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se stampare libri o film
			archivioController.visualizzaDatiRisorsePrestabili(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
