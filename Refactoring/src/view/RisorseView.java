package view;

import interfaces.Risorsa;
import myLib.InputDati;

public abstract class RisorseView 
{	
	public static void stampaDati(Risorsa risorsa, boolean perPrestito)
	{
		System.out.println(risorsa.toString(perPrestito));
	}
	
	public static String chiediTitolo()
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo: ");
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
	
	public static String chiediRisorsaDaRimuovere() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo della risorsa da rimuovere: ");
	}
	
	public static void risorsaNonPresente()
	{
		System.out.println("Siamo spiacenti, la risorsa non è presente nell'archivio");
	}
	
	public static void rimozioneAvvenuta()
	{
		System.out.println("La risorsa è stata eliminata dalle risorse prestabili (verrà conservata in archivio per motivi storici)");
	}
	
	public static void numeroRicorrenza(int pos) 
	{
		System.out.println("\nRicorrenza " + ++pos + ":");
	}
	
	public static int chiediRicorrenzaDaRimuovere(int size) 
	{
		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, size);
	}
	
	public static void nessunaCorrispondenza(Class<?> c)
	{
		System.out.println("Nessun " + c.getSimpleName().toLowerCase() + " corrispondente al criterio di ricerca");
	}
	
	public static void unaRisorsaInArchivio(Class<?> c)
	{
		System.out.println("\nE' presente un " + c.getSimpleName().toLowerCase() + " in archivio: ");
	}
	
	public static void numeroRisorseInArchivio(int size, String s)
	{
		System.out.println("\nSono presenti " + size + " " + s + " in archivio: ");
	}
	
	public static void stampaTitolo(Risorsa risorsa) 
	{
		System.out.println("titolo: " + risorsa.getTitolo());
	}
	
	public static void risorseInArchivio(Class<?> c) 
	{
		System.out.println("\n" + c.getSimpleName() + " in archivio: \n");	
	}
	
	public static int selezionaRisorsa(int size, Class<?> c)
	{
		return InputDati.leggiIntero("Seleziona il " + c.getSimpleName().toLowerCase() + " che vuoi ricevere in prestito (0 per annullare): ", 0, size);
	}
	
	public static void copieTutteInPrestito(String titolo) 
	{
		System.out.println("Tutte le copie di \"" + titolo + "\" sono in prestito!");	
	}
	
	public static void stampaSottoCategoria(Risorsa risorsa)
	{
		System.out.println("Sottocategoria: " + risorsa.getSottoCategoria());
	}
	
	public static void piùRisorseStessoTitolo(String categoria, String titolo) 
	{
		System.out.println("Sono presenti più " + categoria + " dal titolo \"" + titolo + "\": ");	
	}
	
	public static void noRisorseDisponibili(String categoria) 
	{
		System.out.println("In archivio non sono presenti " + categoria + " disponibili");
	}
	
	public static void risorsaNonPresente(String categoria, String titolo)
	{
		System.out.println("In archivio non sono presenti " + categoria + " il cui titolo è: " + titolo );
	}
}
