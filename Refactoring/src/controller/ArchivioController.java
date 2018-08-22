package controller;

import menus.risorse.MenuCercaRisorsa;
import menus.risorse.MenuRimuoviRisorsa;
import model.Archivio;
import view.MessaggiSistemaView;

public class ArchivioController 
{
	FilmController filmController;
	LibriController libriController;
	
	public ArchivioController(Archivio archivio)
	{
		filmController = new FilmController(archivio);
		libriController = new LibriController(archivio);
	}
	
	public FilmController getFilmController() 
	{
		return filmController;
	}

	public LibriController getLibriController() 
	{
		return libriController;
	}
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 * @return l'id della risorsa rimossa
	 */
	public String rimuoviRisorsa(String[] CATEGORIE) 
	{
		MessaggiSistemaView.avvisoRimozioneRisorsa();
		
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRimosso = MenuRimuoviRisorsa.showAndReturnID(CATEGORIE, this);
		return idRimosso;
	}
	
	/**
	 * permette la ricerca in archivio di un libro o di un film
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void cercaRisorsa(String[] CATEGORIE) 
	{
//		non va qua: non ha senso che in archivio si usi il controller e non i dati dell'archivio stesso
		MenuCercaRisorsa.show(CATEGORIE, this);
	}
}
