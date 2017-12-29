package parte2;
import java.io.Serializable;
import java.util.Vector;

import myLib.InputDati;
import myLib.MyMenu;

public class Libri implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final String[] GENERI = {"Romanzo","Fumetto","Poesia"}; 
	private static final String[] SOTTOGENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	
	private Vector<Libro> libri;
	private Vector<String> autori;
	
	public Libri()
	{
		this.libri = new Vector<Libro>();
		this.autori = new Vector<String>();
	}
	
	public Vector<Libro> getLibri() 
	{
		return libri;
	}
	public void setLibri(Vector<Libro> libri) 
	{
		this.libri = libri;
	}
	public Vector<String> getAutori() 
	{
		return autori;
	}
	public void setAutori(Vector<String> autori) 
	{
		this.autori = autori;
	}

	public void addLibro()
	{
		String titolo = InputDati.leggiStringa("Inserisci il nome del titolo: ");
		
		do
		{
			String autore = InputDati.leggiStringa("Inserisci l'autore: ");
			autori.add(autore);
		} while(InputDati.yesOrNo("ci sono altri autori ?"));
		
		int pagine = InputDati.leggiInteroPositivo("Inserisci il numero delle pagine: ");
		int annoPubblicazione = InputDati.leggiInteroPositivo("Inserisci l'anno di pubblicazione: ");
		String casaEditrice = InputDati.leggiStringa("Inserisci la casa editrice: ");
		String lingua = InputDati.leggiStringa("Inserisci la lingua del testo: ");
		String genere = this.scegliGenere();
		String sottoGenere = this.scegliSottoGenere(genere);
		int nLicenza = InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
		
		Libro l = new Libro(titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, sottoGenere, nLicenza);
		libri.add(l);
		System.out.println("Libro aggiunto con successo!");
	}
	
	public void removeLibro()
	{
		String titolo = InputDati.leggiStringa("Inserisci il titolo del libro da rimuovere: ");
		for (int i=0;i<libri.size();i++) 
		{
			if(libri.get(i).getNome().equals(titolo))
			{
				libri.remove(i);
				return;
			}
		}
		System.out.println("Spiacente, non è stato possibilie rimuovere il libro");
	}
	
	public void stampaLibri()
	{
		if(libri.isEmpty())
		{
			System.out.println("Nessun libro presente");
			return;
		}
		
		Vector<Libro> listaLibri = libri;
		
		for(int j=0;j<listaLibri.size();j++)
		{
			if(! listaLibri.get(j).getSottoGenere().equals("-"))
			{
				System.out.println("");
				System.out.println("---------------------- ");
				System.out.println("Categoria: " + listaLibri.get(j).getGenere() + ", Sottocategoria: "  +
						listaLibri.get(j).getSottoGenere() + "\n");
				System.out.println("titolo: " + listaLibri.get(j).getNome());
				
				for (int i=j+1;i<listaLibri.size();i++) 
				{
					if(listaLibri.get(j).getGenere().equals(listaLibri.get(i).getGenere()))
					{
						if(listaLibri.get(j).getSottoGenere().equals(listaLibri.get(i).getSottoGenere()))
						{
							System.out.println("titolo: " + listaLibri.get(i).getNome());
							listaLibri.remove(i);
						}
					}
				}
			}
			else
			{
				System.out.println("");
				System.out.println("---------------------- ");
				System.out.println("Categoria: " + listaLibri.get(j).getGenere());
				System.out.println("titolo: " + listaLibri.get(j).getNome());
				for (int i=j+1;i<listaLibri.size();i++) 
				{
					if(listaLibri.get(j).getGenere().equals(listaLibri.get(i).getGenere()))
					{
							System.out.println("titolo: " + listaLibri.get(i).getNome());
							listaLibri.remove(i);
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
