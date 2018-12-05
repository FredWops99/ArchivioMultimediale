package controllerMVC;

import java.util.Vector;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Film;
import model.Risorse;
import myLib.MyMenu;
import viewInterfaces.IFilmsView;

/**
 * CONTROLLER interagisce con view e modifica il MODEL
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class FilmsController 
{
	private Risorse model;
	private IFilmsView filmsView;

	private ManageRisorseHandler manageRisorseHandler;
//	CATEGORIA è Film
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String IDENTIFIER = "F";
	
	public FilmsController(Risorse risorse, ManageRisorseHandler manageRisorseHandler) 
	{
		this.model = risorse;
		this.manageRisorseHandler = manageRisorseHandler;

//		per la view: leggo classe da proprietà di sistema (caricata dal Main) 
//		così Controller non dipende dalla classe View ma solo dalla sua interfaccia IView
		String className = System.getProperty("FilmsView"); //"FilmsView" è una chiave nel file: il valore corrispondende è il Fully-Qualified Name della classe view
		try 
		{
			this.filmsView = (IFilmsView)Class.forName(className).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public IFilmsView getFilmsView() 
	{
		return filmsView;
	}
	
	public Risorse getModel()
	{
		return model;
	}
	
	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}

		String titolo = filmsView.chiediTitolo();
		int durata = filmsView.chiediDurata();
		int annoDiUscita = filmsView.chiediAnnoUscita();
		String lingua = filmsView.chiediLingua();
		String regista = filmsView.chiediRegista();
		int nLicenze = filmsView.chiediNumeroLicenze();
		
		Film f = new Film(IDENTIFIER + model.getLastId(), sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		boolean aggiuntaRiuscita = model.addRisorsa(f);
		
		if(aggiuntaRiuscita)
		{
			filmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			filmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	/**
 	 * metodo per Test che consente di non chiedere in input all'utente i campi per creare la risorsa
	 */
	public void addFilm(String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze)
	{
		Film f = new Film("F"+ model.getLastId(), sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		model.addRisorsa(f);
	}
	
	private String scegliSottoCategoria()
	{
		final String SCEGLI_CATEGORIA = "scegli la sottocategoria del film: ";
		MyMenu menu = new MyMenu(SCEGLI_CATEGORIA, SOTTOCATEGORIE, true);
		try
		{
			return SOTTOCATEGORIE[menu.scegliBase() - 1];
		}
//		se l'utente selezione 0: ANNULLA -> eccezione
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "annulla";
		}	
	}

	public void conferma(boolean aggiuntaRiuscita) 
	{
		if(aggiuntaRiuscita)
		{
			filmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			filmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	public Vector<Risorsa> filmsPrestabili() 
	{
		Vector<Risorsa>filmPrestabili = new Vector<>();
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='F' && risorsa.isPrestabile())
			{
				filmPrestabili.add(risorsa);
			}
		}
		return filmPrestabili;
	}
	
	/**
	 * presenta all'utente il menu per decidere se scegliere un film dall'archivio completo o filtrando la ricerca.
	 * informerà della scelta l'handler del caso d'uso (ScegliFilmHandler)
	 * @return la risorsa selezionata dall'utente
	 */
	public Risorsa menuScegliFilm() 
	{
		final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
		final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";

		MyMenu menuSceltaFilm = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaFilm.scegliBase();
		
		return manageRisorseHandler.scegliFilm(scelta);
	}
	
	public Film selezionaFilm(Vector<Risorsa> filmsFiltrati) 
	{
		if(filmsFiltrati.isEmpty())
		{
			filmsView.noRisorseDisponibili("films");
			return null;
		}
		else
		{
			for(int i = 0; i < filmsFiltrati.size(); i++)
			{
				filmsView.getMessaggiSistemaView().stampaPosizione(i);
				filmsView.getMessaggiSistemaView().cornice();
				filmsView.stampaDati(filmsFiltrati.get(i), true);
				filmsView.getMessaggiSistemaView().cornice();
			}
			
			int selezione;
			do
			{
				filmsView.getMessaggiSistemaView().cornice();
				selezione = filmsView.selezionaRisorsa(filmsFiltrati.size(), Film.class);
				if(selezione == 0)
				{
					return null;
				}
				else if(filmsFiltrati.get(selezione-1).getInPrestito() < filmsFiltrati.get(selezione-1).getNLicenze())
				{
					return (Film) filmsFiltrati.get(selezione-1);
				}
				else
				{
					filmsView.copieTutteInPrestito(filmsFiltrati.get(selezione-1).getTitolo());
				}
			}
			while(true);
		}
	}	
}