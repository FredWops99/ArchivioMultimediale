package controllerMVC;

import java.util.Vector;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Film;
import model.Risorse;
import myLib.MyMenu;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FilmsController 
{
	private Risorse model;
	private ManageRisorseHandler manageRisorseHandler;
	
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String IDENTIFIER = "F";
	
	public FilmsController(Risorse risorse, ManageRisorseHandler manageRisorseHandler) 
	{
		this.model = risorse;
		this.manageRisorseHandler = manageRisorseHandler;
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

		String titolo = FilmsView.chiediTitolo();
		int durata = FilmsView.chiediDurata();
		int annoDiUscita = FilmsView.chiediAnnoUscita();
		String lingua = FilmsView.chiediLingua();
		String regista = FilmsView.chiediRegista();
		int nLicenze = FilmsView.chiediNumeroLicenze();
		
		Film f = new Film(IDENTIFIER + model.getLastId(), sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		boolean aggiuntaRiuscita = model.addRisorsa(f);
		
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
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
	
	/**
 	 * metodo per Test che consente di non chiedere in input all'utente i campi per creare la risorsa
	 */
	public void addFilm(String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze)
	{
		Film f = new Film("F"+ model.getLastId(), sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		model.addRisorsa(f);
	}
	
//	public void stampaDatiFilmPrestabili() 
//	{
//		Vector<Risorsa>filmDaStampare = new Vector<>();
//		for(Risorsa risorsa : model.getRisorse())
//		{
//			if(risorsa.getId().charAt(0)=='F' && risorsa.isPrestabile())
//			{
//				filmDaStampare.add(risorsa);
//			}
//		}
//		FilmsView.stampaDati(filmDaStampare);
//	}

	public void conferma(boolean aggiuntaRiuscita) 
	{
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
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
			FilmsView.noRisorseDisponibili("films");
			return null;
		}
		else
		{
			for(int i = 0; i < filmsFiltrati.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				FilmsView.stampaDati(filmsFiltrati.get(i), true);
				MessaggiSistemaView.cornice();
			}
			
			int selezione;
			do
			{
				MessaggiSistemaView.cornice();
				selezione = FilmsView.selezionaRisorsa(filmsFiltrati.size(), Film.class);
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
					FilmsView.copieTutteInPrestito(filmsFiltrati.get(selezione-1).getTitolo());
				}
			}
			while(true);
		}
	}	
}