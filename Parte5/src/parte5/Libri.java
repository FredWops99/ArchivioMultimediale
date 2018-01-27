package parte5;
import java.io.Serializable;
import java.util.Vector;

import myLib.MyMenu;
import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;

/**
 * Classe che rappresenta l'insieme dei libri presenti in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Libri implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * id incrementale univoco per ogni libro
	 */
	private int lastId;
	
	private static final String[] SOTTOCATEGORIE = {"Romanzo","Fumetto","Poesia"}; //le sottocategorie della categoria LIBRO (Romanzo, fumetto, poesia,...)
	private static final String[] GENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di pubblicazione", "Filtra per autore"};
	
	private Vector<Libro> libri;
	
	/**
	 * costruttore della classe: inizializza il Vector di libri
	 */
	public Libri()
	{
		this.libri = new Vector<Libro>();
		lastId = 0;
	}
	
	public Vector<Libro> getLibri() 
	{
		return libri;
	}
	public void setLibri(Vector<Libro> libri) 
	{
		this.libri = libri;
	}
	
	/**
	 * procedura per l'aggiunta di un libro alla raccolta: chiede all'utente di inserire tutti i campi necessari, crea l'oggetto Libro e lo aggiunge al vector
	 */
	public Libro addLibro()
	{
		String sottoCategoria = scegliSottoCategoria();//la sottocategoria della categoria LIBRO (Romanzo, fumetto, poesia,...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return null;
		}
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: ");
		int pagine = InputDati.leggiInteroPositivo("Inserisci il numero di pagine: ");
		int annoPubblicazione = InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: ", GestioneDate.ANNO_CORRENTE);
		String casaEditrice = InputDati.leggiStringaNonVuota("Inserisci la casa editrice: ");
		String lingua = InputDati.leggiStringaNonVuota("Inserisci la lingua del testo: ");
		Vector<String> autori = new Vector<String>();
		do
		{
			String autore = InputDati.leggiStringaNonVuota("Inserisci l'autore: ");
			autori.add(autore);
		} 
		while(InputDati.yesOrNo("ci sono altri autori? "));
		String genere = this.scegliGenere(sottoCategoria);//se la sottocategoria ha generi disponibili
		int nLicenze = InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
		
		Libro l = new Libro("L"+lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		addPerSottoCategorie(l);
		
		System.out.println("Libro aggiunto con successo!");
		return l;
	}
	
	/**
	 * presenta all'utente la scelta della sottocategoria di Libro tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
	private String scegliSottoCategoria()
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del libro: ", SOTTOCATEGORIE, true);
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
	 * inserisco i libri nel Vector in modo che siano ordinati per sottocategorie, così, quando vengono stampati, i generi sono in ordine
	 * (il metodo stampaLibri li raccoglierà per generi)
	 * @param l il libro da inserire
	 */
	private void addPerSottoCategorie(Libro l)
	{
		if(libri.isEmpty())
		{
			libri.add(l);
		}
		else
		{
			for(int i = 0; i < libri.size(); i++)
			{
				if(libri.get(i).getSottoCategoria().equals(l.getSottoCategoria()))
				{
					libri.add(i+1, l);
					return;
				}
			}
			libri.add(l);
		}
	}

	/**
	 * procedura per rimuovere un libro dalla raccolta: viene chiesto il nome del libro e se ce ne sono più di uno viene chiesto all'utente quale eliminare
	 * @return l'id del libro che l'utente ha deciso di rimuovere ("-1" se non viene rimosso nessun libro)
	 */
	public String removeLibro()
	{
		String idSelezionato;
		
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro da rimuovere: ");
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < libri.size(); i++) 
		{
			if(libri.get(i).getTitolo().equals(titolo))
			{
//				ogni volta che in libri trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			System.out.println("Siamo spiacenti, il libro non è presente nell'archivio");
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in libri
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = libri.get((int)posizioniRicorrenze.get(0)).getId();
			libri.remove((int)posizioniRicorrenze.get(0));
			System.out.println("Rimozione avvenuta con successo!");
		}
//		se ci sono più elementi nel vettore (più libri con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in libri di quell'elemento e lo rimuovo da libri
		else
		{
			System.out.println("Sono presenti più libri dal titolo \"" + titolo + "\": ");
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				System.out.println("\nRicorrenza " + ++pos + ":");
				System.out.println(BelleStringhe.CORNICE);
				libri.elementAt((int)i).stampaDati(false);
				System.out.println(BelleStringhe.CORNICE);
			}
			
			int daRimuovere = InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
			if(daRimuovere > 0)
			{
				idSelezionato = libri.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				libri.remove((int)posizioniRicorrenze.get(daRimuovere-1));
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
	 * stampa i dati dei libri corrispondenti ai parametri di ricerca specificati dall'utente
	 */
	public void cercaLibro()
	{
		Vector<Libro> libriFiltrati = null;
		String titoloParziale = null;
		int annoPubblicazione = 0;
		String nomeAutore = null;
		
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
				titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: \n");
				libriFiltrati = filtraLibriPerTitolo(titoloParziale);
				break;
			}
			
			case 2:	//FILTRA PER ANNO DI PUBBLICAZIONE
			{
				annoPubblicazione = InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: \n", GestioneDate.ANNO_CORRENTE);
				libriFiltrati = filtraLibriPerData(annoPubblicazione);
				break;
			}
			
			case 3: //FILTRA PER AUTORE
			{
				nomeAutore = InputDati.leggiStringaNonVuota("Inserisci il nome dell'autore:  \n");
				libriFiltrati = filtraLibriPerAutori(nomeAutore);
				break;
			}
		}
		
		if(scelta == 1 && libriFiltrati.isEmpty()) 
		{
			System.out.println("In archivio non sono presenti libri il cui titolo è " + titoloParziale);
			return;
		}
		if(scelta == 2 && libriFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoPubblicazione);
			return;
		}
		if(scelta == 3 && libriFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
			return;
		}
		
		for (int i=0; i <libriFiltrati.size(); i++) 
		{
			System.out.println();
			System.out.println(BelleStringhe.CORNICE);
			libriFiltrati.get(i).stampaDati(false);
		}
	}
	
	/**
	 * stampa tutti i libri raggruppandoli per sottocategoria e genere
	 */
	public void stampaLibri()
	{
		if(libri.isEmpty())
		{
			System.out.println("Nessun libro presente");
		}
		else
		{
			if(libri.size()==1)
			{
				System.out.println("\nE' presente " + libri.size() + " libro in archivio: ");
			}
			else//piu di un libro in archivio
			{
				System.out.println("\nSono presenti " + libri.size() + " libri in archivio: ");

			}
			
//			uso "libriDaStampare" così quando stampo un libro nella sua categoria posso eliminarlo e non stamparlo di nuovo dopo
			Vector<Libro> libriDaStampare = new Vector<>();
			for(int i = 0; i < libri.size(); i++)
			{
				libriDaStampare.add(libri.get(i));
			}
			
			for(int j = 0; j < libriDaStampare.size(); j++)
			{				
				System.out.println("\n" + BelleStringhe.CORNICE);
				if(! libriDaStampare.get(j).getGenere().equals("-"))
				{
					System.out.println("Sottocategoria: " + libriDaStampare.get(j).getSottoCategoria() + 
							", genere: "  + libriDaStampare.get(j).getGenere() + "\n" + BelleStringhe.CORNICE);
					System.out.println("titolo: " + libriDaStampare.get(j).getTitolo());
					
					for (int i = j+1; i < libriDaStampare.size(); i++) 
					{
						if(libriDaStampare.get(j).getSottoCategoria().equals(libriDaStampare.get(i).getSottoCategoria()))
						{
							if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
							{
								System.out.println("titolo: " + libriDaStampare.get(i).getTitolo());
								libriDaStampare.remove(i);
							}
						}
					}
				}
				else
				{
					System.out.println("Sottocategoria: " + libriDaStampare.get(j).getSottoCategoria() + "\n" + BelleStringhe.CORNICE);
					System.out.println("titolo: " + libriDaStampare.get(j).getTitolo());
					for (int i=j+1;i<libriDaStampare.size();i++) 
					{
						if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
						{
								System.out.println("titolo: " + libriDaStampare.get(i).getTitolo());
								libriDaStampare.remove(i);
						}
					}
				}
			}
		}
	}
	
	/**
	 * se la sottocategoria di libro ne prevede, presenta all'utente la scelta del genere del libro tra quelli presenti in elenco.
	 * se la sottocategoria non ne prevede restituisce un simbolo di default
	 * @param sottoCategoria la sottocategoria di libro che l'utente sta inserendo
	 * @return la scelta dell'utente o "-" se la sottocategoria non prevede generi
	 */
	private String scegliGenere(String sottoCategoria)
	{
		if(sottoCategoria.equals("Romanzo") || sottoCategoria.equals("Fumetto")) //se si aggiunge un genere va aggiunto anche qui !
		{
			MyMenu menu = new MyMenu("scegli un genere: ", GENERI);
			return GENERI[menu.scegliNoIndietro() - 1];	
		}
		else
		{
			return "-";
		}
	}

	/**
	 * Consente all'utente di selezionare un libro in base a dei criteri di ricerca
	 * @return il libro corrispondente ali criteri inseriti dall'utente
	 */
	public Libro scegliLibro() 
	{
		MyMenu menuSceltaLibro = new MyMenu("\nScegli come visualizzare le risorse: ", new String[] {"Filtra ricerca", "Visualizza archivio"}, true); 
		int scelta = menuSceltaLibro.scegliBase();
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Libro> libriFiltrati = null;
				String titoloParziale = null;
				int annoPubblicazione = 0;
				String nomeAutore = null;
				
				MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
				int scegli = menuFiltro.scegliBase();
				switch(scegli)
				{
					case 0:
					{
						return null;
					}
					case 1: //FILTRA PER TITOLO
					{
						titoloParziale = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: \n");
						libriFiltrati = filtraLibriPerTitolo(titoloParziale);
						break;
					}
					
					case 2://FILTRA PER ANNO PUBBLICAZIONE
					{
						annoPubblicazione = InputDati.leggiIntero("Inserisci l'anno di pubblicazione: \n");
						libriFiltrati = filtraLibriPerData(annoPubblicazione);
						break;
					}
					
					case 3: //FILTRA PER AUTORE
					{
						nomeAutore = InputDati.leggiStringaNonVuota("Inserisci il nome dell'autore:  \n");
						libriFiltrati = filtraLibriPerAutori(nomeAutore);
						break;
					}
				}
				
				if(!libriFiltrati.isEmpty())
				{
					for(int i = 0; i < libriFiltrati.size(); i++)
					{
						System.out.println("\n" + (i+1) + ") ");
						System.out.println(BelleStringhe.CORNICE);
						libriFiltrati.get(i).stampaDati(true);
						System.out.println(BelleStringhe.CORNICE);
					}
					
					int selezione;
					do
					{
						System.out.println("\n" + BelleStringhe.CORNICE);
						selezione = InputDati.leggiIntero("Seleziona il libro che vuoi ricevere in prestito (0 per annullare): ", 0, libri.size());
						if(selezione == 0)
						{
							return null;
						}
						else if(libri.get(selezione-1).getInPrestito() < libri.get(selezione-1).getnLicenze())
						{
							return libri.get(selezione-1);
						}
						else
						{
							System.out.println("Tutte le copie di \"" + libri.get(selezione-1).getTitolo() + "\" sono in prestito!");
						}
					}
					while(true);
				}
				else//nessuna corrispondenza: vettore vuoto
				{
					System.out.println("Nessun libro corrispondente al criterio di ricerca");
					return null;
				}
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				if(libri.isEmpty())
				{
					System.out.println("Non sono presenti libri in archivio");
					return null;
				}
				System.out.println("\nLibri in archivio: \n");
				for(int i = 0; i < libri.size(); i++)
				{
					System.out.println(i+1 + ")");
					System.out.println(BelleStringhe.CORNICE);
					libri.get(i).stampaDati(true);
					System.out.println(BelleStringhe.CORNICE+ "\n");		
				}
				int selezione;
				do
				{
					selezione = InputDati.leggiIntero("Seleziona il libro che vuoi ricevere in prestito (0 per annullare): ", 0, libri.size());
					if(selezione == 0)
					{
						return null;
					}
					else if(libri.get(selezione-1).getInPrestito() < libri.get(selezione-1).getnLicenze())
					{
						return libri.get(selezione-1);
					}
					else
					{
						System.out.println("Tutte le copie di \"" + libri.get(selezione-1).getTitolo() + "\" sono in prestito!");
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * METODI PER LA RICERCA DI LIBRI IN BASE A DETERMINATI PARAMETRI										 *
	 * 																										 *
	 *  filtraLibriPerTitolo  -> fltra in base al titolo parziale passato dall'utente						 *
	 *  filtraLibriPerData    -> filtra in base all'anno di uscita immesso dall'utente						 *
	 *  filtraLibroPerAutori  -> filtra in base al nome dell'autore passato dall'utente						 *
	 *  																									 *
	 *  ogni metodo restituisce un vector di libri contenente i libri che corrispondono ai parametri		 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * filtra tutti i libri in base al titolo
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerTitolo(String titoloParziale)
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		
		for(Libro libro : libri)
		{
			if(libro.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
			{
				libriTrovati.add(libro);
			}
		}
		return libriTrovati;
	}
	
	/**
	 * filtra tutti i libri in base all'anno di pubblicazione
	 * @param annoPubblicazione l'anno da usare come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerData(int annoPubblicazione)
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		
		for(Libro libro : libri)
		{
			if(libro.getAnnoPubblicazione() == annoPubblicazione)
			{
				libriTrovati.add(libro);
			}
		}
		return libriTrovati;
	}
	
	/**
	 * filtra tutti i libri in base all'autore
	 * @param autore il nome dell'autore da usare come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerAutori(String autore)
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		for(Libro libro : libri)
		{
			for(String s : libro.getAutori())
			{
				if(s.toLowerCase().equals(autore.toLowerCase()))
				{
					libriTrovati.add(libro);
				}
			}
		}
		return libriTrovati;
	}
}
