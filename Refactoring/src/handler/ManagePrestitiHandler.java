package handler;

import controllerMVC.ArchivioController;
import controllerMVC.PrestitiController;
import model.Fruitore;
import model.Risorsa;
import service.Main;

public class ManagePrestitiHandler 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	
	public static void richiediPrestito(int scelta, Fruitore utenteLoggato, PrestitiController prestitiController, ArchivioController archivioController) 
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			
			Risorsa risorsa = archivioController.scegliRisorsa(categoria);
			if(risorsa != null)
			{
				prestitiController.effettuaPrestito(utenteLoggato, risorsa);
			}

			Main.salvaPrestiti();
			Main.salvaArchivio();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public static void terminaPrestiti(int scelta, Fruitore utenteLoggato, PrestitiController prestitiController) 
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
				
				Main.salvaPrestiti();
				Main.salvaArchivio();
				
				break;
			}
			case 2://elimina un solo prestito (sceglie l'utente)
			{
				prestitiController.terminaPrestitoDi(utenteLoggato);
				
				Main.salvaPrestiti();
				Main.salvaArchivio();
				
				break;
			}
		}		
	}
}
