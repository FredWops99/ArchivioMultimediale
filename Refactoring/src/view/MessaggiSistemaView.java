package view;

import myLib.BelleStringhe;
import myLib.InputDati;

public class MessaggiSistemaView 
{
	private static final String MESSAGGIO_ADDIO = "Grazie per aver usato ArchivioMultimediale!";
	private static final String MESSAGGIO_PASSWORD = "Inserire la password per accedere all'area riservata agli operatori: ";	
	
	public static void stampaAddio()
	{
		System.out.println("\n"+BelleStringhe.incornicia(MESSAGGIO_ADDIO));
	}
	
	public static String chiediPasswordOperatore() 
	{
		return InputDati.leggiStringaNonVuota(MESSAGGIO_PASSWORD);	
	}

	public static void accessoEseguito() 
	{
		System.out.println("Accesso eseguito con successo!");		
	}

//	va in RisorsaView?
	public static void avvisoRimozioneRisorsa() 
	{
		System.out.println("ATTENZIONE! Se la risorsa che si desidera rimuovere ha copie attualmente in prestito, queste verranno sottratte ai fruitori");		
	}

	public static void cornice() 
	{
		System.out.println(BelleStringhe.CORNICE);	
	}
	
	/**
	 * consente di stampare una cornice che abbia una riga vuota prima o dopo
	 * @param spazioInizio true se si vuole una riga vuota prima della cornice
	 * @param spazioFine true se si vuole una riga vuota dopo la cornice
	 */
	public static void cornice(boolean spazioInizio, boolean spazioFine)
	{
		if(!spazioInizio && !spazioFine)
		{
			cornice();
		}
		else if(spazioInizio && !spazioFine)
		{
			System.out.println();
			cornice();
		}
		else if(!spazioInizio && spazioFine)
		{
			cornice();
			System.out.println();
		}
		else
		{
			System.out.println();
			cornice();
			System.out.println();
		}
	}
	
	public static void stampaPosizione(int i) 
	{
		System.out.println("\n" + (i+1) + ") ");
	}
}
