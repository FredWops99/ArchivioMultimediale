package menus.risorse;

import controller.ArchivioController;
import myLib.MyMenu;

public class MenuElencoRisorse 
{
	public static void show(String[] CATEGORIE, ArchivioController archivioController)
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
			if(categoria == CATEGORIE[0])//LIBRI
			{
				archivioController.getLibriController().stampaDatiLibriPrestabili();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				archivioController.getFilmController().stampaDati();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
