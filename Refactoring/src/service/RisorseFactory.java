package service;

import java.util.Vector;
import interfaces.Risorsa;
import model.Film;
import model.Libro;
import model.Risorse;
import myLib.MyMenu;
import viewInterfaces.IFilmsView;
import viewInterfaces.ILibriView;

public class RisorseFactory 
{
	private final String[] CATEGORIE = {"Libri","Films"};
	private static final String[] SOTTOCATEGORIE_LIBRI = {"Romanzo","Fumetto","Poesia"}; //le sottocategorie della categoria LIBRO (Romanzo, fumetto, poesia,...)
	private static final String[] SOTTOCATEGORIE_FILM = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String[] GENERI_LIBRI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	private static final String IDENTIFIER_LIBRI = "L";
	private static final String IDENTIFIER_FILM = "F";

	
	private Risorse risorse;
	private ILibriView libriView;
	private IFilmsView filmsView;

	public RisorseFactory(Risorse risorse, ILibriView libriView, IFilmsView filmsView) 
	{
		this.risorse = risorse;
		this.libriView = libriView;
		this.filmsView = filmsView;
	}

	public Risorsa creaRisorsa(String categoria)
	{
		Risorsa risorsa = null;
		if(categoria == CATEGORIE[0])//LIBRO
		{
			risorsa = creaLibro();
		}
		else if(categoria == CATEGORIE[1])//FILM
		{
			risorsa = createFilm();
		}
		return risorsa;
	}

	private Risorsa creaLibro() 
	{
		String sottoCategoria = scegliSottoCategoriaLibro();
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return null;
		}
		
		String genere = scegliGenere(sottoCategoria);
		String titolo = libriView.chiediTitolo();
		int pagine = libriView.chiediPagine();
		int annoPubblicazione = libriView.chiediAnnoPubblicazione();
		String casaEditrice = libriView.chiediCasaEditrice();
		String lingua = libriView.chiediLingua();
		Vector<String> autori = new Vector<String>();
		do
		{
			String autore = libriView.chiediAutore();
			autori.add(autore);
		} 
		while(libriView.ciSonoAltriAutori());
		
		int nLicenze = libriView.chiediNumeroLicenze();
		
		Libro l = new Libro(IDENTIFIER_LIBRI + risorse.getLastId(), sottoCategoria, titolo, autori, pagine, annoPubblicazione, casaEditrice, lingua, genere, nLicenze);
		return l;
	}
	
	private Risorsa createFilm() 
	{
		String sottoCategoria = this.scegliSottoCategoriaFilm();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return null;
		}

		String titolo = filmsView.chiediTitolo();
		int durata = filmsView.chiediDurata();
		int annoDiUscita = filmsView.chiediAnnoUscita();
		String lingua = filmsView.chiediLingua();
		String regista = filmsView.chiediRegista();
		int nLicenze = filmsView.chiediNumeroLicenze();
		
		Film f = new Film(IDENTIFIER_FILM + risorse.getLastId(), sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		return f;
	}
	
	private String scegliSottoCategoriaLibro() 
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del libro: ", SOTTOCATEGORIE_LIBRI, true);
		try
		{
			return SOTTOCATEGORIE_LIBRI[menu.scegliBase() - 1];
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
			MyMenu menu = new MyMenu("scegli un genere: ", GENERI_LIBRI);
			return GENERI_LIBRI[menu.scegliNoIndietro() - 1];	
		}
		else
		{
			return "-";
		}
	}
	
	private String scegliSottoCategoriaFilm()
	{
		final String SCEGLI_CATEGORIA = "scegli la sottocategoria del film: ";
		MyMenu menu = new MyMenu(SCEGLI_CATEGORIA, SOTTOCATEGORIE_FILM, true);
		try
		{
			return SOTTOCATEGORIE_FILM[menu.scegliBase() - 1];
		}
//		se l'utente selezione 0: ANNULLA -> eccezione
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "annulla";
		}	
	}
}
