package menus.risorse;

import controller.FilmController;
import model.Libri;
import myLib.MyMenu;

public class MenuRimuoviRisorsa 
{
	public static String showAndReturnID(String[] CATEGORIE, Libri libri, FilmController filmController)
	{
		String idRisorsa = "-1";

		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];
			if(categoria == CATEGORIE[0])//LIBRI
			{
				idRisorsa = libri.removeLibro();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				idRisorsa = filmController.removeFilm();
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
