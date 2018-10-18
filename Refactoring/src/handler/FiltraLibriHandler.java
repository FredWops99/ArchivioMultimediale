package handler;

import java.util.Vector;
import controllerMVC.LibriController;
import interfaces.Risorsa;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;

public class FiltraLibriHandler 
{
	private LibriController libriController;
	
	public FiltraLibriHandler(LibriController libriController)
	{
		this.libriController = libriController;
	}
	
	/**
	 * se i libri vengono filtrati per essere prenotati viene restituita la lista, se invece è solo per la ricerca vengono visualizzati e basta
	 * @param daPrenotare 
	 * @return la lista dei libri filtrati (if daPrenotare)
	 */
	public Vector<Risorsa> menuFiltraLibri(boolean daPrenotare) 
	{
		final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
		final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di pubblicazione", "Filtra per autore"};
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
		int scelta = menuFiltro.scegliBase();
		
		Vector<Risorsa> libriFiltrati = filtraLibri(scelta, daPrenotare);
		
		if(daPrenotare)
		{
			return libriFiltrati;
		}
		else// !daPrenotare
		{
			return null;
		}
	}
	
	public Vector<Risorsa> filtraLibri(int scelta, boolean daPrenotare) 
	{
		Vector<Risorsa> libriFiltrati = null;
		String titoloParziale = null;
		int annoPubblicazione = 0;
		String nomeAutore = null;
		
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return null;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = LibriView.chiediTitolo();
				libriFiltrati = filtraLibriPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO DI PUBBLICAZIONE
			{
				annoPubblicazione = LibriView.chiediAnnoPubblicazione();
				libriFiltrati = filtraLibriPerAnnoPubblicazione(annoPubblicazione);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeAutore = LibriView.chiediAutore();
				libriFiltrati = filtraLibriPerAutori(nomeAutore);
				break;
			}
		}
		if (daPrenotare == false)
		{
			if(scelta == 1 && libriFiltrati.isEmpty()) 
			{
				LibriView.risorsaNonPresente(titoloParziale);
				return null;
			}
			if(scelta == 2 && libriFiltrati.isEmpty())
			{
				LibriView.annoNonPresente(annoPubblicazione);
				return null;
			}
			if(scelta == 3 && libriFiltrati.isEmpty())
			{
				LibriView.autoreNonPresente(nomeAutore);
				return null;
			}
			
			for (int i=0; i <libriFiltrati.size(); i++) 
			{
				MessaggiSistemaView.cornice(true,false);
				libriController.stampaDatiLibro(libriFiltrati.get(i), false);
			}
		}
		
		else if (daPrenotare == true)
		{
			return libriFiltrati;
		}
		//se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
	
	public Vector<Risorsa> filtraLibriPerTitolo(String titoloParziale)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = libriController.getModel().filtraRisorsePerTitolo(titoloParziale);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='L')
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraLibriPerAnnoPubblicazione(int annoPubblicazione)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = libriController.getModel().filtraRisorsePerUscita(annoPubblicazione);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='L')
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraLibriPerAutori(String autore)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = libriController.getModel().filtraLibriPerAutori(autore);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getId().charAt(0)=='L')
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
}
