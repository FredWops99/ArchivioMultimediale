package menus.risorse;

import controller.ArchivioController;
import myLib.MyMenu;

public class MenuAggiungiRisorsa 
{	
	private static final String INTESTAZIONE = "scegli la categoria: ";

	public static void show(String[] CATEGORIE, ArchivioController archivioController)
	{
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase()-1];
			if(categoria == CATEGORIE[0])//LIBRO
			{
				archivioController.getLibriController().addLibro();
			}
			if(categoria == CATEGORIE[1])//FILM
			{
//				controller interagisce con view per creare il film
				archivioController.getFilmController().addFilm();
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 per uscire viene lanciata eccezione: CATEGORIE[-1].
//			non va fatto nulla, basta intercettarla
		}
	}
}
