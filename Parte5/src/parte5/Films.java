package parte5;

import java.io.Serializable;
import java.util.Vector;
import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

/**
 * Classe che rappresenta l'insieme dei film in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Films implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * id incrementale univoco per ogni film
	 */
	private int lastId;
	
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di uscita", "Filtra per regista"};
	private static final int ANNO_PRIMA_PELLICOLA = 1885;
	
	private Vector<Film> films;
	
	/**
	 * costruttore della classe: inizializza il Vector di films
	 */
	public Films()
	{
		this.films = new Vector<Film>();
		lastId = 0;
	}
	public Vector<Film> getfilms() 
	{
		return films;
	}
	public void setfilms(Vector<Film> films) 
	{
		this.films = films;
	}
	
	/**
	 * procedura per l'aggiunta di un film alla raccolta: chiede all'utente di inserire tutti i campi necessari, crea l'oggetto Film e lo aggiunge al vector
	 */
	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del film: ");
		int durata = InputDati.leggiInteroPositivo("Inserisci la durata del film (in minuti): ");
		int annoDiUscita = InputDati.leggiIntero("Inserisci l'anno di uscita: ", ANNO_PRIMA_PELLICOLA, GestioneDate.ANNO_CORRENTE);
		String lingua = InputDati.leggiStringaNonVuota("Inserisci la lingua del film: ");
		String regista = InputDati.leggiStringaNonVuota("Inserisci il regista: ");	
		int nLicenze = InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
		
		Film f = new Film("F"+lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		addPerSottoCategorie(f);
		
		System.out.println("Film aggiunto con successo!");
	}
	
	/**
	 * presenta all'utente la scelta della sottocategoria di Film tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
	private String scegliSottoCategoria()
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del film: ", SOTTOCATEGORIE, true);
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
	 * inserisco i films nel Vector in modo che siano ordinati per sottocategorie, così, quando vengono stampati, i generi sono in ordine
	 * (il metodo stampaFilms li raccoglierà per generi)
	 * @param f il film da inserire
	 */
	private void addPerSottoCategorie(Film f)
	{
		if(films.isEmpty())
		{
			films.add(f);
		}
		else
		{
			for(int i = 0; i < films.size(); i++)
			{
				if(films.get(i).getSottoCategoria().equals(f.getSottoCategoria()))
				{
					films.add(i+1, f);
					return;
				}
			}
			films.add(f);
		}
	}
	
	/**
	 * procedura per rimuovere un film dalla raccolta: viene chiesto il nome del film e se ce ne sono più di uno viene chiesto all'utente quale eliminare
	 * @return l'id del film che l'utente ha deciso di rimuovere ("-1" se non viene rimosso nessun film)
	 */
	public String removeFilm()
	{
		String idSelezionato;
		
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del film da rimuovere: ");
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < films.size(); i++) 
		{
			if(films.get(i).isPrestabile() && films.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			System.out.println("Siamo spiacenti, il film non è presente nell'archivio");
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);;
			System.out.println("Rimozione avvenuta con successo!");
		}
