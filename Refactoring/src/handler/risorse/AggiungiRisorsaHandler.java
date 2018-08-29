package handler.risorse;

import controller.ArchivioController;

public class AggiungiRisorsaHandler 
{	
	public static void aggiungiRisorsa(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se creare un libro o un film
			archivioController.addRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
