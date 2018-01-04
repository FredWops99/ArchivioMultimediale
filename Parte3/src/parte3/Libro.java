package parte3;
import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che rappresenta la descrizione di una risorsa multimediale di tipo Libro
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Libro implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String sottoCategoria;//sottocategorie della categoria LIBRO: Romanzo, Fumetto, Poesia...
	private String nome;
	private Vector<String> autori = new Vector<>();
	private int pagine;
	private int annoPubblicazione;
	private String casaEditrice;
	private String lingua;
	private String genere;
	private int nLicenze;
	
	/**
	 * Costuttore della classe libro
	 * @param nome il titolo del libro
	 * @param autori il vector degli autori del libro
	 * @param pagine il numero di pagine
	 * @param annoPubblicazione l'anno di pubblicazione
	 * @param casaEditrice la casa editrice
	 * @param lingua la lingua del testo
	 * @param sottoCategoria la sottocategoria della categoria LIBRO (es. Romanzo, Fumetto, Poesia...)
	 * @param genere il genere del libro ( "-" se il genere non ha sottogeneri)
	 * @param nLicenze il numero di licenze disponibili
	 */
	public Libro(String sottoCategoria, String nome, Vector<String> autori, int pagine, int annoPubblicazione, String casaEditrice,
			String lingua, String genere, int nLicenze) 
	{
		this.setSottoCategoria(sottoCategoria);
		this.setNome(nome);
		this.setAutori(autori);
		this.setPagine(pagine);
		this.setAnnoPubblicazione(annoPubblicazione);
		this.setCasaEditrice(casaEditrice);
		this.setLingua(lingua);
		this.setGenere(genere);
		this.setnLicenze(nLicenze);
	}

	/**
	 * Stampa tutte le informazioni del libro
	 */
	public void stampaDati()
	{
		System.out.println("------------------------------------------------");
		System.out.println("Categoria-----------------: Libro");
		System.out.println("Sottocategoria------------: " + sottoCategoria);
		System.out.println("Titolo--------------------: " + nome);
		System.out.print("Autori--------------------:");
		for(int i = 0; i < autori.size(); i++)
		{
			System.out.print(" " + autori.elementAt(i));
			if(i < autori.size()-1)
			{
				System.out.print(",");
			}
			else System.out.println();
		}
		if(!genere.equals("-"))
		{
			System.out.println("Genere--------------------: " + genere);
		}
		System.out.println("Numero pagine-------------: " + pagine);
		System.out.println("Anno di pubblicazione-----: " + annoPubblicazione);
		System.out.println("Casa editrice-------------: " + casaEditrice);
		System.out.println("Lingua--------------------: " + lingua);
		System.out.println("Numero licenze------------: " + nLicenze);
	}

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public Vector<String> getAutori() 
	{
		return autori;
	}
	public void setAutori(Vector<String> autori) 
	{
		this.autori = autori;
	}
	public int getPagine()
	{
		return pagine;
	}
	public void setPagine(int pagine) 
	{
		this.pagine = pagine;
	}
	public int getAnnoPubblicazione() 
	{
		return annoPubblicazione;
	}
	public void setAnnoPubblicazione(int annoPubblicazione) 
	{
		this.annoPubblicazione = annoPubblicazione;
	}
	public String getCasaEditrice() 
	{
		return casaEditrice;
	}
	public void setCasaEditrice(String casaEditrice) 
	{
		this.casaEditrice = casaEditrice;
	}
	public String getLingua() 
	{
		return lingua;
	}
	public void setLingua(String lingua) 
	{
		this.lingua = lingua;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public void setSottoCategoria(String sottoCategoria) 
	{
		this.sottoCategoria = sottoCategoria;
	}
	public String getGenere() 
	{
		return genere;
	}
	public void setGenere(String sottoGenere) 
	{
		this.genere = sottoGenere;
	}
	public int getnLicenze() 
	{
		return nLicenze;
	}
	public void setnLicenze(int nLicenze) 
	{
		this.nLicenze = nLicenze;
	}
}
