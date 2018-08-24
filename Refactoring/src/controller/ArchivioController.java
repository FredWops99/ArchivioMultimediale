package controller;

import java.util.Vector;

import menus.risorse.MenuCercaRisorsa;
import menus.risorse.MenuRimuoviRisorsa;
import model.Archivio;
import model.Film;
import model.Libro;
import model.Risorsa;
import view.MessaggiSistemaView;

public class ArchivioController 
{
	private static final String[] CATEGORIE = {"Libri","Film"};

	private FilmController filmController;
	private LibriController libriController;
	
	public ArchivioController(Archivio archivio)
	{
		filmController = new FilmController(archivio.getFilms());
		libriController = new LibriController(archivio.getLibri());
	}
	
//	da usare in storico (Controller ?)
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
	
	/**
	 * permette la ricerca in archivio di un libro o di un film
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void scegliRisorsaDaCercare() 
	{
		MenuCercaRisorsa.show(CATEGORIE, this);
	}
	
	public void cercaRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
//			controller interagisce con view per cercare il libro
			libriController.cercaLibro();
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
//			controller interagisce con view per cercare il film
			filmController.cercaFilm();
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
			return libriController.scegliLibro();
		}
		else if(categoria == CATEGORIE[1])
		{
			return filmController.scegliFilm();
		}
		else return null;
	}
}
