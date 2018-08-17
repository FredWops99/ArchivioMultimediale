package model;

import java.io.Serializable;
import java.util.Vector;

import menus.risorse.libri.MenuSottoCategoriaLibri;
import menus.risorse.libri.MenuFiltroLibri;
import model.Libro;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;
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
	
	private static final String[] GENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di pubblicazione", "Filtra per autore"};
	
	private static final long serialVersionUID = 1L;
	/**
	 * id incrementale univoco per ogni libro
	 */
	private int lastId;
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
	 * procedura per l'aggiunta di un libro alla raccolta: chiede all'utente di inserire 
	 * tutti i campi necessari, crea l'oggetto Libro e lo aggiunge al vector
	 */
	public void addLibro()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria LIBRO (Romanzo, fumetto, poesia,...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}
		String genere = this.scegliGenere(sottoCategoria);//se la sottocategoria ha generi disponibili
		
		String titolo = LibriView.chiediTitolo();
		int pagine = LibriView.chiediPagine();
		int annoPubblicazione = LibriView.chiediAnnoPubblicazione();
		String casaEditrice = LibriView.chiediCasaEditrice();
		String lingua = LibriView.chiediLingua();
		Vector<String> autori = new Vector<String>();
		do
		{
			String autore = LibriView.chiediAutore();
			autori.add(autore);
		} 
		while(LibriView.ciSonoAltriAutori());
		
		int nLicenze = LibriView.chiediNumeroLicenze();
		
		Libro l = new Libro("L"+lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		
		if(!libroEsistente(l))
		{
			addPerSottoCategorie(l);
			LibriView.aggiuntaRiuscita();
		}
		
		else
		{
			LibriView.aggiuntaNonRiuscita();
		}
	}
	
	/**
	 * presenta all'utente la scelta della sottocategoria di Libro tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
	private String scegliSottoCategoria()
	{
		String sottocategoria = MenuSottoCategoriaLibri.show();
		
		return sottocategoria;
	}
	
	/**
	 * inserisco i libri nel Vector in modo che siano ordinati per sottocategorie, così, quando vengono stampati, i generi sono in ordine
	 * (il metodo stampaLibri li raccoglierà per generi)
	 * (precondizione: l != null)
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
	 * indica se il libro è già presente in archivio
	 * (precondizione: l != null)
	 * @param l il libro da cercare
	 * @return true se il libro è presente in archivio
	 */
	private boolean libroEsistente(Libro l) 
	{
		for(Libro libro : libri)
		{
			if(l.getTitolo().equals(libro.getTitolo()) && l.getAutori().equals(libro.getAutori()) && l.getAnnoPubblicazione()==libro.getAnnoPubblicazione()
					&& l.getCasaEditrice().equals(libro.getCasaEditrice()) && l.getGenere().equals(libro.getGenere()) && l.getLingua().equals(libro.getLingua())
					&& l.getSottoCategoria().equals(libro.getSottoCategoria()) && l.getPagine()==libro.getPagine()
					&& libro.isPrestabile())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permette la rimozione di un libro da parte dell'operatore: il libro selezionato verrà etichettato come "non Prestabile" ma rimarrà in archivio per 
	 * motivi storici
	 * @return l'id del libro rimosso
	 */
	public String removeLibro()
	{
		String idSelezionato;
		
		String titolo = LibriView.chiediLibroDaRimuovere();
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < libri.size(); i++)
		{
			if(libri.get(i).isPrestabile() && libri.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in libri trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			LibriView.libroNonPresente();
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in libri
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = libri.get((int)posizioniRicorrenze.get(0)).getId();
			libri.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			LibriView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore (più libri con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quello che vuole rimuovere
		else
		{
			LibriView.piùLibriStessoTitolo(titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				LibriView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				libri.elementAt((int)i).stampaDati(false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = LibriView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze);
			
			if(daRimuovere > 0)
			{
				idSelezionato = libri.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				libri.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				LibriView.rimozioneAvvenuta();
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
		MenuFiltroLibri.show(libri);
	}
	/**
	 * stampa tutti i libri raggruppandoli per sottocategoria e genere
	 */
	public void stampaLibri()
	{
//		uso "libriDaStampare" così quando stampo un libro nella sua categoria posso eliminarlo e non stamparlo di nuovo dopo
		Vector<Libro> libriDaStampare = new Vector<>();
		for(Libro libro : libri)
		{
			if(libro.isPrestabile())
			{
				libriDaStampare.add(libro);
			}
		}
		if(libriDaStampare.size() == 0)
		{
			System.out.println("In archivio non sono presenti libri disponibili");
			return;
		}
		
		if(libriDaStampare.size() == 1)
		{
			System.out.println("\nE' presente un libro in archivio: ");
		}
		else//piu di un libro prestabile in archivio
		{
			System.out.println("\nSono presenti " + libri.size() + " libri in archivio: ");
		}
		for(int j = 0; j < libriDaStampare.size(); j++)
		{				
			System.out.println("\n" + BelleStringhe.CORNICE);
			if(!libriDaStampare.get(j).getGenere().equals("-"))
			{
				System.out.println("Sottocategoria: " + libriDaStampare.get(j).getSottoCategoria() + 
						", genere: "  + libriDaStampare.get(j).getGenere() + "\n" + BelleStringhe.CORNICE);
				System.out.println("titolo: " + libriDaStampare.get(j).getTitolo());
//				conteggio al contrario così quando elimino un elemento non salto il successivo
				for(int i = libriDaStampare.size()-1; i >= j+1; i--) 
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
//				conteggio al contrario così quando elimino un elemento non salto il successivo
				for(int i = libriDaStampare.size()-1; i >= j+1; i--)
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
	
	/**
	 * se la sottocategoria di libro ne prevede, presenta all'utente la scelta del genere del libro tra quelli presenti in elenco.
	 * se la sottocategoria non ne prevede restituisce un simbolo di default
	 * (precondizione: sottoCategoria != null)
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
						libriFiltrati = MenuFiltroLibri.filtraLibriPerTitolo(titoloParziale, libriFiltrati);
						break;
					}
					case 2://FILTRA PER ANNO PUBBLICAZIONE
					{
						annoPubblicazione = InputDati.leggiIntero("Inserisci l'anno di pubblicazione: \n");
						libriFiltrati = MenuFiltroLibri.filtraLibriPerData(annoPubblicazione, libriFiltrati);
						break;
					}
					case 3: //FILTRA PER AUTORE
					{
						nomeAutore = InputDati.leggiStringaNonVuota("Inserisci il nome dell'autore:  \n");
						libriFiltrati = MenuFiltroLibri.filtraLibriPerAutori(nomeAutore, libriFiltrati);
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
				Vector<Libro>libriPrestabili = new Vector<>();
				for(Libro libro : libri)
				{
					if(libro.isPrestabile())
					{
						libriPrestabili.add(libro);
					}
				}
				if(libriPrestabili.isEmpty())
				{
					System.out.println("In archivio non sono presenti libri disponibili");
					return null;
				}
				
				System.out.println("\nLibri in archivio: \n");
				for(int i = 0; i < libriPrestabili.size(); i++)
				{
					System.out.println(i+1 + ")");
					System.out.println(BelleStringhe.CORNICE);
					libriPrestabili.get(i).stampaDati(true);
					System.out.println(BelleStringhe.CORNICE+ "\n");		
				}
				int selezione;
				do
				{
					selezione = InputDati.leggiIntero("Seleziona il libro che vuoi ricevere in prestito (0 per annullare): ", 0, libriPrestabili.size());
					if(selezione == 0)
					{
						return null;
					}
					else if(libriPrestabili.get(selezione-1).getInPrestito() < libriPrestabili.get(selezione-1).getnLicenze())
					{
						return libriPrestabili.get(selezione-1);
					}
					else
					{
						System.out.println("Tutte le copie di \"" + libriPrestabili.get(selezione-1).getTitolo() + "\" sono in prestito!");
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}

}
