package parte3;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

/**
 * Classe che rappresenta l'insieme dei libri presenti in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Libri implements Serializable
{
	private static final long serialVersionUID = 1L;
//	a ogni libro viene assegnato un ID incrementale univoco
	private int lastId;
	
	private static final String[] SOTTOCATEGORIE = {"Romanzo","Fumetto","Poesia"}; //le sottocategorie della categoria LIBRO (Romanzo, fumetto, poesia,...)
	private static final String[] GENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {	"Filtra per titolo",
																"Filtra per anno di pubblicazione",
																"Filtra per autore"};
	
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
	public void addLibro()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria LIBRO (Romanzo, fumetto, poesia,...)
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
		
		Libro l = new Libro(lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		addPerSottoCategorie(l);
		
		System.out.println("Libro aggiunto con successo!");
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
	 */
	public void removeLibro()
	{
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro da rimuovere: ");
		
		Vector<Integer> posizioni = new Vector<>();
		
		for (int i = 0; i < libri.size(); i++) 
		{
			if(libri.get(i).getNome().equals(titolo))
			{
				posizioni.add(i);
			}
		}
		if(posizioni.size()==0)
		{
			System.out.println("Siamo spiacenti, il libro non è presente nell'archivio");
		}
		else if(posizioni.size()==1)
		{
			libri.remove((int)posizioni.get(0));
			System.out.println("Rimozione avvenuta con successo!");
		}
		else//Più elementi con lo stesso nome
		{
			System.out.println("Sono presenti più libri dal titolo \"" + titolo + "\": ");
			int pos = 0;
			for(Integer i : posizioni)
			{
				System.out.println("\nRicorrenza " + ++pos + ":");
				libri.elementAt((int)i).stampaDati(false);
			}
			
			int daRimuovere = InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioni.size());
			if(daRimuovere > 0)
			{
				libri.remove(daRimuovere-1);
				System.out.println("Rimozione avvenuta con successo!");
			}
		}	
	}
	
	/**
	 * chiede all'utente il nome del libro del quale visualizzare tutte le informazioni: se ce ne sono più uno con lo stesso stampa i dettagli di questi ultimi
	 */
	public void dettagliLibro()
	{
		Vector<Libro> libriFiltrati = null;
		String titoloParziale = null;
		int annoPubblicazione = 0;
		String nomeAutore = null;
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO); 
		int scelta = menuFiltro.scegliBase();
		switch(scelta) 
		{
			case 1: 
			{
				titoloParziale = InputDati.leggiStringa("Inserisci il titolo del libro: \n");
				libriFiltrati = filtraLibroPerTitolo(titoloParziale);
				break;
			}
			
			case 2:
			{
				annoPubblicazione = InputDati.leggiIntero("Inserisci l'anno di pubblicazione: \n");
				libriFiltrati = filtraLibroPerData(annoPubblicazione);
				break;
			}
			
			case 3: 
			{
				nomeAutore = InputDati.leggiStringa("Inserisci il nome dell'autore:  \n");
				libriFiltrati = filtraLibroPerAutori(nomeAutore);
				break;
			}
		}
		
		if(scelta ==1 && libriFiltrati.isEmpty()) 
		{
			System.out.println("In archivio non sono presenti libri il cui titolo è " + titoloParziale);
			return;
		}
		if(scelta ==2 && libriFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoPubblicazione);
			return;
		}
		if(scelta ==3 && libriFiltrati.isEmpty())
		{
			System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
			return;
		}
		
		for (int i=0;i <libriFiltrati.size();i++) 
		{
			System.out.println();
			libriFiltrati.get(i).stampaDati(false);
		}
		/* codice modificato per la ricerca con diversi parametri**/
		
		/*String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: ");
		int numLibri = 0;
		
		for(Libro libro : libri)
		{
			if(libro.getNome().equals(titolo))
			{
				System.out.println();
				libro.stampaDati(false);
				numLibri++;
			}
		}
		if(numLibri == 0)
		{
			System.out.println("In archivio non sono presenti libri il cui titolo è " + titolo);
		}
		*/
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
				System.out.println("\n---------------------- ");
				if(! libriDaStampare.get(j).getGenere().equals("-"))
				{
					System.out.println("Sottocategoria: " + libriDaStampare.get(j).getSottoCategoria() + 
							", genere: "  + libriDaStampare.get(j).getGenere() + "\n");
					System.out.println("titolo: " + libriDaStampare.get(j).getNome());
					
					for (int i = j+1; i < libriDaStampare.size(); i++) 
					{
						if(libriDaStampare.get(j).getSottoCategoria().equals(libriDaStampare.get(i).getSottoCategoria()))
						{
							if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
							{
								System.out.println("titolo: " + libriDaStampare.get(i).getNome());
								libriDaStampare.remove(i);
							}
						}
					}
				}
				else
				{
					System.out.println("Sottocategoria: " + libriDaStampare.get(j).getSottoCategoria() + "\n");
					System.out.println("titolo: " + libriDaStampare.get(j).getNome());
					for (int i=j+1;i<libriDaStampare.size();i++) 
					{
						if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
						{
								System.out.println("titolo: " + libriDaStampare.get(i).getNome());
								libriDaStampare.remove(i);
						}
					}
				}
			}
		}
	}
	
	/**
	 * presenta all'utente la scelta della sottocategoria di Libro tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
	private String scegliSottoCategoria()
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del libro: ", SOTTOCATEGORIE);
		return SOTTOCATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
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
			return GENERI[menu.scegliBase() - 1];	
		}
		else
		{
			return "-";
		}
	}

	public Libro scegliLibro() 
	{
		int selezione;
		
		System.out.println("\nLibri in archivio: \n");
		for(int i = 0; i < libri.size(); i++)
		{
			System.out.println(i+1 + ")");
			libri.get(i).stampaDati(true);
			System.out.println(BelleStringhe.CORNICE+ "\n");		
		}
		
		do
		{
			selezione = InputDati.leggiIntero("Seleziona il libro che vuoi ricevere in prestito (0 per annullare): ", 0, libri.size());
			if(selezione == 0)
			{
				return null;
			}
			else if(libri.get(selezione-1).getInPrestito() < libri.get(selezione-1).getnLicenze())
			{
				System.out.println(libri.get(selezione-1).getNome() + " selezionato!");
				return libri.get(selezione-1);
			}
			else
			{
				System.out.println("Tutte le copie di \"" + libri.get(selezione-1).getNome() + "\" sono in prestito!");
			}
		}
		while(true);
	}
	
	
	/** * * * * * * * * * * * * * * * * * * * * * * * *
	 * MEDOTO PER LA RICERCA DI LIBRI IN BASE A DETERMINATI PARAMETRI
	 * 
	 *  filtraLibroPerTitolo  -> fltra in base al titolo parziale passato dall'utente
	 *  filtraLibroPerData    -> filtra in base all'anno di pubblicazione immesso dall'utente
	 *  filtraLibroPerAutori  -> filtra in base al nome dell'autore che gli viene passato dall'utente
	 *  
	 *  ogni metodo restituisce un vector di libri contenente i libri che rispondono a determinati parametri
	 *  * * * * * * * * * * * * * * * * * * * * * * * *
	 */ 
	
	public Vector<Libro> filtraLibroPerTitolo(String titoloParziale)
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		
		for(Libro libro : libri)
		{
			if(libro.getNome().contains(titoloParziale))
			{
				libriTrovati.add(libro);
			}
		}
		return libriTrovati;
	}
	
	public Vector<Libro> filtraLibroPerData(int annoPubblicazione)
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
	
	public Vector<Libro> filtraLibroPerAutori(String autore)
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		for(Libro libro : libri)
		{
			for(String s: libro.getAutori())
			{
			if(s.equals(autore))
				{
					libriTrovati.add(libro);
				}
			}
		}
		return libriTrovati;
	}
}
