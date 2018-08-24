package menus.risorse;

import controller.ArchivioController;
import myLib.MyMenu;

public class MenuRimuoviRisorsa 
{
	private static final String INTESTAZIONE = "scegli la categoria: ";

	public static String showAndReturnID(String[] CATEGORIE, ArchivioController archivioController)
	{
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];
			return archivioController.removeRisorsa(categoria);		
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
}