//		se ci sono più elementi nel vettore (più films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			System.out.println("Sono presenti più films dal titolo \"" + titolo + "\": ");
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				System.out.println("\nRicorrenza " + ++pos + ":");
				System.out.println(BelleStringhe.CORNICE);
				films.elementAt((int)i).stampaDati(false);
				System.out.println(BelleStringhe.CORNICE);
			}
			
			int daRimuovere = InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
			if(daRimuovere > 0)
			{
				idSelezionato = films.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				films.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				System.out.println("Rimozione avvenuta con successo!");
			}
			else//0: annulla
			{
				idSelezionato = "-1";
			}
		}
		return idSelezionato;
	}
	
	/**
	 * stampa i dati dei film corrispondenti ai parametri di ricerca specificati dall'utente
	 */
	public void cercaFilm()
	{
		Vector<Film> filmsFiltrati = null;
		String titoloParziale = null;
		int annoUscita = 0;
		String nomeRegista = null;
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
		int scelta = menuFiltro.scegliBase();
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del film: \n");
				filmsFiltrati = filtraFilmPerTitolo(titoloParziale);
				break;
			}
			
			case 2:	//FILTRA PER ANNO PUBBLICAZIONE
			{
				annoUscita = InputDati.leggiIntero("Inserisci l'anno di uscita: \n", ANNO_PRIMA_PELLICOLA, GestioneDate.ANNO_CORRENTE);
				filmsFiltrati = filtraFilmPerUscita(annoUscita);
				break;
			}
			
			case 3: //FILTRA PER AUTORE
			{
				nomeRegista = InputDati.leggiStringaNonVuota("Inserisci il nome del regista:  \n");
				filmsFiltrati = filtraFilmPerRegista(nomeRegista);
				break;
			}
		}
		
		if(scelta == 1 && filmsFiltrati.isEmpty()) 
		{
			System.out.println("In archivio non sono presenti film il cui titolo è " + titoloParziale);
			return;
		}
		if(scelta == 2 && filmsFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti film il cui anno di uscita è " + annoUscita);
			return;
		}
		if(scelta == 3 && filmsFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti film il cui regista è " + nomeRegista);
			return;
		}
		
		for (int i=0; i <filmsFiltrati.size(); i++) 
		{
			System.out.println();
			System.out.println(BelleStringhe.CORNICE);
			filmsFiltrati.get(i).stampaDati(false);
		}
	}
	
	/**
	 * stampa tutti i films raggruppandoli per sottocategoria
	 */
	public void stampaFilms()
	{
		Vector<Film>filmDaStampare = new Vector<>();
		for(Film film : films)
		{
			if(film.isPrestabile())
			{
				filmDaStampare.add(film);
			}
		}
		
		if(filmDaStampare.isEmpty())
		{
			System.out.println("In archivio non sono presenti film disponibili");
		}
		else
		{
			if(filmDaStampare.size()==1)
			{
				System.out.println("\nE' presente un film in archivio: ");
			}
			else
			{
				System.out.println("\nSono presenti " + films.size() + " films in archivio: ");
			}
			
			for(int i = 0; i < filmDaStampare.size(); i++)
			{
//				stampa la sottocategoria solo se è il primo elemento o se il sottogenere è cambiato (sono in ordine nel vettore)
				if(i == 0 || filmDaStampare.get(i).getSottoCategoria() != filmDaStampare.get(i-1).getSottoCategoria())
				{
					System.out.println("\n" + BelleStringhe.CORNICE);	
					System.out.println(filmDaStampare.get(i).getSottoCategoria());
					System.out.println(BelleStringhe.CORNICE);
				}
				System.out.println("titolo: " + filmDaStampare.get(i).getTitolo());
			}
		}
	}
	
	/**
	 * Consente all'utente di selezionare un film in base a dei criteri di ricerca
	 * @return il film corrispondente ai criteri inseriti dall'utente
	 */
	public Film scegliFilm() 
	{
		MyMenu menuSceltaFilm = new MyMenu("\nScegli come visualizzare le risorse: ", new String[] {"Filtra ricerca", "Visualizza archivio"}, true); 
		int scelta = menuSceltaFilm.scegliBase();
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Film> FilmsFiltrati = null;
				String titoloParziale = null;
				int annoUscita = 0;
				String nomeRegista = null;
				
				MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
				int scegli = menuFiltro.scegliBase();
				switch(scegli)
				{
					case 0://INDIETRO
					{
						return null;
					}
					case 1: //FILTRA PER TITOLO
					{
						titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: \n");
						FilmsFiltrati = filtraFilmPerTitolo(titoloParziale);
						break;
					}
					case 2://FILTRA PER ANNO PUBBLICAZIONE
					{
						annoUscita = InputDati.leggiIntero("Inserisci l'anno di uscita: \n", ANNO_PRIMA_PELLICOLA, GestioneDate.ANNO_CORRENTE);
						FilmsFiltrati = filtraFilmPerUscita(annoUscita);
						break;
					}
					case 3: //FILTRA PER AUTORE
					{
						nomeRegista = InputDati.leggiStringaNonVuota("Inserisci il nome del regista:  \n");
						FilmsFiltrati = filtraFilmPerRegista(nomeRegista);
						break;
					}
				}
				
				if(!FilmsFiltrati.isEmpty())
				{
					for(int i = 0; i < FilmsFiltrati.size(); i++)
					{
						System.out.println("\n" + (i+1) + ") ");
						System.out.println(BelleStringhe.CORNICE);
						FilmsFiltrati.get(i).stampaDati(true);
						System.out.println(BelleStringhe.CORNICE);
					}
					
					int selezione;
					do
					{
						System.out.println("\n" + BelleStringhe.CORNICE);
						selezione = InputDati.leggiIntero("Seleziona il Film che vuoi ricevere in prestito (0 per annullare): ", 0, films.size());
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
							System.out.println("Tutte le copie di \"" + films.get(selezione-1).getTitolo() + "\" sono in prestito!");
						}
					}
					while(true);
				}
				else//nessuna corrispondenza: vettore vuoto
				{
					System.out.println("Nessun film corrispondente al criterio di ricerca");
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
					System.out.println("In archivio non sono presenti film disponibili");
					return null;
				}
				
				System.out.println("\nFilm in archivio: \n");
				for(int i = 0; i < filmPrestabili.size(); i++)
				{
					System.out.println(i+1 + ")");
					System.out.println(BelleStringhe.CORNICE);
					filmPrestabili.get(i).stampaDati(true);
					System.out.println(BelleStringhe.CORNICE+ "\n");		
				}
				int selezione;
				do
				{
					selezione = InputDati.leggiIntero("Seleziona il film che vuoi ricevere in prestito (0 per annullare): ", 0, filmPrestabili.size());
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
						System.out.println("Tutte le copie di \"" + filmPrestabili.get(selezione-1).getTitolo() + "\" sono in prestito!");
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * METODI PER LA RICERCA DI FILM IN BASE A DETERMINATI PARAMETRI										 *
	 * 																										 *
	 *  filtraFilmPerTitolo  -> fltra in base al titolo parziale passato dall'utente						 *
	 *  filtraFilmPerUscita  -> filtra in base all'anno di uscita immesso dall'utente						 *
	 *  filtraFilmPerRegista -> filtra in base al nome del regista passato dall'utente						 *
	 *  																									 *
	 *  ogni metodo restituisce un vector di film contenente i film che corrispondono ai parametri			 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	 
	/**
	 * filtra tutti i film in base al titolo
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public Vector<Film> filtraFilmPerTitolo(String titoloParziale)
	{
		Vector<Film> filmTrovati = new Vector<>(); 
		
		for(Film film : films)
		{
			if(film.isPrestabile() && film.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
			{
				filmTrovati.add(film);
			}
		}
		return filmTrovati;
	}
	
	/**
	 * filtra tutti i film in base all'anno di pubblicazione
	 * @param annoUscita l'anno da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public Vector<Film> filtraFilmPerUscita(int annoUscita)
	{
		Vector<Film> filmTrovati = new Vector<>(); 
		
		for(Film film : films)
		{
			if(film.isPrestabile() && film.getAnnoDiUscita() == annoUscita)
			{
				filmTrovati.add(film);
			}
		}
		return filmTrovati;
	}
	
	/**
	 * filtra tutti i film in base al regista
	 * @param regista il nome del regista da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public Vector<Film> filtraFilmPerRegista(String regista)
	{
		Vector<Film> filmTrovati = new Vector<>(); 
		for(Film film : films)
		{
			if(film.isPrestabile())
			{
				if(regista.toLowerCase().equals(regista.toLowerCase()))
				{
					filmTrovati.add(film);
				}
			}
		}
		return filmTrovati;
	}
}