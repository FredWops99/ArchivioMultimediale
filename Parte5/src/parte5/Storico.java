package parte5;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

/**
 * Classe con metodi statici (non serve istanziare un oggetto Storico) che serve per gestire le informazioni storiche dell'archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Storico implements Serializable
{
	private static final String[] VOCI_MENU_STORICO = {"Numero prestiti per anno solare", "Numero proroghe per anno solare",
														"Risorsa che è stata oggetto del maggior numero di prestiti per anno solare",
														"Numero di prestiti per fruitore per anno solare", "Risorse prestabili in passato", "Iscrizioni decadute",
														"Iscrizioni rinnovate", "Prestiti prorogati", "Prestiti scaduti", "Prestiti terminati in anticipo"};
	private static final long serialVersionUID = 1L;
	
	public static void menuStorico(Prestiti prestiti) 
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
					System.out.println("Nell'anno solare selezionato ci sono stati " + prestitiAnnoSolare(prestiti) +" prestiti");
					
					continuaMenuStorico = true;
					break;
				}
				case 2://Numero proroghe per anno solare
				{
					System.out.println("Nell'anno solare selezionato sono stati prorogati " + prorogheAnnoSolare(prestiti) +" prestiti");
					
					continuaMenuStorico = true;
					break;
				}
				case 3://Risorsa che è stata oggetto del maggior numero di prestiti per anno solare
				{
					risorsaPiùInPrestito(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 4://Numero di prestiti per fruitore per anno solare
				{
					prestitiAnnuiPerFruitore(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 5://risorse prestabili in passato
				{
					
					continuaMenuStorico = true;
					break;
				}
				case 6://iscrizioni decadute
				{
					
					continuaMenuStorico = true;
					break;
				}
				case 7://iscrizioni rinnovate
				{
					
					continuaMenuStorico = true;
					break;
				}
				case 8://prestiti prorogati
				{
					
					continuaMenuStorico = true;
					break;
				}
				case 9://prestiti scaduti
				{
					
					continuaMenuStorico = true;
					break;
				}
				case 10://prestiti terminati in anticipo
				{
					
					continuaMenuStorico = true;
					break;
				}
			}
		}
		while(continuaMenuStorico);
	}
	
	private static int prestitiAnnoSolare(Prestiti prestiti)
	{
		int contatorePrestiti = 0;
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatorePrestiti++;
		}
		
		return contatorePrestiti;
	}
	
	private static int prorogheAnnoSolare(Prestiti prestiti)
	{
		int contatoreProroghe = 0;
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno della proroga più vecchia
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataRichiestaProroga().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatoreProroghe++;
		}
		
		return contatoreProroghe;
	}
	
	private static void risorsaPiùInPrestito(Prestiti prestiti)
	{
		int maxGenerale = 0;
		String titoloRisorsaMax = "";
		Vector<Risorsa> risorseAnnue = new Vector<Risorsa>();
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
		
		//seleziono solo le risorse che sono state prenotate nell'anno corrente
		for (Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				risorseAnnue.add(prestito.getRisorsa());
			}
		}
		//da qui avviene il conteggio
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
	private static void prestitiAnnuiPerFruitore(Prestiti prestiti)
	{
		Vector<Prestito> prestitiAnnui = new Vector<Prestito>();
		int annoSelezionato = InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio		
		//seleziono solo i prestiti che sono stati effettuati nell'anno indicato
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				prestitiAnnui.add(prestito);
			}
		}
		//da qui avviene il conteggio
		for(Prestito prestito : prestitiAnnui)
		{
			int nPrestiti = 0;
			Fruitore fruitoreInConsiderazione = prestito.getFruitore();
//			conteggio al contrario così quando elimino non salto elementi
			for (int i = prestitiAnnui.size()-1; i >= 0; i--) 
			{
				if(fruitoreInConsiderazione.equals(prestitiAnnui.get(i).getFruitore()))
				{
					nPrestiti++;
					prestitiAnnui.remove(i);
				}
			}
			if(nPrestiti == 0)
			{
				System.out.println("Il fruitore " + fruitoreInConsiderazione.getUser() + " non ha richiesto prestiti nell'anno selezionato");
			}
			else
			{
				System.out.println("Il fruitore " + fruitoreInConsiderazione.getUser() + " ha richiesto " + nPrestiti + " prestiti nell' anno selezionato");
			}
		}
	}
}
