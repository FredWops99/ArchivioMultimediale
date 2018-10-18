package controllerMVC;

import java.util.Vector;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Libro;
import model.Risorse;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;

/**
 * CONTROLLER interagisce con view (quindi anche menu?) e modifica il MODEL
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class LibriController 
{
	private Risorse model;
	private int lastId;	
	private ManageRisorseHandler manageRisorseHandler;
	
//	CATEGORIA è libro
	private static final String[] SOTTOCATEGORIE = {"Romanzo","Fumetto","Poesia"}; //le sottocategorie della categoria LIBRO (Romanzo, fumetto, poesia,...)
	private static final String[] GENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	private static final String IDENTIFIER = "L";
	
	public LibriController(Risorse risorse, ManageRisorseHandler manageRisorseHandler)
	{
		this.model = risorse;
		this.lastId = risorse.getLastIdLibro();
		this.manageRisorseHandler = manageRisorseHandler;
	}

	public Risorse getModel() 
	{
		return model;
	}
	
	public int getLastId() 
	{
		return lastId;
	}
	
	public void addLibro()
	{
		String sottoCategoria = scegliSottoCategoria();
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}
		
		String genere = scegliGenere(sottoCategoria);
//				ScegliGenereLibroHandler.show(sottoCategoria);//se la sottocategoria ha generi disponibili
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
		
		Libro l = new Libro(IDENTIFIER +lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		
		boolean aggiuntaRiuscita = model.addRisorsa(l);
		
		if(aggiuntaRiuscita)
		{
			LibriView.aggiuntaRiuscita(Libro.class);
		}
		else
		{
			LibriView.aggiuntaNonRiuscita(Libro.class);
		}
	}
	
	/**
	 * metodo per Test che consente di non chiedere in input all'utente i campi per creare la risorsa
	 */
	public void addLibro(String sottoCategoria, String titolo, Vector<String> autori, int pagine, int annoPubblicazione,
			String casaEditrice, String lingua, String genere, int nLicenze)
	{
		Libro l = new Libro("L"+lastId++, sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		
		model.addRisorsa(l);
	}

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
	 * permette la rimozione di un libro da parte dell'operatore: il libro selezionato verrà etichettato come "non Prestabile" ma rimarrà in archivio per 
	 * motivi storici
	 * @return l'id del libro rimosso
	 */
	public String removeLibro()
	{
		Vector<Risorsa> risorse = model.getRisorse();

		String idSelezionato;
		
		String titolo = LibriView.chiediRisorsaDaRimuovere(Libro.class);
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < risorse.size(); i++)
		{
			if(risorse.get(i).getId().charAt(0)=='L' && risorse.get(i).isPrestabile() 
					&& risorse.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
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
			idSelezionato = risorse.get((int)posizioniRicorrenze.get(0)).getId();
			risorse.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			LibriView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore (più libri con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quello che vuole rimuovere
		else
		{
			LibriView.piùRisorseStessoTitolo(titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				LibriView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiLibro(risorse.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = LibriView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
			
			if(daRimuovere > 0)
			{
				idSelezionato = risorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				risorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
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
	public void stampaDatiLibro(Risorsa libro, boolean perPrestito)
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
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='L' && risorsa.isPrestabile())
			{
				libriDaStampare.add((Libro)risorsa);
			}
		}
		LibriView.stampaDatiPerCategorie(libriDaStampare);
	}
	
	public Vector<Risorsa> libriPrestabili()
	{
		Vector<Risorsa>libriPrestabili = new Vector<>();
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='L' && risorsa.isPrestabile())
			{
				libriPrestabili.add(risorsa);
			}
		}
		return libriPrestabili;
	}
	
	/**
	 * presenta all'utente il menu per decidere se scegliere un libro dall'archivio completo o filtrando la ricerca.
	 * informerà della scelta l'handler del caso d'uso (ScegliLibroHandler)
	 * @return
	 */
	public Risorsa menuScegliLibro() 
	{
		final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";
		final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
		
		MyMenu menuSceltaLibro = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaLibro.scegliBase();
		
		return manageRisorseHandler.scegliLibro(scelta);
	}	
	
	public Libro selezionaLibro(Vector<Risorsa> libriFiltrati) 
	{
		if(libriFiltrati.isEmpty())
		{
			LibriView.noRisorseDisponibili();
			return null;
		}
		else
		{
			for(int i = 0; i < libriFiltrati.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				stampaDatiLibro(libriFiltrati.get(i), true);
				MessaggiSistemaView.cornice();
			}
			
			int selezione;
			do
			{
				MessaggiSistemaView.cornice();
				selezione = LibriView.selezionaRisorsa(libriFiltrati.size(), Libro.class);
				if(selezione == 0)
				{
					return null;
				}
				else if(libriFiltrati.get(selezione-1).getInPrestito() < libriFiltrati.get(selezione-1).getNLicenze())
				{
					return (Libro) libriFiltrati.get(selezione-1);
				}
				else
				{
					LibriView.copieTutteInPrestito(libriFiltrati.get(selezione-1).getTitolo());
				}
			}
			while(true);
		}		
	}
}
