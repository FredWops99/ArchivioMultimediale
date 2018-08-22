package menus.risorse;

import controller.ArchivioController;
import myLib.MyMenu;

public class MenuRimuoviRisorsa 
{
	public static String showAndReturnID(String[] CATEGORIE, ArchivioController archivioController)
	{
		String idRisorsa = "-1";

		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];
			if(categoria == CATEGORIE[0])//LIBRI
			{
				idRisorsa = archivioController.getLibriController().removeLibro();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				idRisorsa = archivioController.getFilmController().removeFilm();
			}
			return idRisorsa;
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
}
