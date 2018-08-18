package view;

import java.util.Vector;

import model.Libro;
import myLib.GestioneDate;
import myLib.InputDati;

public class LibriView 
{
	public static void stampaDati(Libro libro, boolean perPrestito) 
	{
		System.out.println(libro.toString(perPrestito));	
	}
	
	public static String chiediTitolo() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: ");
	}
	
	public static int chiediPagine()
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di pagine: ");
	}
	
	public static int chiediAnnoPubblicazione()
	{
		return InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: ", GestioneDate.ANNO_CORRENTE);
	}
	
	public static String chiediCasaEditrice()
	{
		return InputDati.leggiStringaNonVuota("Inserisci la casa editrice: ");
	}
	
	public static String chiediLingua() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la lingua del testo: ");
	}
	
	public static String chiediAutore()
	{
		return InputDati.leggiStringaNonVuota("Inserisci l'autore: ");
	}
	
	public static Boolean ciSonoAltriAutori()
	{
		return InputDati.yesOrNo("ci sono altri autori? ");
	}
	
	public static int chiediNumeroLicenze()
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
	}
	
	public static void aggiuntaRiuscita()
	{
		System.out.println("Libro aggiunto con successo!");
	}
	
	public static void aggiuntaNonRiuscita()
	{
		System.out.println("Il libro è già presente in archivio");
	}
	
	public static String chiediLibroDaRimuovere() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del libro da rimuovere: ");
	}
	
	public static void libroNonPresente()
	{
		System.out.println("Siamo spiacenti, il libro non è presente nell'archivio");
	}
	
	public static void libroNonPresente(String s)
	{
		System.out.println("In archivio non sono presenti libri il cui titolo è: " + s );
	}
	
	public static void rimozioneAvvenuta()
	{
		System.out.println("La risorsa è stata eliminata dalle risorse prestabili (verrà conservata in archivio per motivi storici)");
	}
	
	public static void piùLibriStessoTitolo(String titolo)
	{
		System.out.println("Sono presenti più libri dal titolo \"" + titolo + "\": ");
	}
	
	public static void numeroRicorrenza(int pos) 
	{
		System.out.println("\nRicorrenza " + ++pos + ":");
	}
	
	public static int chiediRicorrenzaDaRimuovere(Vector<Integer> posizioniRicorrenze) 
	{
		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
	}
	
	public static void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoUscita);	
	}
	
	public static void autoreNonPresente(String nomeAutore)
	{
		System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
	}
	
	public static void zeroLibriInArchivio()
	{
		System.out.println("In archivio non sono presenti libri disponibili");
	}
	
	public static void unoLibriInArchivio()
	{
		System.out.println("\nE' presente un libro in archivio: ");
	}
	
	public static void piùLibriInArchivio(Vector<Libro> libri)
	{
		System.out.println("\nSono presenti " + libri.size() + " libri in archivio: ");
	}
	
	public static void sottocategoria(Libro l)
	{
		System.out.println("Sottocategoria: " + l.getSottoCategoria());
	}
	
	public static void genere(Libro l)
	{
		System.out.println("Genere: " + l.getGenere());
	}
	
	public static void titolo(Libro l)
	{
		
		System.out.println("Titolo: " + l.getTitolo());
	}
}
