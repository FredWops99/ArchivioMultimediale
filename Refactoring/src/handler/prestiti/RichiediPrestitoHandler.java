package handler.prestiti;

import controller.ArchivioController;
import controller.PrestitiController;
import model.Fruitore;
import model.Main;
import model.Risorsa;

public class RichiediPrestitoHandler 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	
	public static void richiedi(int scelta, Fruitore utenteLoggato, PrestitiController prestitiController, ArchivioController archivioController) 
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
}
