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
			
//			il main salva su file quell'archivio, nella classe MenuOperatore
//			non c'è bisogno di settare l'archivio del main perchè è lo stesso riferimento
//			Main.setArchivio(model);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
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
	
//	deve restituire al main l'id della risorsa da eliminare (far diventare non prestabile)
	public String removeFilm()
	{
		Vector<Risorsa> risorse = model.getRisorse();
		
		String idSelezionato;
		
		String titolo = FilmsView.chiediRisorsaDaRimuovere(Film.class);
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < risorse.size(); i++) 
		{
			if(risorse.get(i).getId().charAt(0)=='F' && risorse.get(i).isPrestabile() 
					&& risorse.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			FilmsView.risorsaNonPresente(Film.class);
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = risorse.get((int)posizioniRicorrenze.get(0)).getId();
			risorse.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			FilmsView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore (più films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.piùRisorseStessoTitolo(titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(risorse.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
					
			if(daRimuovere > 0)
			{
				idSelezionato = risorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				risorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				FilmsView.rimozioneAvvenuta();
			}
			else//0: annulla
			{
				idSelezionato = "-1";
			}
		}		
		return idSelezionato;
	}
	
	public void stampaDatiFilm(Risorsa risorsa, boolean perPrestito) 
	{
		FilmsView.stampaDati(risorsa, perPrestito);
	}
	
//	main contatta il controller che si occupa dell'interazione con la view
	public void stampaDatiFilmPrestabili() 
	{
		Vector<Risorsa>filmDaStampare = new Vector<>();
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='F' && risorsa.isPrestabile())
			{
				filmDaStampare.add(risorsa);
			}
		}
		FilmsView.stampaDati(filmDaStampare);
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
			FilmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	public Vector<Risorsa> filtraFilmPerTitolo(String titoloParziale)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = model.filtraRisorsePerTitolo(titoloParziale);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='F')
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraFilmPerUscita(int annoUscita)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = model.filtraRisorsePerUscita(annoUscita);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='F')
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraFilmPerRegista(String regista)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = model.filtraFilmPerRegista(regista);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='F')
			{
				result.addElement(risorsa);
			}
		}
		return result;
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
			FilmsView.noRisorseDisponibili();
			return null;
		}
		else
		{
			for(int i = 0; i < filmsFiltrati.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(filmsFiltrati.get(i), true);
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