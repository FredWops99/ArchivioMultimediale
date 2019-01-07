package handler;

import java.util.Vector;
import controllerMVC.RisorseController;
import interfaces.Risorsa;
/**
 * Handler che gestisce le operazioni di sistema, delegando alle opportune classi, in base alla scelta dell'utente all'interno di un menu.
 * qui in particolare vengono gestite le opzioni dei menu per cercare una risorsa e scegliere una risorsa
 * in questa classe non è più richiesta l'interazione con l'utente
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
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
	
	public Risorsa scegliFilm(int scelta) 
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa>filmsFiltrati = filtraFilmHandler.menuFiltraFilm(true);
				return risorseController.selezionaRisorsa(filmsFiltrati, "film");
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>filmPrestabili = risorseController.getFilmsController().filmsPrestabili();
				return risorseController.selezionaRisorsa(filmPrestabili, "film");
			}
		}
//		DEFAULT: qua non arriva mai
		assert false;
		return null;
	}
	
	public Risorsa scegliLibro(int scelta)
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
				
				return risorseController.selezionaRisorsa(libriFiltrati, "libri");
			}
				
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>libriPrestabili = risorseController.getLibriController().libriPrestabili();
				
				return risorseController.selezionaRisorsa(libriPrestabili, "libri");
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}
