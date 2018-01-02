package parte2;
import java.io.Serializable;
import java.util.Vector;

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
	
	private static final String[] GENERI = {"Romanzo","Fumetto","Poesia"}; 
	private static final String[] SOTTOGENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	
	private Vector<Libro> libri;
	
	/**
	 * costruttore della classe: inizializza il Vector di libri
	 */
	public Libri()
	{
		this.libri = new Vector<Libro>();
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
	 * procedura per l'aggiunta di un libro alla raccolta: chiede all'utente di inserire tutti i campi necessari
	 */
	public void addLibro()
	{
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il nome del titolo: ");
		
		
		int pagine = InputDati.leggiInteroPositivo("Inserisci il numero delle pagine: ");
		int annoPubblicazione = InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: ", GestioneDate.ANNO_CORRENTE);
		String casaEditrice = InputDati.leggiStringaNonVuota("Inserisci la casa editrice: ");
		String lingua = InputDati.leggiStringaNonVuota("Inserisci la lingua del testo: ");
		
		int nLicenze = InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
		Vector<String> autori = new Vector<String>();
		do
		{
			String autore = InputDati.leggiStringaNonVuota("Inserisci l'autore: ");
			autori.add(autore);
		} 
		while(InputDati.yesOrNo("ci sono altri autori? "));
		String genere = this.scegliGenere();
		String sottoGenere = this.scegliSottoGenere(genere);
		
		Libro l = new Libro(titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, sottoGenere, nLicenze);
		addPerCategorie(l);
		
		System.out.println("Libro aggiunto con successo!");
	}
	
	/**
	 * inserisco i libri nel Vector in modo che siano ordinati per genere, così, quando vengono stampati, i generi sono in ordine
	 * (il metodo stampaLibri li raccoglierà per sottogeneri: il suo difetto era che tra sottogeneri dello stesso genere potevano esserci 
	 * altri generi con i relativi sottogeneri)
	 * @param l il libro da inserire
	 */
	private void addPerCategorie(Libro l)
	{
		if(libri.isEmpty())
		{
			libri.add(l);
		}
		else
		{
			for(int i = 0; i < libri.size(); i++)
			{
				if(libri.get(i).getGenere().equals(l.getGenere()))
				{
					libri.add(i+1, l);
					return;
				}
			}
			libri.add(l);
		}
		
	}

	/**
	 * procedura per rimuovere un libro dalla raccolta
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
				libri.elementAt((int)i).stampaDati();
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
	 * libri con lo stesso nome vengono stampati entrambi
	 */
	public void dettagliLibro()
	{
		String titolo = InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: ");
		int numLibri = 0;
		
		for(Libro libro : libri)
		{
			if(libro.getNome().equals(titolo))
			{
				System.out.println();
				libro.stampaDati();
				numLibri++;
			}
		}
		if(numLibri == 0)
		{
			System.out.println("In archivio non sono presenti libri il cui titolo è " + titolo);
		}
	}
	
	
	/**
	 * stampa tutti i libri raggruppandoli per genere e sottogenere
	 */
	public void stampaLibri()
	{
		if(libri.isEmpty())
		{
			System.out.println("Nessun libro presente");
			return;
		}
		
		System.out.println("\nSono presenti " + libri.size() + " libri in archivio: ");
		
		for(int j = 0; j < libri.size(); j++)
		{
			System.out.println("\n---------------------- ");
			
			if(! libri.get(j).getSottoGenere().equals("-"))
			{
				System.out.println("Genere: " + libri.get(j).getGenere() + ", Sottogenere: "  +
						libri.get(j).getSottoGenere() + "\n");
				System.out.println("titolo: " + libri.get(j).getNome());
				
				for (int i = j+1; i < libri.size(); i++) 
				{
					if(libri.get(j).getGenere().equals(libri.get(i).getGenere()))
					{
						if(libri.get(j).getSottoGenere().equals(libri.get(i).getSottoGenere()))
						{
							System.out.println("titolo: " + libri.get(i).getNome());
							libri.remove(i);
						}
					}
				}
			}
			else
			{
				System.out.println("Genere: " + libri.get(j).getGenere() + "\n");
				System.out.println("titolo: " + libri.get(j).getNome());
				for (int i=j+1;i<libri.size();i++) 
				{
					if(libri.get(j).getGenere().equals(libri.get(i).getGenere()))
					{
							System.out.println("titolo: " + libri.get(i).getNome());
							libri.remove(i);
					}
				}
			}
		}
	}
	private String scegliGenere()
	{
		MyMenu menu = new MyMenu("scegli un genere", GENERI);
		return GENERI[menu.scegliBase("") - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
	}
	
	private String scegliSottoGenere(String genere)
	{
		if(genere.equals("Romanzo") || genere.equals("Fumetto")) //se si aggiunge un genere va aggiunto anche qui !
		{
			MyMenu menu = new MyMenu("scegli un sotto-genere", SOTTOGENERI);
			return SOTTOGENERI[menu.scegliBase("") - 1];	
		}
		else
		{
			return "-";
		}
	}
}
