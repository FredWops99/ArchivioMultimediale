package parte4;

/**
 * Quest’ultima versione dell’applicazione deve supportare la conservazione in archivio di
informazioni storiche relative a:
	- fruitori, iscrizioni, rinnovi di iscrizione e decadenze;
	- risorse (ad esempio, si deve tenere traccia di risorse che sono state prestabili in
	  passato ma ora non lo sono più);
	- prestiti e proroghe degli stessi.
	
	-----------------
	
	Inoltre questa versione deve fornire una risposta ad alcune semplici interrogazioni
dell’archivio rivolte dall’operatore, quali
- numero di prestiti per anno solare,
- numero di proroghe per anno solare,
- risorsa che è stata oggetto del maggior numero di prestiti per anno solare,
- numero di prestiti per fruitore per anno solare.
 */
import java.io.Serializable;
import java.util.Vector;
import myLib.GestioneDate;
import myLib.InputDati;
import myLib.MyMenu;

public class Storico implements Serializable
{
	private static final String[] VOCI_MENU_STORICO = {"Visualizza prestiti avvenuti in un anno solare",
														"Visualizza proroghe avvenute in un anno solare",
														"Visualizza la risorsa che è stata oggetto del maggior numero di prestiti in un anno solare",
														"Visualizza il numero di prestiti per fruitore per un anno solare"};
	private static final long serialVersionUID = 1L;
	
	Vector<Fruitore> storicoFruitori;
	Vector<Prestito> storicoPrestiti;
	Vector<Risorsa> storicoRisorse;
	
	void Storico()
	{
		this.storicoFruitori = new Vector<Fruitore>();
		this.storicoPrestiti = new Vector<Prestito>();
		this.storicoRisorse = new Vector<Risorsa>();
	}
	
	public int prestitiAnnoSolare()
	{
		int contatorePrestiti = 0;
		String annoSelezionato = InputDati.leggiStringaNonVuota("Inserisci l'anno: ");
		for (int i=0; i<storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataInizio().YEAR == Integer.parseInt(annoSelezionato)) 
				contatorePrestiti++;
		}
		
		return contatorePrestiti;
	}
	
	public int prorogheAnnoSolare()
	{
		int contatoreProroghe = 0;
		String annoSelezionato = InputDati.leggiStringaNonVuota("Inserisci l'anno: ");
		for (int i=0; i<storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataRichiestaProroga().YEAR == Integer.parseInt(annoSelezionato)) 
				contatoreProroghe++;
		}
		
		return contatoreProroghe;
	}
	
	public void risorsaPiùInPrestito()
	{
		int max2 = 0;
		String risorsaMax = "";
		Vector<Risorsa> risorseAnnue = new Vector<Risorsa>();
		String annoSelezionato = InputDati.leggiStringaNonVuota("Inserisci l'anno: ");
		
		//seleziono solo le risorse che sono state prenotate nell'anno corrente
		for (int i=0; i<storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataInizio().YEAR == Integer.parseInt(annoSelezionato))
			{
				risorseAnnue.add(storicoPrestiti.get(i).getRisorsa());
			}
		}
		
		//da qui in giù avviene il conteggio
		for(int i=0; i<risorseAnnue.size();i++)
		{
			String titoloInConisderazione = risorseAnnue.get(i).getTitolo();
			int max1 = 0;
			for (int j=0; j<risorseAnnue.size();j++) 
			{
				if(titoloInConisderazione.equals(risorseAnnue.get(j).getTitolo()))
				{
					max1++;
					risorseAnnue.remove(j);
				}
			}
			if(max1 > max2) 
			{
				max2 = max1;
				risorsaMax = risorseAnnue.get(i).getTitolo();
			}
		}
		
		System.out.println("La risorsa che ha avuto più prenotazioni è: "+risorsaMax+ 
							" con un totale di "+max2+" prestiti");
	}
	public void prestitiPerFruitore()
	{
		Vector<Prestito> prestitiAnnui = new Vector<Prestito>();
		String annoSelezionato = InputDati.leggiStringaNonVuota("Inserisci l'anno: ");
		
		//seleziono solo i prestiti che sono stati effettuati nell'anno corrente
		for (int i=0; i<storicoPrestiti.size(); i++) 
		{
			if(storicoPrestiti.get(i).getDataInizio().YEAR == Integer.parseInt(annoSelezionato))
			{
				prestitiAnnui.add(storicoPrestiti.get(i));
			}
		}
		
		//da qui in giù avviene il conteggio
		for(int i=0;i<prestitiAnnui.size();i++)
		{
			int nPrestiti = 0;
			Fruitore fruitoreInConsiderazione = storicoPrestiti.get(i).getFruitore();
			for (int j=0;j<prestitiAnnui.size();j++) 
			{
				if(fruitoreInConsiderazione.equals(storicoPrestiti.get(j)))
				{
					nPrestiti++;
					prestitiAnnui.remove(j);
				}
			}
			
			System.out.println("Il fruitore "+fruitoreInConsiderazione.getUser()+" ha effettuato "+
									nPrestiti+ " nell' anno corrente");
			
		}
	}

	public void menuStorico() 
	{
		MyMenu menuStorico = new MyMenu("Storico: ",VOCI_MENU_STORICO);
		
		switch (menuStorico.scegliBase()) 
		{
			case 0:
			{
				break;
			}
			case 1:
			{
				System.out.println("Ci sono stati nell'anno solare da te selezionato " + prestitiAnnoSolare() +" prestiti");
				break;
			}
			case 2:
			{
				System.out.println("Ci sono state nell'anno solare da te selezionato " + prorogheAnnoSolare() +" proroghe");
				break;
			}
			case 3:
			{
				risorsaPiùInPrestito();
				break;
			}
			case 4:
			{
				prestitiPerFruitore();
				break;
			}
		}
	}
}
