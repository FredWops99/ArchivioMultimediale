package controller;

import java.util.Vector;
import menus.risorse.*;
import menus.risorse.films.MenuFiltroFilm;
import menus.risorse.libri.MenuFiltroLibri;
import menus.risorse.films.ScegliFilmHandler;
import menus.risorse.libri.ScegliLibroHandler;
import model.*;
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
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 * @return l'id della risorsa rimossa
	 */
	public String scegliRisorsaDaRimuovere() 
	{
		MessaggiSistemaView.avvisoRimozioneRisorsa();
		
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRimosso = MenuRimuoviRisorsa.showAndReturnID(CATEGORIE, this);
		return idRimosso;
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
	
	public void cercaRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
			MenuFiltroLibri.show(false, libriController);
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			MenuFiltroFilm.show(false, filmController);	
		}
	}

	public void stampaDatiRisorsePrestabili(String categoria) 
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

	public String removeRisorsa(String categoria) 
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

	public Risorsa scegliRisorsa(String categoria) 
	{
		if(categoria == CATEGORIE[0])
		{
//			return libriController
			return ScegliLibroHandler.show(libriController);
		}
		else if(categoria == CATEGORIE[1])
		{
			return ScegliFilmHandler.show(filmController);
		}
		else return null;
	} 
}
