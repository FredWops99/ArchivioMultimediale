package model;

import java.io.Serializable;
import java.util.Vector;
import model.Libro;

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
	
	public int getLastId()
	{
		return lastId;
	}
	
	public boolean addLibro(Libro l)
	{
		if(!libroEsistente(l))
		{
			addPerSottoCategorie(l);
			return true;
		}
		
		else
		{
			return false;
		}	
	}
	
	/**
	 * procedura per l'aggiunta di un libro alla raccolta: chiede all'utente di inserire 
	 * tutti i campi necessari, crea l'oggetto Libro e lo aggiunge al vector
	 */
//	public void addLibro()
//	{
//		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria LIBRO (Romanzo, fumetto, poesia,...)
////		se l'utente annulla la procedura
//		if(sottoCategoria == "annulla")
//		{
//			return;
//		}
//		String genere = scegliGenere(sottoCategoria);//se la sottocategoria ha generi disponibili
//		String titolo = LibriView.chiediTitolo(Libro.class);
//		int pagine = LibriView.chiediPagine();
//		int annoPubblicazione = LibriView.chiediAnnoPubblicazione();
//		String casaEditrice = LibriView.chiediCasaEditrice();
//		String lingua = LibriView.chiediLingua();
//		Vector<String> autori = new Vector<String>();
//		do
//		{
//			String autore = LibriView.chiediAutore();
//			autori.add(autore);
//		} 
//		while(LibriView.ciSonoAltriAutori());
//		
//		int nLicenze = LibriView.chiediNumeroLicenze();
//		
//		Libro l = new Libro("L"+lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
//		
//		if(!libroEsistente(l))
//		{
//			addPerSottoCategorie(l);
//			LibriView.aggiuntaRiuscita(Libro.class);
//		}
//		
//		else
//		{
//			LibriView.aggiuntaNonRiuscita(Libro.class);
//		}
//	}
	
//	/**
//	 * presenta all'utente la scelta della sottocategoria di Libro tra quelle presenti in elenco
//	 * @return la scelta dell'utente
//	 */
//	private String scegliSottoCategoria()
//	{
//		String sottocategoria = MenuSottoCategoriaLibri.show();
//		
//		return sottocategoria;
//	}
//	
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
	public boolean libroEsistente(Libro l) 
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
	
	/** filtra tutti i libri in base al titolo
	  * (precondizione: titoloParziale != null)
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerTitolo(String titoloParziale) 
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		
		for(Libro libro : libri)
		{
			if(libro.isPrestabile() && libro.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
			{
				libriTrovati.add(libro);
			}
		}
		return libriTrovati;
	}
	
	/**
	 * filtra tutti i libri in base all'anno di pubblicazione
	 * (precondizione: annoPubblicazione != null)
	 * @param annoPubblicazione l'anno da usare come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerAnnoPubblicazione(int annoPubblicazione) 
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		
		for(Libro libro : libri)
		{
			if(libro.isPrestabile() && libro.getAnnoPubblicazione() == annoPubblicazione)
			{
				libriTrovati.add(libro);
			}
		}
		return libriTrovati;
	}
	
	/**
	 * filtra tutti i libri in base all'autore
	 * (precondizione: autore != null)
	 * @param autore il nome dell'autore da usare come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Libro> filtraLibriPerAutori(String autore) 
	{
		Vector<Libro> libriTrovati = new Vector<>(); 
		for(Libro libro : libri)
		{
			if(libro.isPrestabile())
			{
				for(String s : libro.getAutori())
				{
					if(s.toLowerCase().equals(autore.toLowerCase()))
					{
						libriTrovati.add(libro);
					}
				}
			}
		}
		return libriTrovati;
	}
}
