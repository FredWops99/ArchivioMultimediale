package view;

import myLib.BelleStringhe;
import myLib.InputDati;
import viewInterfaces.IMessaggiSistemaView;

public class MessaggiSistemaView implements IMessaggiSistemaView
{
//	SINGLETON: serve in molti punti e non voglio nè creare oggetto nuovo ogni volta nè farlo passare da un'oggetto all'altro
	private static MessaggiSistemaView instance;
//	costruttore privato, usato solo da getinstance()
	private MessaggiSistemaView() {}
//	se MessaggiSistemaView è già stata instanziata viene restituita l'istanza, altrimenti viene creata
	public static MessaggiSistemaView getInstance()
	{
		if(instance == null)
		{
			instance = new MessaggiSistemaView();
		}
		return instance;
	}
	
	
	private final String MESSAGGIO_ADDIO = "Grazie per aver usato ArchivioMultimediale!";
	private final String MESSAGGIO_PASSWORD = "Inserire la password per accedere all'area riservata agli operatori: ";	
	
	public void stampaAddio()
	{
		System.out.println("\n"+BelleStringhe.incornicia(MESSAGGIO_ADDIO));
	}
	
	public String chiediPasswordOperatore() 
	{
		return InputDati.leggiStringaNonVuota(MESSAGGIO_PASSWORD);	
	}

	public void accessoEseguito() 
	{
		System.out.println("Accesso eseguito con successo!");		
	}

	public void avvisoRimozioneRisorsa() 
	{
		System.out.println("ATTENZIONE! Se la risorsa che si desidera rimuovere ha copie attualmente in prestito, queste verranno sottratte ai fruitori");		
	}

	public void cornice() 
	{
		System.out.println(BelleStringhe.CORNICE);	
	}
	
	/**
	 * consente di stampare una cornice che abbia una riga vuota prima o dopo
	 * @param spazioInizio true se si vuole una riga vuota prima della cornice
	 * @param spazioFine true se si vuole una riga vuota dopo la cornice
	 */
	public void cornice(boolean spazioInizio, boolean spazioFine)
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
	
	public void stampaPosizione(int i) 
	{
		System.out.println("\n" + (i+1) + ") ");
	}
}
