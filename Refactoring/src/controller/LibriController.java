package controller;

import java.util.Vector;
import menus.risorse.libri.MenuFiltroLibri;
import menus.risorse.libri.MenuScegliGenereLibro;
import menus.risorse.libri.MenuScegliLibro;
import menus.risorse.libri.MenuSottoCategoriaLibri;
import model.Libri;
import model.Libro;
import view.LibriView;
import view.MessaggiSistemaView;

/**
 * CONTROLLER interagisce con view (quindi anche menu?) e modifica il MODEL
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class LibriController 
{
	private Libri model;
	private int lastId;	
	
	public LibriController(Libri libri)
	{
		this.model = libri;
		this.lastId = libri.getLastId();
	}
	
	public Libri getModel() 
	{
		return model;
	}
	
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
		
		boolean aggiuntaRiuscita = model.addLibro(l);
		
		if(aggiuntaRiuscita)
		{
			LibriView.aggiuntaRiuscita(Libro.class);
			
//			il main salva su file quell'archivio, nella classe MenuOperatore
//			non c'è bisogno di settare l'archivio del main perchè è lo stesso riferimento
//			Main.setArchivio(model);
		}
		else
		{
			LibriView.aggiuntaNonRiuscita(Libro.class);
		}
	}
	
	/**
	 * permette la rimozione di un libro da parte dell'operatore: il libro selezionato verrà etichettato come "non Prestabile" ma rimarrà in archivio per 
	 * motivi storici
	 * @return l'id del libro rimosso
	 */
	public String removeLibro()
	{
		Vector<Libro> libri = model.getLibri();

		
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
				stampaDatiLibro(libri.elementAt((int)i), false);
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
	 * ne stampa uno a uno, chiamando il corrispettivo metodo nella view
	 * @param libro
	 * @param perPrestito
	 */
	public void stampaDatiLibro(Libro libro, boolean perPrestito)
	{
		LibriView.stampaDati(libro, perPrestito);
	}
	
	/**
	 * stampa tutti i libri raggruppandoli per sottocategoria e genere, chiamando il corrispettivo metodo nella view
	 */
	public void stampaDatiLibriPrestabili()
	{
//		uso "libriDaStampare" così quando stampo un libro nella sua categoria posso eliminarlo e non stamparlo di nuovo dopo
		Vector<Libro> libriDaStampare = new Vector<>();
		for(Libro libro : model.getLibri())
		{
			if(libro.isPrestabile())
			{
				libriDaStampare.add(libro);
			}
		}
		LibriView.stampaDatiPerCategorie(libriDaStampare);
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
	 * stampa i dati dei libri corrispondenti ai parametri di ricerca specificati dall'utente
	 */
	public void cercaLibro()
	{
		MenuFiltroLibri.show(false, this);
	}
	
	/**
	 * Consente all'utente di selezionare un libro in base a dei criteri di ricerca
	 * @return il libro corrispondente ali criteri inseriti dall'utente
	 */
	public Libro scegliLibro() 
	{
		Libro libroSelezionato = MenuScegliLibro.show(this);
		return libroSelezionato;
	}
}
