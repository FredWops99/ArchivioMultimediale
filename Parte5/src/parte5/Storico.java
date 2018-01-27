package parte5;

/**
	Quest’ultima versione dell’applicazione deve supportare la conservazione in archivio di
	informazioni storiche relative a:
	- fruitori, iscrizioni, rinnovi di iscrizione e decadenze;
	- risorse (ad esempio, si deve tenere traccia di risorse che sono state prestabili in
	  passato ma ora non lo sono più);
	- prestiti e proroghe degli stessi.
	
	Inoltre questa versione deve fornire una risposta ad alcune semplici interrogazioni
	dell’archivio rivolte dall’operatore, quali
	- numero di prestiti per anno solare,
	- numero di proroghe per anno solare,
	- risorsa che è stata oggetto del maggior numero di prestiti per anno solare,
	- numero di prestiti per fruitore per anno solare.
 */
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

public class Storico implements Serializable
{
	private static final String[] VOCI_MENU_STORICO = {"Numero prestiti per anno solare",
														"Numero proroghe per anno solare",
														"Risorsa che è stata oggetto del maggior numero di prestiti per anno solare",
														"Numero di prestiti per fruitore per anno solare"};
	private static final long serialVersionUID = 1L;
	
	Vector<Fruitore> storicoFruitori;
	Vector<Prestito> storicoPrestiti;
	Vector<Risorsa> storicoRisorse;
	
	public Storico()
	{
		this.storicoFruitori = new Vector<Fruitore>();
		this.storicoPrestiti = new Vector<Prestito>();
		this.storicoRisorse = new Vector<Risorsa>();
	}
	
	public void addPrestito(Fruitore fruitore, Risorsa risorsa)
	{
		Prestito prestito = new Prestito(fruitore, risorsa);
		storicoPrestiti.add(prestito);
	}
	
	public void addFruitore(Fruitore f)
	{
		storicoFruitori.add(f);
	}
	
	private int prestitiAnnoSolare()
	{
		int contatorePrestiti = 0;
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
		for (int i = 0; i < storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatorePrestiti++;
		}
		
		return contatorePrestiti;
	}
	
	private int prorogheAnnoSolare()
	{
		int contatoreProroghe = 0;
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno della proroga più vecchia
		for (int i = 0; i < storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataRichiestaProroga().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatoreProroghe++;
		}
		
		return contatoreProroghe;
	}
	
	private void risorsaPiùInPrestito()
	{
		int maxGenerale = 0;
		String titoloRisorsaMax = "";
		Vector<Risorsa> risorseAnnue = new Vector<Risorsa>();
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
		
		//seleziono solo le risorse che sono state prenotate nell'anno corrente
		for (Prestito prestito : storicoPrestiti) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				risorseAnnue.add(prestito.getRisorsa());
			}
		}
		
		//da qui in giù avviene il conteggio
		for(Risorsa risorsa : risorseAnnue)
		{
			String titoloInConisderazione = risorsa.getTitolo();
			int maxRisorsa = 0;
			for (int i = 0; i < risorseAnnue.size(); i++) 
			{
				if(titoloInConisderazione.equals(risorseAnnue.get(i).getTitolo()))
				{
					maxRisorsa++;
					risorseAnnue.remove(i);
				}
			}
			if(maxRisorsa > maxGenerale) 
			{
				maxGenerale = maxRisorsa;
				titoloRisorsaMax = risorsa.getTitolo();
			}
		}
		
		if(maxGenerale==0)
		{
			System.out.println("Non sono presenti prestiti relativi all'anno inserito");
		}
		else
		{
			System.out.println("La risorsa che ha avuto più prenotazioni è: " + titoloRisorsaMax + ", con un totale di " + maxGenerale + " prestiti");
		}
	}
	private void prestitiPerFruitore()
	{
		Vector<Prestito> prestitiAnnui = new Vector<Prestito>();
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
		
		if(storicoPrestiti.isEmpty())
		{
			System.out.println("Non sono presenti prestiti relativi all'anno inserito");
			return;
		}
		
		//seleziono solo i prestiti che sono stati effettuati nell'anno corrente
		for (Prestito prestito : storicoPrestiti) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				prestitiAnnui.add(prestito);
			}
		}
		
		//da qui in giù avviene il conteggio
		for(Prestito prestito : storicoPrestiti)
		{
			int nPrestiti = 0;
			Fruitore fruitoreInConsiderazione = prestito.getFruitore();
			for (int i = 0; i < prestitiAnnui.size(); i++) 
			{
				if(fruitoreInConsiderazione.equals(storicoPrestiti.get(i).getFruitore()))
				{
					nPrestiti++;
					prestitiAnnui.remove(i);
				}
			}
			
			System.out.println("Il fruitore " + fruitoreInConsiderazione.getUser() + " ha effettuato " + nPrestiti + " prestiti nell' anno corrente");
		}
	}

	public void menuStorico() 
	{
		boolean continuaMenuStorico = true;
		
		do
		{
			MyMenu menuStorico = new MyMenu("\nScegli cosa visualizzare: ",VOCI_MENU_STORICO, true);
			
			switch (menuStorico.scegliBase()) 
			{
				case 0:
				{
					continuaMenuStorico = false;
					break;
				}
				case 1://Numero prestiti per anno solare
				{
					System.out.println("Nell'anno solare selezionato ci sono stati " + prestitiAnnoSolare() +" prestiti");
					
					continuaMenuStorico = true;
					break;
				}
				case 2://Numero proroghe per anno solare
				{
					System.out.println("Nell'anno solare selezionato sono stati prorogati " + prorogheAnnoSolare() +" prestiti");
					
					continuaMenuStorico = true;
					break;
				}
				case 3://Risorsa che è stata oggetto del maggior numero di prestiti per anno solare
				{
					risorsaPiùInPrestito();
					
					continuaMenuStorico = true;
					break;
				}
				case 4://Numero di prestiti per fruitore per anno solare
				{
					prestitiPerFruitore();
					
					continuaMenuStorico = true;
					break;
				}
			}
		}
		while(continuaMenuStorico);
		
	}
	
//	controlla se i fruitori presenti nello storico sono decaduti ed eventualmente li segna come decaduti
//	ricalca il metodo ControlloIscrizioni in Fruitori
	
	public void controlloFruitoriStorico()
	{
		for (int i = 0; i < storicoFruitori.size(); i++) 
		{
			if(storicoFruitori.get(i).getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)
			{
				storicoFruitori.get(i).decaduto = true;
			}
		}
	}

	public void rinnovaIscrizioneInStorico(Fruitore f)
	{
		for (int i = 0; i < storicoFruitori.size(); i++)
		{
			if(f.getUser().equals(storicoFruitori.get(i).getUser()) 
					&& storicoFruitori.get(i).decaduto == false)
			{
				storicoFruitori.get(i).setDataIscrizione(GestioneDate.DATA_CORRENTE);
			}
		}
	}
}
