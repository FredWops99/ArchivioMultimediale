package parte3;

import java.io.Serializable;
import java.util.Vector;

import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

public class Films implements Serializable
{

	private static final long serialVersionUID = 1L;
//	a ogni Film viene assegnato un ID incrementale univoco
	private int lastId;
	
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {	"Filtra per titolo",
																"Filtra per anno di uscita",
																"Filtra per regista"};
	
	private Vector<Film> films;
	
	/**
	 * costruttore della classe: inizializza il Vector di Films
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
	
	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del film: ");
		int durata = InputDati.leggiInteroPositivo("Inserisci la durata del film: ");
		int annoDiUscita = InputDati.leggiInteroConMassimo("Inserisci l'anno di uscita: ", GestioneDate.ANNO_CORRENTE);
		String lingua = InputDati.leggiStringaNonVuota("Inserisci la lingua del film: ");
		String regista = InputDati.leggiStringaNonVuota("Inserisci il regista: ");	
		int nLicenze = InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
		
		Film f = new Film(lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
		System.out.println("Film aggiunto con successo!");
	}
	
	private String scegliSottoCategoria()
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del film: ", SOTTOCATEGORIE);
		return SOTTOCATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
	}
	
	
	public int removeLibro()
	{
		int idSelezionato;
		
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del film da rimuovere: ");
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < films.size(); i++) 
		{
			if(films.get(i).getNome().equals(titolo))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			System.out.println("Siamo spiacenti, il film non è presente nell'archivio");
			idSelezionato = -1;
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.remove((int)posizioniRicorrenze.get(0));
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
				films.remove((int)posizioniRicorrenze.get(daRimuovere-1));
				System.out.println("Rimozione avvenuta con successo!");
			}
			else//0: annulla
			{
				idSelezionato = -1;
			}
		}
		return idSelezionato;
	}
	
	public void cercaFilm()
	{
		Vector<Film> filmsFiltrati = null;
		String titoloParziale = null;
		int annoUscita = 0;
		String nomeRegista = null;
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO); 
		int scelta = menuFiltro.scegliBase();
		switch(scelta) 
		{
			case 1: 
			{
				titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del film: \n");
				filmsFiltrati = filtraFilmPerTitolo(titoloParziale);
				break;
			}
			
			case 2:
			{
				annoUscita = InputDati.leggiIntero("Inserisci l'anno di uscita: \n");
				filmsFiltrati = filtraFilmPerUscita(annoUscita);
				break;
			}
			
			case 3: 
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
	
	public void stampaFilms()
	{
		if(films.isEmpty())
		{
			System.out.println("Nessun film presente");
		}
		else
		{
			if(films.size()==1)
			{
				System.out.println("\nE' presente " + films.size() + " films in archivio: ");
			}
			else
			{
				System.out.println("\nSono presenti " + films.size() + " films in archivio: ");

			}
			
			for(int j = 0; j < films.size(); j++)
			{				
				System.out.println("\n" + BelleStringhe.CORNICE);	
				System.out.println("Sottocategoria: " + films.get(j).getSottoCategoria() + "\n" + BelleStringhe.CORNICE);
				System.out.println("titolo: " + films.get(j).getNome());
			}
		}
	}
		

	
	private void addPerSottoCategorie(Film l)
	{
		if(films.isEmpty())
		{
			films.add(l);
		}
		else
		{
			for(int i = 0; i < films.size(); i++)
			{
				if(films.get(i).getSottoCategoria().equals(l.getSottoCategoria()))
				{
					films.add(i+1, l);
					return;
				}
			}
			films.add(l);
		}
	}
	
	
	public Film scegliFilm() 
	{
		MyMenu menuSceltaFilm = new MyMenu("\nScegli come visualizzare i risultati: ", new String[] {"Filtra ricerca", "Visualizza archivio"}, true); 
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
				
				MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO); 
				int scegli = menuFiltro.scegliBase();
				switch(scegli)
				{
					case 1: //FILTRA PER TITOLO
					{
						titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: \n");
						FilmsFiltrati = filtraFilmPerTitolo(titoloParziale);
						break;
					}
					
					case 2://FILTRA PER ANNO PUBBLICAZIONE
					{
						annoUscita = InputDati.leggiIntero("Inserisci l'anno di uscita: \n");
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
							System.out.println("Tutte le copie di \"" + films.get(selezione-1).getNome() + "\" sono in prestito!");
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
				System.out.println("\nFilm in archivio: \n");
				for(int i = 0; i < films.size(); i++)
				{
					System.out.println(i+1 + ")");
					System.out.println(BelleStringhe.CORNICE);
					films.get(i).stampaDati(true);
					System.out.println(BelleStringhe.CORNICE+ "\n");		
				}
				int selezione;
				do
				{
					selezione = InputDati.leggiIntero("Seleziona il film che vuoi ricevere in prestito (0 per annullare): ", 0, films.size());
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
						System.out.println("Tutte le copie di \"" + films.get(selezione-1).getNome() + "\" sono in prestito!");
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * METODI PER LA RICERCA DI LIBRI IN BASE A DETERMINATI PARAMETRI
	 * 
	 *  filtraLibroPerTitolo  -> fltra in base al titolo parziale passato dall'utente
	 *  filtraFilmPerUscita    -> filtra in base all'anno di uscita immesso dall'utente
	 *  filtraFilmPerRegista  -> filtra in base al nome del regista che gli viene passato dall'utente
	 *  
	 *  ogni metodo restituisce un vector di libri contenente i libri che rispondono a determinati parametri
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */ 
	
		public Vector<Film> filtraFilmPerTitolo(String titoloParziale)
		{
			Vector<Film> filmTrovati = new Vector<>(); 
			
			for(Film film : films)
			{
				if(film.getNome().toLowerCase().contains(titoloParziale.toLowerCase()))
				{
					filmTrovati.add(film);
				}
			}
			return filmTrovati;
		}
	
		public Vector<Film> filtraFilmPerUscita(int annoUscita)
		{
			Vector<Film> filmTrovati = new Vector<>(); 
			
			for(Film film : films)
			{
				if(film.getAnnoDiUscita() == annoUscita)
				{
					filmTrovati.add(film);
				}
			}
			return filmTrovati;
		}
	
		public Vector<Film> filtraFilmPerRegista(String regista)
		{
			Vector<Film> filmTrovati = new Vector<>(); 
			for(Film film : films)
			{
					if(regista.toLowerCase().equals(regista.toLowerCase()))
					{
						filmTrovati.add(film);
					}
			}
			return filmTrovati;
		}
}