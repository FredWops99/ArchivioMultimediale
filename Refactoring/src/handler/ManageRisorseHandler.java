package handler;

import java.util.Vector;
import controllerMVC.RisorseController;
import interfaces.Risorsa;
import model.Film;
import model.Libro;

public class ManageRisorseHandler 
{	
	private RisorseController risorseController;
	private FiltraFilmHandler filtraFilmHandler;
	private FiltraLibriHandler filtraLibriHandler;
	
	public ManageRisorseHandler(RisorseController risorseController)
	{
		this.risorseController = risorseController;
		this.filtraFilmHandler = new FiltraFilmHandler(risorseController.getFilmsController());
		this.filtraLibriHandler = new FiltraLibriHandler(risorseController.getLibriController());
	}
	
	public void cercaRisorsa(int scelta, String[] CATEGORIE)
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se CERCARE un libro o un film
			risorseController.cercaRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public Film scegliFilm(int scelta) 
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa> filmsFiltrati = filtraFilmHandler.menuFiltraFilm(true);
				
				return risorseController.getFilmsController().selezionaFilm(filmsFiltrati);
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>filmPrestabili = risorseController.getFilmsController().filmsPrestabili();
				
				return risorseController.getFilmsController().selezionaFilm(filmPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	public Libro scegliLibro(int scelta)
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa> libriFiltrati = filtraLibriHandler.menuFiltraLibri(true);
				
				return risorseController.getLibriController().selezionaLibro(libriFiltrati);
			}
				
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>libriPrestabili = risorseController.getLibriController().libriPrestabili();
				
				return risorseController.getLibriController().selezionaLibro(libriPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}
