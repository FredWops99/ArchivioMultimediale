package view;

import interfaces.Risorsa;
import myLib.InputDati;
import viewInterfaces.IRisorseView;

public abstract class RisorseView implements IRisorseView
{	
	public void stampaDati(Risorsa risorsa, boolean perPrestito)
	{
		System.out.println(risorsa.toString(perPrestito));
	}
	
	public String chiediTitolo()
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo: ");
	}
	
	public int chiediNumeroLicenze() 
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
	}
	
	public void aggiuntaRiuscita(Class<?> c) 
	{
		System.out.println(c.getSimpleName() + " aggiunto con successo!");	
	}
	
	public void aggiuntaNonRiuscita(Class<?> c)
	{
		System.out.println("Il " + c.getSimpleName().toLowerCase() + " è già presente in archivio");
	}
	
	public String chiediRisorsaDaRimuovere() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo della risorsa da rimuovere: ");
	}
	
	public void risorsaNonPresente()
	{
		System.out.println("Siamo spiacenti, la risorsa non è presente nell'archivio");
	}
	
	public void rimozioneAvvenuta()
	{
		System.out.println("La risorsa è stata eliminata dalle risorse prestabili (verrà conservata in archivio per motivi storici)");
	}
	
	public void numeroRicorrenza(int pos) 
	{
		System.out.println("\nRicorrenza " + ++pos + ":");
	}
	
	public int chiediRicorrenzaDaRimuovere(int size) 
	{
		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, size);
	}
	
	public void nessunaCorrispondenza(Class<?> c)
	{
		System.out.println("Nessun " + c.getSimpleName().toLowerCase() + " corrispondente al criterio di ricerca");
	}
	
	public void unaRisorsaInArchivio(Class<?> c)
	{
		System.out.println("\nE' presente un " + c.getSimpleName().toLowerCase() + " in archivio: ");
	}
	
	public void numeroRisorseInArchivio(int size, String s)
	{
		System.out.println("\nSono presenti " + size + " " + s + " in archivio: ");
	}
	
	public void stampaTitolo(Risorsa risorsa) 
	{
		System.out.println("titolo: " + risorsa.getTitolo());
	}
	
	public void risorseInArchivio(Class<?> c) 
	{
		System.out.println("\n" + c.getSimpleName() + " in archivio: \n");	
	}
	
	public int selezionaRisorsa(int size, Class<?> c)
	{
		return InputDati.leggiIntero("Seleziona il " + c.getSimpleName().toLowerCase() + " che vuoi ricevere in prestito (0 per annullare): ", 0, size);
	}
	
	public void copieTutteInPrestito(String titolo) 
	{
		System.out.println("Tutte le copie di \"" + titolo + "\" sono in prestito!");	
	}
	
	public void stampaSottoCategoria(Risorsa risorsa)
	{
		System.out.println("Sottocategoria: " + risorsa.getSottoCategoria());
	}
	
	public void piùRisorseStessoTitolo(String categoria, String titolo) 
	{
		System.out.println("Sono presenti più " + categoria + " dal titolo \"" + titolo + "\": ");	
	}
	
	public void noRisorseDisponibili(String categoria) 
	{
		System.out.println("In archivio non sono presenti " + categoria + " disponibili");
	}
	
	public void risorsaNonPresente(String categoria, String titolo)
	{
		System.out.println("In archivio non sono presenti " + categoria + " il cui titolo è: " + titolo );
	}
}
