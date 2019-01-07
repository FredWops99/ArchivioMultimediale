package handler;

import controllerMVC.RisorseController;
import controllerMVC.PrestitiController;
import interfaces.ISavesManager;
import interfaces.Risorsa;
import model.Fruitore;
/**
 * Handler che gestisce le operazioni di sistema, delegando alle opportune classi, in base alla scelta dell'utente all'interno di un menu.
 * qui in particolare vengono gestite le opzioni dei menu per richiedere/terminare prestiti
 * in questa classe non è più richiesta l'interazione con l'utente
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
public class ManagePrestitiHandler 
{
	private static final String[] CATEGORIE = {"Libri","Films"};
	
	private Fruitore utenteLoggato;
	private PrestitiController prestitiController;
	private RisorseController risorseController;
	private ISavesManager gestoreSalvataggi;
	
	public ManagePrestitiHandler(Fruitore utenteLoggato, PrestitiController prestitiController, 
									RisorseController risorseController, ISavesManager gestoreSalvataggi)
	{
		this.utenteLoggato = utenteLoggato;
		this.prestitiController = prestitiController;
		this.risorseController = risorseController;
		this.gestoreSalvataggi = gestoreSalvataggi;
	}
	
	public void richiediPrestito(int scelta) 
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			
			Risorsa risorsa = risorseController.scegliRisorsa(categoria);
			if(risorsa != null)
			{
				prestitiController.effettuaPrestito(utenteLoggato, risorsa);
			}

			gestoreSalvataggi.salvaPrestiti();
			gestoreSalvataggi.salvaRisorse();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public void terminaPrestiti(int scelta) 
	{
		switch (scelta) 
		{
			case 0://indietro
			{
				break;
			}
			case 1://elimina tutti i prestiti
			{
				prestitiController.terminaTuttiPrestitiDi(utenteLoggato);
				
				gestoreSalvataggi.salvaPrestiti();
				gestoreSalvataggi.salvaRisorse();
				
				break;
			}
			case 2://elimina un solo prestito (sceglie l'utente)
			{
				prestitiController.terminaPrestitoDi(utenteLoggato);
				
				gestoreSalvataggi.salvaPrestiti();
				gestoreSalvataggi.salvaRisorse();
				
				break;
			}
		}		
	}
}
