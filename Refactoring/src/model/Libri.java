package model;

import java.io.Serializable;
import java.util.Vector;
import menus.risorse.libri.MenuSottoCategoriaLibri;
import menus.risorse.libri.MenuFiltroLibri;
import menus.risorse.libri.MenuScegliGenereLibro;
import menus.risorse.libri.MenuScegliLibro;
import model.Libro;
import view.LibriView;
import view.MessaggiSistemaView;

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
		String genere = scegliGenere(sottoCategoria);//se la sottocategoria ha generi disponibili
		String titolo = LibriView.chiediTitolo(Libro.class);
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
			LibriView.aggiuntaRiuscita(Libro.class);
		}
		
		else
		{
			LibriView.aggiuntaNonRiuscita(Libro.class);
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
		
		String titolo = LibriView.chiediRisorsaDaRimuovere(Libro.class);
		
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
			LibriView.risorsaNonPresente(Libro.class);
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
			LibriView.piùRisorseStessoTitolo(Libri.class, titolo);
			
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
		MenuFiltroLibri.show(libri,true);
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
		LibriView.stampaDati(libriDaStampare);
		
		
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
		return MenuScegliGenereLibro.show(sottoCategoria);
	}

	/**
	 * Consente all'utente di selezionare un libro in base a dei criteri di ricerca
	 * @return il libro corrispondente ali criteri inseriti dall'utente
	 */
	public Libro scegliLibro() 
	{
		Libro libroSelezionato = MenuScegliLibro.show(libri);
		return libroSelezionato;
	}
}
