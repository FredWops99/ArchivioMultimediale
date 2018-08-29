package controller;

import java.util.Vector;

import handler.risorse.*;
import model.*;
import myLib.MyMenu;
import view.MessaggiSistemaView;

public class ArchivioController 
{
	private final String[] CATEGORIE = {"Libri","Film"};

	private FilmsController filmController;
	private LibriController libriController;
	
	public ArchivioController(Archivio archivio)
	{
		filmController = new FilmsController(archivio.getFilms());
		libriController = new LibriController(archivio.getLibri());
	}
	
	public Vector<Libro> getVectorLibri()
	{
		return libriController.getModel().getLibri();
	}
	
	public Vector<Film> getVectorFilm()
	{
		return filmController.getModel().getFilms();
	}
	
	private String menuRimuoviRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";

		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		return RimuoviRisorsaHandler.rimuoviRisorsa(scelta, CATEGORIE, this);
	}
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * (precondizione: CATEGORIE != null)
	 * @return l'id della risorsa rimossa
	 */
	public String scegliRisorsaDaRimuovere() 
	{
		MessaggiSistemaView.avvisoRimozioneRisorsa();
		
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRimosso = menuRimuoviRisorsa();
//				RimuoviRisorsaHandler.showAndReturnID(CATEGORIE, this);
		return idRimosso;
	}
	
	public String rimuoviRisorsa(String categoria) 
	{
		String idRisorsa = "-1";
		if(categoria == CATEGORIE[0])//LIBRI
		{
			idRisorsa = libriController.removeLibro();
		}
		if(categoria == CATEGORIE[1])//FILMS
		{
			idRisorsa = filmController.removeFilm();
		}
		return idRisorsa;
	}

	public void menuAggiungiRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		AggiungiRisorsaHandler.aggiungiRisorsa(scelta, CATEGORIE, this);
	} 
	
	public void addRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
//			controller interagisce con view per creare il libro
			libriController.addLibro();
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
//			controller interagisce con view per creare il film
			filmController.addFilm();
		}
	}
	
	public void menuCercaRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		CercaRisorsaHandler.cercaRisorsa(scelta, CATEGORIE, this);
	}
	
	public void cercaRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
			libriController.menuFiltraLibri(false);
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			filmController.menuFiltraFilm(false);
		}
	}

	public void menuVisualizzaElencoRisorse() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		VisualizzaElencoRisorseHandler.visualizza(scelta, CATEGORIE, this);
	}
	
	public void visualizzaDatiRisorsePrestabili(String categoria) 
	{
		if(categoria == CATEGORIE[0])//LIBRI
		{
			libriController.stampaDatiLibriPrestabili();
		}
		if(categoria == CATEGORIE[1])//FILMS
		{
			filmController.stampaDatiFilmPrestabili();
		}		
	}

	public Risorsa scegliRisorsa(String categoria) 
	{
		if(categoria == CATEGORIE[0])
		{
//			stampa il menu, l'utente sceglie, poi se ne occupa ScegliLibroHandler
			return libriController.menuScegliLibro();
		}
		else if(categoria == CATEGORIE[1])
		{
//			stampa il menu, l'utente sceglie, poi se ne occupa ScegliFilmHandler
			return filmController.menuScegliFilm();
		}
		else return null;
	}
}
