package view;

import java.util.Vector;
import model.Risorsa;
import myLib.InputDati;

public abstract class RisorseView 
{	
	public static void stampaDati(Risorsa risorsa, boolean perPrestito)
	{
		System.out.println(risorsa.toString(perPrestito));
	}
	
	public static String chiediTitolo(Class<?> c)
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del " + c.getSimpleName().toLowerCase() + ": ");
	}
	
	public static int chiediNumeroLicenze() 
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
	}
	
	public static void aggiuntaRiuscita(Class<?> c) 
	{
		System.out.println(c.getSimpleName() + " aggiunto con successo!");	
	}
	
	public static void aggiuntaNonRiuscita(Class<?> c)
	{
		System.out.println("Il " + c.getSimpleName().toLowerCase() + " è già presente in archivio");
	}
	
	public static String chiediRisorsaDaRimuovere(Class<?> c) 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del " + c.getSimpleName().toLowerCase() + " da rimuovere: ");
	}
	
	public static void risorsaNonPresente(Class<?> c)
	{
		System.out.println("Siamo spiacenti, il " + c.getSimpleName().toLowerCase() + " non è presente nell'archivio");
	}
	
	public static void risorsaNonPresente(Class<?> c, String s)
	{
		System.out.println("In archivio non sono presenti " + c.getSimpleName().toLowerCase() + " il cui titolo è: " + s );
	}
	
	public static void rimozioneAvvenuta()
	{
		System.out.println("La risorsa è stata eliminata dalle risorse prestabili (verrà conservata in archivio per motivi storici)");
	}
	
	public static void piùRisorseStessoTitolo(Class<?> c, String titolo) 
	{
		System.out.println("Sono presenti più " + c.getSimpleName().toLowerCase() + " dal titolo \"" + titolo + "\": ");	
	}
	
	public static void numeroRicorrenza(int pos) 
	{
		System.out.println("\nRicorrenza " + ++pos + ":");
	}
	
	public static int chiediRicorrenzaDaRimuovere(Vector<Integer> posizioniRicorrenze) 
	{
		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
	}
	
	public static void noRisorseDisponibili(Class<?> c) 
	{
		System.out.println("In archivio non sono presenti " + c.getSimpleName().toLowerCase() + " disponibili");
	}
	
	public static void nessunaCorrispondenza(Class<?> c)
	{
		System.out.println("Nessun " + c.getSimpleName().toLowerCase() + " corrispondente al criterio di ricerca");
	}
	
	public static void unaRisorsaInArchivio(Class<?> c)
	{
		System.out.println("\nE' presente un " + c.getSimpleName().toLowerCase() + " in archivio: ");
	}
	
	public static void numeroRisorseInArchivio(int dim, Class<?> c)
	{
		System.out.println("\nSono presenti " + dim + " " + c.getSimpleName().toLowerCase() + " in archivio: ");
	}
	
	public static void stampaTitolo(Risorsa risorsa) 
	{
		System.out.println("titolo: " + risorsa.getTitolo());
	}
	
	public static void risorseInArchivio(Class<?> c) 
	{
		System.out.println("\n " + c.getSimpleName() + " in archivio: \n");	
	}
	
	public static void stampaPosizione(int i) 
	{
		System.out.println("\n" + (i+1) + ") ");
	}
	
	public static int selezionaPrestito(Vector<?> elenco, Class<?> c)
	{
		return InputDati.leggiIntero("Seleziona il " + c.getSimpleName().toLowerCase() + " che vuoi ricevere in prestito (0 per annullare): ", 0, elenco.size());
	}
	
	public static void copieTutteInPrestito(String titolo) 
	{
		System.out.println("Tutte le copie di \"" + titolo + "\" sono in prestito!");	
	}
	
	public static void stampaSottoCategoria(Risorsa risorsa)
	{
		System.out.println("Sottocategoria: " + risorsa.getSottoCategoria());
	}
}
