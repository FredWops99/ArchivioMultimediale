package parte5;

import java.io.Serializable;
import java.util.Vector;
import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;

public class Prestiti implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Vector<Prestito> prestiti;
	
	public Prestiti()
	{
		prestiti = new Vector<>();
	}
	
	/**
	 * controllo per tutti i prestiti presenti se sono scaduti (li rimuovo) oppure no
	 */
	public void controlloPrestiti() 
	{
		int rimossi = 0;
		for (Prestito prestito : prestiti) 
		{
//			controllo solo i prestiti che sono attivi
			if(!prestito.isTerminato())
			{
				if(prestito.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza è precedente a oggi ritorna -1
				{
					prestito.getRisorsa().tornaDalPrestito();
					prestito.ritornaPrestito();
					rimossi++;				
				}
			}
			
		}
		System.out.println("Risorse tornate dal prestito: " + rimossi);
	}
	
	public void stampaPrestitiAttivi() 
	{
		int i = 0;
		for(Prestito prestito : prestiti)
		{
			if(!prestito.isTerminato())
			{
				System.out.println(BelleStringhe.CORNICE);
				prestito.visualizzaPrestito();
				i++;
			}	
		}
		if(i == 0)
		{
			System.out.println("Al momento non sono presenti prestiti attivi");
		}
	}
	
	
	/**
	 * stampa tutti i prestiti attivi di un utente
	 * @param username lo username dell'utente di cui stampare i prestiti
	 * @return il numero di libri attualmente in prestito all'utente
	 */
	public void stampaPrestitiAttiviDi(Fruitore fruitore) 
	{		
		int totPrestiti = 0;
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				if(totPrestiti == 0)//all'inizio
				{
					System.out.println("\nPrestiti in corso: \n");
					System.out.println(BelleStringhe.CORNICE);
				}
				prestito.visualizzaPrestito();
				System.out.println(BelleStringhe.CORNICE);
				totPrestiti++;
			}
		}
		if(totPrestiti == 0)
		{
			System.out.println("Al momento non ci sono prestiti attivi");
		}
	}
	
	public void annullaPrestitiDi(Fruitore fruitore) 
	{		
		int j = 0;
//		dal fondo perchè se elimino dall'inizio si sballano le posizioni
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestito.getRisorsa().tornaDalPrestito();
				prestito.ritornaPrestito();
				j++;
			}
		}
		if(j == 0)
		{
			System.out.println("Al momento non hai prestiti attivi");
		}
		else
		{
			System.out.println("i tuoi prestiti sono stati eliminati");
		}
	}	
	
	public void annullaPrestitiDi(Vector<Fruitore>utenti)
	{
		for(int i = 0; i < utenti.size(); i++)
		{
			annullaPrestitiDi(utenti.get(i));
		}
	}
	
	/**
	 * gli id tra libri e film sono diversi (Lxxx e Fxxx)
	 * @param id
	 */
	public void annullaPrestitiConRisorsa(String id)
	{
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getRisorsa().getId().equals(id))
			{
				prestito.ritornaPrestito();
			}
		}
	}
	
	public Prestito addPrestito(Fruitore fruitore, Risorsa risorsa)
	{
		Prestito prestito = new Prestito(fruitore, risorsa);
		prestiti.add(prestito);
		prestito.getRisorsa().mandaInPrestito();//aggiorna il numero di copie attualmente in prestito	
		
		return prestito;
	}
	
	public Vector<Prestito> getPrestiti() 
	{
		return prestiti;
	}
	public void setPrestiti(Vector<Prestito> prestiti)
	{
		this.prestiti = prestiti;
	}
	
	/**
	 * non conta quanti prestiti il fruitore ha in totale ma quanti per categoria
	 * @param username
	 * @param categoria
	 * @return
	 */
	public int numPrestitiAttiviDi(Fruitore fruitore, String categoria)
	{
		int risorse = 0;
		
		if(categoria.equals("Libri"))
		{
			for(Prestito prestito : prestiti)
			{
				if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore) && prestito.getRisorsa() instanceof Libro)
				{
					risorse++;
				}
			}
		}
		else if(categoria.equals("Films"))
		{
			for(Prestito prestito : prestiti)
			{
				
				if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore) && prestito.getRisorsa() instanceof Film)
				{
					risorse++;
				}
			}
		}
		return risorse;
	}

	/**
	 * precondizione: fruitore != null & risorsa != null
	 * @param fruitore
	 * @param risorsa
	 * @return
	 */
	public boolean prestitoFattibile(Fruitore fruitore, Risorsa risorsa) 
	{
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getRisorsa().equals(risorsa) && prestito.getFruitore().equals(fruitore))
			{
				return false;
			}
		}
//		se arriva qua l'utente non ha già la risorsa in prestito
		return true;
		}

	public void rinnovaPrestito(Fruitore fruitore) 
	{
		Vector<Prestito>prestitiUtente = new Vector<>();
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestitiUtente.add(prestito);
			}
		}
		if(prestitiUtente.isEmpty())
		{
			System.out.println("Non hai prestiti attivi da rinnovare!");
		}
		else
		{
			System.out.println("Seleziona il prestito che vuoi rinnovare: ");
			
			for(int i = 0; i < prestitiUtente.size(); i++)
			{
				System.out.println("\n" + (i+1) + ") ");//stampo la posizione partendo da 1)
				System.out.println(BelleStringhe.CORNICE);
				prestitiUtente.get(i).visualizzaPrestito();
				System.out.println(BelleStringhe.CORNICE);
			}
			
			int selezione = InputDati.leggiIntero
					("\nSeleziona la risorsa per la quale chiedere il rinnovo del prestito (0 per annullare): ", 0, prestitiUtente.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiUtente.get(selezione-1);
				
				if(!prestitoSelezionato.isRinnovabile())
				{
					System.out.println("Hai già prorogato questo prestito: puoi eseguire questa azione solo una volta per ogni prestito");
				}
				else if(GestioneDate.DATA_CORRENTE.after(prestitoSelezionato.getDataRichiestaProroga()))
//				è necessariamente precedente alla data di scadenza prestito sennò sarebbe terminato
				{
					prestitoSelezionato.prorogaPrestito();
				}
				else//non si può ancora rinnovare prestito
				{
					System.out.println("Il tuo prestito non è ancora rinnovabile: ");
					System.out.println("potrai effettuare questa operazione tra il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataRichiestaProroga()) + 
																			" e il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataScadenza()));
				}
			}
		}
	}
}
