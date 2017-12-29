package parte2;
import java.util.Vector;

public class Libro {

	String nome;
	Vector<String> autori = new Vector<>();
	int pagine;
	int annoPubblicazione;
	String casaEditrice;
	String lingua;
	String genere;
	String sottoGenere;
	int nLicenza;
	
	public Libro(String nome, Vector<String> autori, int pagine, int annoPubblicazione, String casaEditrice,
			String lingua, String genere,String sottoGenere,int nLicenza) 
	{
		super();
		this.nome = nome;
		this.autori = autori;
		this.pagine = pagine;
		this.annoPubblicazione = annoPubblicazione;
		this.casaEditrice = casaEditrice;
		this.lingua = lingua;
		this.genere = genere;
		this.sottoGenere = sottoGenere;
		this.nLicenza = nLicenza;
	}
	
	
}
