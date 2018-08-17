package menus.risorse;

import model.Films;
import model.Libri;
import myLib.MyMenu;

public class MenuAggiungiRisorsa 
{
	public static void show(String[] CATEGORIE, Libri libri, Films films)
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase()-1];
			if(categoria == CATEGORIE[0])//LIBRO
			{
				libri.addLibro();
			}
			if(categoria == CATEGORIE[1])//FILM
			{
				films.addFilm();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 per uscire viene lanciata eccezione: CATEGORIE[-1].
//			non va fatto nulla, basta intercettarla
		}
	}
}
