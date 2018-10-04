package handler.risorse.films;

import java.util.Vector;

import controllerMVC.FilmsController;
import model.Film;
import model.Risorsa;

/**
 * Gestisce il caso d'uso di scelta di un film, in entrambi gli scenari: scelta dall'archivio completo o filtrando la ricerca
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class ScegliFilmHandler 
{
	
	public static Film scegliFilm(int scelta, FilmsController filmController) 
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Risorsa> filmsFiltrati = filmController.menuFiltraFilm(true);
				
				return filmController.selezionaFilm(filmsFiltrati);
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Risorsa>filmPrestabili = filmController.filmsPrestabili();
				
				return filmController.selezionaFilm(filmPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}
