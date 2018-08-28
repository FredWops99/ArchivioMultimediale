package menus.risorse.films;

import java.util.Vector;
import controller.FilmsController;
import model.Film;
import myLib.MyMenu;

public class ScegliFilmHandler 
{
	private static final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
	private static final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";

	public static Film show(FilmsController filmController) 
	{
		MyMenu menuSceltaFilm = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaFilm.scegliBase();
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Film> filmsFiltrati = MenuFiltroFilm.show(true, filmController);
				
				return filmController.selezionaFilm(filmsFiltrati);
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Film>filmPrestabili = filmController.filmsPrestabili();
				
				return filmController.selezionaFilm(filmPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}
