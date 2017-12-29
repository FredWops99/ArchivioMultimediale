package parte2;
import java.util.Vector;

import myLib.InputDati;
import myLib.MyMenu;

public class Libri {

	private static final String[] generi = {"Romanzo","Fumetti","Poesia"}; 
	private static final String[] sottoGeneri = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	
	private Vector<Libro> libri = new Vector<>();
	private Vector<String> autori = new Vector<>();
	
	
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
		int nLicenza = InputDati.leggiInteroPositivo("Inserisci il numero della licenza: ");
		
		Libro l = new Libro(titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, sottoGenere, nLicenza);
		libri.add(l);
	}
	
	public void removeLibro()
	{
		String titolo = InputDati.leggiStringa("Inserisci il titolo del libro da rimuovere: ");
		for (int i=0;i<libri.size();i++) 
		{
			if(libri.get(i).nome.equals(titolo))
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
			if(! listaLibri.get(j).sottoGenere.equals("-"))
			{
				System.out.println("");
				System.out.println("---------------------- ");
				System.out.println("Categoria: " + listaLibri.get(j).genere + ", Sottocategoria: "  +
						listaLibri.get(j).sottoGenere + "\n");
				System.out.println("titolo: " + listaLibri.get(j).nome);
				
				for (int i=j+1;i<listaLibri.size();i++) 
				{
					if(listaLibri.get(j).genere.equals(listaLibri.get(i).genere))
					{
						if(listaLibri.get(j).sottoGenere.equals(listaLibri.get(i).sottoGenere))
						{
							System.out.println("titolo: " + listaLibri.get(i).nome);
							listaLibri.remove(i);
						}
					}
				}
			}
			else
			{
				System.out.println("");
				System.out.println("---------------------- ");
				System.out.println("Categoria: " + listaLibri.get(j).genere);
				System.out.println("titolo: " + listaLibri.get(j).nome);
				for (int i=j+1;i<listaLibri.size();i++) 
				{
					if(listaLibri.get(j).genere.equals(listaLibri.get(i).genere))
					{
							System.out.println("titolo: " + listaLibri.get(i).nome);
							listaLibri.remove(i);
					}
				}
			}
		}
	}
	private String scegliGenere()
	{
		MyMenu menu = new MyMenu("scegli un genere", generi);
		return generi[menu.scegliBase("") - 1];	
	}
	
	private String scegliSottoGenere(String genere)
	{
		if(genere.equals("Romanzo") || genere.equals("Fumetti")) //se si aggiunge un genere va aggiunto anche qui !
		{
			MyMenu menu = new MyMenu("scegli un sotto-genere", sottoGeneri);
			return sottoGeneri[menu.scegliBase("") - 1];	
		}
		else
		{
			return "-";
		}
	}
}
