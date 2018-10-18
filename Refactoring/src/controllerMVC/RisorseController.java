package controllerMVC;

import handler.FiltraFilmHandler;
import handler.FiltraLibriHandler;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.*;
import myLib.MyMenu;
import view.MessaggiSistemaView;

public class RisorseController 
{
	private final String[] CATEGORIE = {"Libri","Film"};

	private FilmsController filmsController;
	private LibriController libriController;
	
	private FiltraLibriHandler filtraLibriHandler;
	private FiltraFilmHandler filtraFilmHandler;
	private ManageRisorseHandler manageRisorseHandler;
	
	public RisorseController(Risorse risorse)
	{
		manageRisorseHandler = new ManageRisorseHandler(this);
		filmsController = new FilmsController(risorse,manageRisorseHandler);
		libriController = new LibriController(risorse,manageRisorseHandler);
	}
	
	public FilmsController getFilmsController() 
	{
		return filmsController;
	}

	public LibriController getLibriController() 
	{
		return libriController;
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
	
	private String menuRimuoviRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";

		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			return rimuoviRisorsa(categoria);		
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
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
			idRisorsa = filmsController.removeFilm();
		}
		return idRisorsa;
	}
	
	public void menuAggiungiRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		try
		{
			String categoria = CATEGORIE[scelta - 1];
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se creare un libro o un film
			addRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
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
			filmsController.addFilm();
		}
	}

	public void menuVisualizzaElencoRisorse() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		try
		{
			String categoria = CATEGORIE[scelta - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se stampare libri o film
			visualizzaDatiRisorsePrestabili(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}	}
	
	public void visualizzaDatiRisorsePrestabili(String categoria) 
	{
		if(categoria == CATEGORIE[0])//LIBRI
		{
			libriController.stampaDatiLibriPrestabili();
		}
		if(categoria == CATEGORIE[1])//FILMS
		{
			filmsController.stampaDatiFilmPrestabili();
		}		
	}
	
//	la ricerca avviene tramite filtri
	public void cercaRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
			if(filtraLibriHandler==null)
			{
				filtraLibriHandler = new FiltraLibriHandler(libriController);
			}
			filtraLibriHandler.menuFiltraLibri(false);
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			if(filtraFilmHandler==null)
			{
				filtraFilmHandler = new FiltraFilmHandler(filmsController);
			}
			filtraFilmHandler.menuFiltraFilm(false);
		}
	}

//	quando si deve scegliere una risorsa per il prestito c'è la possibilità di visualizzare l'intero elenco
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
			return filmsController.menuScegliFilm();
		}
		else return null;
	}
}
