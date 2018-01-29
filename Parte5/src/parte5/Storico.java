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
														"Iscrizioni rinnovate", "Prestiti prorogati", "Prestiti terminati", "Prestiti terminati in anticipo"};
	private static final long serialVersionUID = 1L;
	
	public static void menuStorico(Prestiti prestiti,Archivio archivio, Fruitori fruitori) 
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
					risorsePrestabili(archivio);
					
					continuaMenuStorico = true;
					break;
				}
				case 6://iscrizioni decadute
				{
					fruitoriDecaduti(fruitori);
					
					continuaMenuStorico = true;
					break;
				}
				case 7://iscrizioni rinnovate
				{
					fruitoriRinnovati(fruitori);
					
					continuaMenuStorico = true;
					break;
				}
				case 8://prestiti prorogati
				{
					prestitiProrogati(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 9://prestiti scaduti
				{
					prestitiTerminati(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 10://prestiti terminati in anticipo
				{
					prestitiTerminatiInAnticipo(prestiti);
					
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
			if(prestito.isProrogato() && prestito.getDataRitorno().get(GregorianCalendar.YEAR) == annoSelezionato)
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
		for(int i = 0; i < risorseAnnue.size(); i++)
		{
			String titoloInConisderazione = risorseAnnue.get(i).getTitolo();
			int maxRisorsa = 0;
			for (int j = 0; j < risorseAnnue.size(); j++) 
			{
				if(titoloInConisderazione.equals(risorseAnnue.get(j).getTitolo()))
				{
					maxRisorsa++;
					risorseAnnue.remove(j);
				}
			}
			if(maxRisorsa > maxGenerale) 
			{
				maxGenerale = maxRisorsa;
				titoloRisorsaMax = risorseAnnue.get(i).getTitolo();
			}
		}
		
		if(maxGenerale==0)
		{
			System.out.println("Nell'anno selezionato non sono stati richiesti prestiti");
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
		if(prestitiAnnui.isEmpty())
		{
			System.out.println("Nell'anno selezionato non sono stati richiesti prestiti");
		}
		else
		{
			for(int j = 0; j < prestitiAnnui.size(); j++)
			{
				int nPrestiti = 0;
				Fruitore fruitoreInConsiderazione = prestiti.getPrestiti().get(j).getFruitore();
//				conteggio al contrario così quando elimino non salto elementi
				for (int i = prestitiAnnui.size()-1; i >= j; i--)
				{
					if(fruitoreInConsiderazione.equals(prestitiAnnui.get(i).getFruitore()))
					{
						nPrestiti++;
						prestitiAnnui.remove(i);
					}
				}
				System.out.println("L'utente " + fruitoreInConsiderazione.getUser() + " ha richiesto " + nPrestiti + " prestiti nell'anno selezionato");
			}
		}
	}
	
	private static void risorsePrestabili(Archivio archivio)
	{
		Vector<Libro> libri = archivio.getLibri().getLibri();
		Vector<Film>  films = archivio.getFilms().getfilms();
		
		System.out.println("Libri che erano disponibili al prestito e che ora non lo sono più: ");
		int numL = 0;
		for(int i = 0; i < libri.size(); i++)
		{
			if(!libri.get(i).isPrestabile())
			{
				System.out.println("- " + libri.get(i).getTitolo());
				numL++;
			}
		}
		if(numL == 0)
		{
			System.out.println("Nessuno");
		}
		System.out.println(); //NEW LINE
		System.out.println("Film che erano disponibili al prestito e che ora non lo sono più: ");
		int numF = 0;
		for(int i = 0; i < films.size(); i++)
		{
			if(!films.get(i).isPrestabile())
			{
				System.out.println("- " + films.get(i).getTitolo());
				numF++;
			}
		}
		if(numF == 0)
		{
			System.out.println("Nessuno");
		}
	}
	private static void fruitoriDecaduti(Fruitori fruitori)
	{
		
		int num = 0;
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(fruitori.getFruitori().get(i).isDecaduto())
			{
//				lo stampa solo al primo fruitori con iscrizione decaduta che trova
				if(num == 0)
				{
					System.out.println("Fruitori decaduti: \n");
				}
				System.out.println("- " + fruitori.getFruitori().get(i).getNome() + " " + fruitori.getFruitori().get(i).getCognome()
						+ " (il " + GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getDataScadenza()) + ")");
				num++;
			}
		}
		if(num == 0)
		{
			System.out.println("Nessuna iscrizione è ancora decaduta");
		}
	}
	
	private static void fruitoriRinnovati(Fruitori fruitori)
	{
		int num = 0;
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(!fruitori.getFruitori().get(i).getRinnovi().isEmpty())
			{
//				lo stampa solo al primo fruitori con rinnovi che trova
				if(num == 0)
				{
					System.out.println("Iscrizioni rinnovate: \n");
				}
				num++;
				System.out.println("Il fruitore " + fruitori.getFruitori().get(i).getUser() + " ha rinnovato la sua iscrizione nelle seguenti date:");
				for (int j = 0; i < fruitori.getFruitori().get(i).getRinnovi().size(); j++) 
				{
					System.out.println("- "+ GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getRinnovi().get(j)));
				}
			}
		}
		if(num == 0)
		{
			System.out.println("Nessun fruitore ha ancora rinnovato la sua iscrizione");
		}
	}
	
	private static void prestitiProrogati(Prestiti prestiti)
	{
		int num = 0;
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isProrogato())
			{
				if(num == 0)
				{
//					lo stampa solo al primo prestito rinnovato che trova
					System.out.println("Prestiti che sono stati rinnovati: \n");
				}
				num++;
				System.out.println("- "+ prestiti.getPrestiti().get(i).getRisorsa().getTitolo() 
						+ " (il" + GestioneDate.visualizzaData(prestiti.getPrestiti().get(i).getDataRichiestaProroga()) + ")");
			}
		}
		if(num == 0)
		{
			System.out.println("Nessun prestito è ancora stato rinnovato");
		}
	}
	private static void prestitiTerminati(Prestiti prestiti)
	{
		int num = 0;
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isTerminato())
			{
//				lo stampa solo al primo prestito terminato che trova
				if(num == 0)
				{
					System.out.println("Prestiti che sono terminati: \n");
				}
				num++;
				String risorsa = prestiti.getPrestiti().get(i).getRisorsa().getClass().getSimpleName();
				System.out.println(risorsa + ": "+ prestiti.getPrestiti().get(i).getRisorsa().getTitolo() +
									", Fruitore: " + prestiti.getPrestiti().get(i).getFruitore().getUser()
									+ " (il " + GestioneDate.visualizzaData(prestiti.getPrestiti().get(i).getDataRitorno()) + ")");
			}
		}
		if(num == 0)
		{
			System.out.println("Nessun prestito è ancora terminato");
		}
	}
	
	private static void prestitiTerminatiInAnticipo(Prestiti prestiti) 
	{
		int num = 0;
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.isTerminato() && prestito.getDataRitorno().before(prestito.getDataScadenza()))
			{
				if(num == 0)
				{
					System.out.println("Prestiti terminati prima della data di scandenza: ");
				}
				num++;
				String risorsa = prestito.getRisorsa().getClass().getSimpleName();
				System.out.println(risorsa + ": "+ prestito.getRisorsa().getTitolo() + ", Fruitore: " + prestito.getFruitore().getUser()
									+ " (terminato il " + GestioneDate.visualizzaData(prestito.getDataRitorno()) + 
									", scandenza il " + GestioneDate.visualizzaData(prestito.getDataScadenza()) + ")");
			}
		}
		if(num == 0)
		{
			System.out.println("Nessun prestito è terminato prima della sua data di scandenza");
		}
	}
}
