package menus.risorse.films;

import java.util.Vector;
import model.Film;
import myLib.MyMenu;
import view.FilmsView;
import view.MessaggiSistemaView;

public class MenuScegliFilm 
{
	private static final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
	private static final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";

	public static Film show(Vector<Film> films) 
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
				Vector<Film> filmsFiltrati = MenuFiltroFilm.show(films, true);
				
				if(!filmsFiltrati.isEmpty())
				{
					for(int i = 0; i < filmsFiltrati.size(); i++)
					{
						FilmsView.stampaPosizione(i);
						MessaggiSistemaView.cornice();
						filmsFiltrati.get(i).stampaDati(true);
						MessaggiSistemaView.cornice();
					}
					
					int selezione;
					do
					{
						MessaggiSistemaView.cornice();
						selezione = FilmsView.selezionaPrestito(films);
						if(selezione == 0)
						{
							return null;
						}
						else if(films.get(selezione-1).getInPrestito() < films.get(selezione-1).getnLicenze())
						{
							return films.get(selezione-1);
						}
						else
						{
							FilmsView.copieTutteInPrestito(films.get(selezione-1).getTitolo());
						}
					}
					while(true);
				}
				else//nessuna corrispondenza: vettore vuoto
				{
					FilmsView.nessunaCorrispondenza();
					return null;
				}
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Film>filmPrestabili = new Vector<>();
				for(Film film : films)
				{
					if(film.isPrestabile())
					{
						filmPrestabili.add(film);
					}
				}
				if(filmPrestabili.isEmpty())
				{
					FilmsView.noFilmsDisponibili();
					return null;
				}
				
				FilmsView.filmInArchivio();
				for(int i = 0; i < filmPrestabili.size(); i++)
				{
//					System.out.println(i+1 + ")");
					FilmsView.stampaPosizione(i);
					MessaggiSistemaView.cornice();
					filmPrestabili.get(i).stampaDati(true);
					MessaggiSistemaView.cornice();
				}
				int selezione;
				do
				{
					selezione = FilmsView.selezionaPrestito(filmPrestabili);
					if(selezione == 0)
					{
						return null;
					}
					else if(filmPrestabili.get(selezione-1).getInPrestito() < filmPrestabili.get(selezione-1).getnLicenze())
					{
						return filmPrestabili.get(selezione-1);
					}
					else
					{
						FilmsView.copieTutteInPrestito(filmPrestabili.get(selezione-1).getTitolo());
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}
