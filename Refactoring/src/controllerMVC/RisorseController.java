package controllerMVC;

import java.util.Vector;
import handler.FiltraFilmHandler;
import handler.FiltraLibriHandler;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.*;
import myLib.MyMenu;
import viewInterfaces.IFilmsView;
import viewInterfaces.ILibriView;
import viewInterfaces.IMessaggiSistemaView;

public class RisorseController 
{
	private final String[] CATEGORIE = {"Libri","Films"};

	private Risorse risorse;
	
	private FilmsController filmsController;
	private IFilmsView filmsView;
	private LibriController libriController;
	private ILibriView libriView;
	private IMessaggiSistemaView messaggiSistemaView;
	
	private FiltraLibriHandler filtraLibriHandler;
	private FiltraFilmHandler filtraFilmHandler;
	private ManageRisorseHandler manageRisorseHandler;
	
	public RisorseController(Risorse risorse)
	{
		manageRisorseHandler = new ManageRisorseHandler(this);
		this.risorse = risorse;
		filmsController = new FilmsController(risorse,manageRisorseHandler);
		filmsView = filmsController.getFilmsView();
		libriController = new LibriController(risorse,manageRisorseHandler);
		libriView = libriController.getLibriView();
//		per messaggiSistemaView: sennò Controller dipenderebbe da MessaggiSistemaView, a causa dell'instanziamento. così solo interface
		try 
		{
			this.messaggiSistemaView = (IMessaggiSistemaView)Class.forName(System.getProperty("MessaggiSistemaView")).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public FilmsController getFilmsController() 
	{
		return filmsController;
	}

	public LibriController getLibriController() 
	{
		return libriController;
	}
	
//	la ricerca avviene tramite filtri
	public void cercaRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
			if(filtraLibriHandler==null)
			{
				filtraLibriHandler = new FiltraLibriHandler(libriController);
			}
			filtraLibriHandler.menuFiltraLibri(false);
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			if(filtraFilmHandler==null)
			{
				filtraFilmHandler = new FiltraFilmHandler(filmsController);
			}
			filtraFilmHandler.menuFiltraFilm(false);
		}
	}

//	quando si deve scegliere una risorsa per il prestito c'è la possibilità di visualizzare l'intero elenco
	public Risorsa scegliRisorsa(String categoria) 
	{
		if(categoria == CATEGORIE[0])
		{
//			stampa il menu, l'utente sceglie, poi se ne occupa ScegliLibroHandler
			return libriController.menuScegliLibro();
		}
		else if(categoria == CATEGORIE[1])
		{
//			stampa il menu, l'utente sceglie, poi se ne occupa ScegliFilmHandler
			return filmsController.menuScegliFilm();
		}
		else return null;
	}
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * (precondizione: CATEGORIE != null)
	 * @return l'id della risorsa rimossa
	 */
	public String scegliRisorsaDaRimuovere() 
	{
		messaggiSistemaView.avvisoRimozioneRisorsa();
		
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRimosso = menuRimuoviRisorsa();
//				RimuoviRisorsaHandler.showAndReturnID(CATEGORIE, this);
		return idRimosso;
	}
	
	private String menuRimuoviRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";

		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			return rimuoviRisorsa(categoria);		
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
	
	/**
	 * permette la rimozione di una risorsa da parte dell'operatore: la risorsa selezionata verrà etichettata come "non Prestabile" 
	 * ma rimarrà in archivio per motivi storici
	 * @return l'id della risorsa rimosso
	 */
	public String rimuoviRisorsa(String categoria) 
	{
		Vector<Risorsa> elencoRisorse = risorse.getRisorse();
		String idSelezionato;
		
		String titolo = filmsView.chiediRisorsaDaRimuovere();
				
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		if(categoria == CATEGORIE[0])//LIBRI
		{
			for (int i = 0; i < elencoRisorse.size(); i++)
			{
				if(elencoRisorse.get(i).getId().charAt(0)=='L' && elencoRisorse.get(i).isPrestabile() 
						&& elencoRisorse.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
				{
//					ogni volta che trovo una risorsa con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
					posizioniRicorrenze.add(i);
				}
			}
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			for (int i = 0; i < elencoRisorse.size(); i++)
			{
				if(elencoRisorse.get(i).getId().charAt(0)=='F' && elencoRisorse.get(i).isPrestabile() 
						&& elencoRisorse.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
				{
//					ogni volta che trovo una risorsa con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
					posizioniRicorrenze.add(i);
				}
			}
		}
		
		if(posizioniRicorrenze.size()==0)
		{
			filmsView.risorsaNonPresente();
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizione
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = elencoRisorse.get((int)posizioniRicorrenze.get(0)).getId();
			elencoRisorse.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			filmsView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore li stampo e chiedo di selezionare quale si vuole rimuovere
		else
		{
			filmsView.piùRisorseStessoTitolo(categoria, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				filmsView.numeroRicorrenza(pos);
				messaggiSistemaView.cornice();
				filmsView.stampaDati(elencoRisorse.elementAt((int)i), false);
				messaggiSistemaView.cornice();
			}
			
			int daRimuovere = filmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
			
			if(daRimuovere > 0)
			{
				idSelezionato = elencoRisorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				elencoRisorse.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				filmsView.rimozioneAvvenuta();
			}
			else//0: annulla
			{
				idSelezionato = "-1";
			}
		}
		return idSelezionato;
	}
	
	public void menuAggiungiRisorsa() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		try
		{
			String categoria = CATEGORIE[scelta - 1];
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se creare un libro o un film
			addRisorsa(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	} 
	
	public void addRisorsa(String categoria)
	{
		if(categoria == CATEGORIE[0])//LIBRO
		{
//			controller interagisce con view per creare il libro
			libriController.addLibro();
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
//			controller interagisce con view per creare il film
			filmsController.addFilm();
		}
	}

	public void menuVisualizzaElencoRisorse() 
	{
		final String INTESTAZIONE = "scegli la categoria: ";
		MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
		int scelta = menu.scegliBase();
		
		try
		{
			String categoria = CATEGORIE[scelta - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
//			viene passata come stringa la categoria selezionata: archivioController deciderà poi se stampare libri o film
			
			stampaDatiRisorsePrestabili(categoria);
			
//			visualizzaDatiRisorsePrestabili(categoria);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}	
	}
	
	private void stampaDatiRisorsePrestabili(String categoria)
	{
	//	uso "libriDaStampare" così quando stampo un libro nella sua categoria posso eliminarlo e non stamparlo di nuovo dopo
		Vector<Risorsa> risorseDaStampare = new Vector<>();
		for(Risorsa risorsa : risorse.getRisorse())
		{
			if(risorsa.getCategoria().equals(categoria) && risorsa.isPrestabile())
			{
				risorseDaStampare.add(risorsa);
			}
		}
		//anche se unifico con interfaccia risorsa devo comunque stampare i dati in modo diverso: films e risorse hanno campi diversi
		if(categoria == CATEGORIE[0])
		{
			libriView.stampaDatiPerCategorie(risorseDaStampare);
		}
		else if(categoria == CATEGORIE[1])
		{
			filmsController.getFilmsView().stampaDati(risorseDaStampare);
		}
	}
}
