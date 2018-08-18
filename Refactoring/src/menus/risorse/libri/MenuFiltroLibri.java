package menus.risorse.libri;

import java.util.Vector;
import model.Libro;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;

public class MenuFiltroLibri 
{
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di pubblicazione", "Filtra per autore"};
	
	public static void show(Vector<Libro> libri) 
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
				titoloParziale = LibriView.chiediTitolo();
				libriFiltrati = filtraLibriPerTitolo(titoloParziale, libri);
				break;
			}
			case 2:	//FILTRA PER ANNO DI PUBBLICAZIONE
			{
				annoPubblicazione = LibriView.chiediAnnoPubblicazione();
				libriFiltrati = filtraLibriPerData(annoPubblicazione, libri);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeAutore = LibriView.chiediAutore();
				libriFiltrati = filtraLibriPerAutori(nomeAutore, libri);
				break;
			}
		}
		
		if(scelta == 1 && libriFiltrati.isEmpty()) 
		{
			LibriView.libroNonPresente(titoloParziale);
			return;
		}
		if(scelta == 2 && libriFiltrati.isEmpty())
		{
			LibriView.annoNonPresente(annoPubblicazione);
			return;
		}
		if(scelta == 3 && libriFiltrati.isEmpty())
		{
			LibriView.autoreNonPresente(nomeAutore);
			return;
		}
		
		for (int i=0; i <libriFiltrati.size(); i++) 
		{
			System.out.println();
			MessaggiSistemaView.cornice();
			libriFiltrati.get(i).stampaDati(false);
		}
	
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
	 
	 /** filtra tutti i libri in base al titolo
	  * (precondizione: titoloParziale != null)
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public static Vector<Libro> filtraLibriPerTitolo(String titoloParziale, Vector<Libro> libri)
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
	public static Vector<Libro> filtraLibriPerData(int annoPubblicazione, Vector<Libro> libri)
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
	public static Vector<Libro> filtraLibriPerAutori(String autore, Vector<Libro> libri)
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
