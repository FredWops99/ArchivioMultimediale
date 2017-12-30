package parte2;
import java.io.Serializable;
import java.util.Vector;

public class Libro implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Vector<String> autori = new Vector<>();
	private int pagine;
	private int annoPubblicazione;
	private String casaEditrice;
	private String lingua;
	private String genere;
	private String sottoGenere;
	private int nLicenze;
	
	public Libro(String nome, Vector<String> autori, int pagine, int annoPubblicazione, String casaEditrice,
			String lingua, String genere,String sottoGenere,int nLicenze) 
	{
		this.setNome(nome);
		this.setAutori(autori);
		this.setPagine(pagine);
		this.setAnnoPubblicazione(annoPubblicazione);
		this.setCasaEditrice(casaEditrice);
		this.setLingua(lingua);
		this.setGenere(genere);
		this.setSottoGenere(sottoGenere);
		this.setnLicenze(nLicenze);
	}
	
	public void stampaDati()
	{
		System.out.println("------------------------------------------------\n");
		System.out.println("Titolo--------------------: " + nome);
		System.out.print("Autori--------------------:");
		for(int i = 0; i < autori.size(); i++)
		{
			System.out.print(" " + autori.elementAt(i));
			if(i < autori.size()-1)
			{
				System.out.print(",");
			}
		}
		System.out.println("\nNumero pagine-------------: " + pagine);
		System.out.println("Anno di pubblicazione-----: " + annoPubblicazione);
		System.out.println("Casa editrice-------------: " + casaEditrice);
		System.out.println("Lingua--------------------: " + lingua);
		System.out.println("Genere--------------------: " + genere);
		if(!sottoGenere.equals("-"))
		{
			System.out.println("Sottogenere---------------: " + sottoGenere);
		}
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
	public String getGenere() 
	{
		return genere;
	}
	public void setGenere(String genere) 
	{
		this.genere = genere;
	}
	public String getSottoGenere() 
	{
		return sottoGenere;
	}
	public void setSottoGenere(String sottoGenere) 
	{
		this.sottoGenere = sottoGenere;
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
