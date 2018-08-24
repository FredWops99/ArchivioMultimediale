package menus.risorse;

import controller.ArchivioController;
import myLib.MyMenu;

public class MenuStampaElencoRisorse 
{
	private static final String INTESTAZIONE = "scegli la categoria: ";

	public static void show(String[] CATEGORIE, ArchivioController archivioController)
	{
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se stampare libri o film
			archivioController.stampaDatiRisorsePrestabili(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
