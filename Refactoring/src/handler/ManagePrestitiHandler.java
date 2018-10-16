package handler;

import controllerMVC.RisorseController;
import controllerMVC.PrestitiController;
import interfaces.ISavesManager;
import model.Fruitore;
import model.Risorsa;

public class ManagePrestitiHandler 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	
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
