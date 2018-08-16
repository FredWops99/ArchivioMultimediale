package model;

import java.io.Serializable;
import java.util.Vector;
import myLib.BelleStringhe;
import myLib.GestioneDate;
import myLib.InputDati;

/**
 * Classe che racchiude l'elenco dei prestiti attivi degli utenti
 * @author Prandini Stefano
 * @author Landi Federico
 *
 */
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
	public void controlloPrestitiScaduti() 
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
					prestito.terminaPrestito();
					rimossi++;				
				}
			}
			
		}
		System.out.println("Risorse tornate dal prestito: " + rimossi);
	}
	
	/**
	 * Stampa l'elenco di tutti i prestiti attivi
	 */
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
	 * (precondizione: fruitore != null)
	 * stampa tutti i prestiti attivi di un utente
	 * @param fruitore lo username dell'utente di cui stampare i prestiti
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
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che permette al fruitore di scegliere quale dei suoi prestiti attivi terminare
	 * @param fruitore il fruitore al quale chiedere quale prestito terminare
	 */
	public void terminaPrestitoDi(Fruitore fruitore)
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
			System.out.println("Non hai prestiti attivi!");
		}
		else
		{
			System.out.println("Seleziona il prestito che vuoi terminare: ");
			
			for(int i = 0; i < prestitiUtente.size(); i++)
			{
				System.out.println("\n" + (i+1) + ") ");//stampo la posizione partendo da 1)
				System.out.println(BelleStringhe.CORNICE);
				prestitiUtente.get(i).visualizzaPrestito();
				System.out.println(BelleStringhe.CORNICE);
			}
			
			int selezione = InputDati.leggiIntero
					("\nSeleziona la risorsa della quale vuoi terminare il prestito (0 per annullare): ", 0, prestitiUtente.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiUtente.get(selezione-1);
				
				prestitoSelezionato.getRisorsa().tornaDalPrestito();
				prestitoSelezionato.terminaPrestito();
				System.out.println("Prestito terminato!");
			}
		}
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che elimina tutti i prestiti di un determinato fruitore
	 * @param fruitore il fruitore del quale eliminare tutti i prestiti
	 */
	public void terminaTuttiPrestitiDi(Fruitore fruitore) 
	{		
		int j = 0;
//		dal fondo perchè se elimino dall'inizio si sballano le posizioni
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestito.getRisorsa().tornaDalPrestito();
				prestito.terminaPrestito();
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
	
	/**
	 * (precondizione: utenti != null)
	 * permette di terminare tutti i prestiti di vari fruitori.
	 * Metodo utilizzato quando l'operatore decide che una risorsa non è più
	 * disponibile per il prestito.
	 * @param utenti gli utenti a cui verranno terminati tutti i prestiti 
	 */
	public void terminaTuttiPrestitiDi(Vector<Fruitore>utenti)
	{
		for(int i = 0; i < utenti.size(); i++)
		{
			terminaTuttiPrestitiDi(utenti.get(i));
		}
	}
	
	/**
	 * (precondizione: id != null)
	 * rimuove tutti i prestiti di una determinata risorsa
	 * (gli id dei libri sono diversi da quelli dei film (Lxxx e Fxxx)
	 * @param id l'id della risorsa
	 */
	public void annullaPrestitiConRisorsa(String id)
	{
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getRisorsa().getId().equals(id))
			{
				prestito.terminaPrestito();
			}
		}
	}
	
	/**
	 * crea ed aggiunge un prestito all'elenco 
	 * (precondizione: il fruitore non possiede già la risorsa in prestito & fruitore != null & risorsa != null)
	 * @param fruitore fruitore che richiede il prestito
	 * @param risorsa la risorsa che verrà presa in prestito dal fruitore 
	 */
	public void addPrestito(Fruitore fruitore, Risorsa risorsa)
	{
		Prestito prestito = new Prestito(fruitore, risorsa);
		prestiti.add(prestito);
		prestito.getRisorsa().mandaInPrestito();//aggiorna il numero di copie attualmente in prestito	
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
	 * (precondizione: fruitore != null)
	 * conta quanti prestiti ha il fruitore indicato, per la categoria selezionata
	 * @param fruitore il fruitore del quale contare i prestiti
	 * @param categoria la categoria nella quale cercare i prestiti 
	 * @return il numero di prestiti attivi del fruitore, per la categoria selezionata
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
	 * (precondizione: fruitore != null & risorsa != null)
	 * controlla che il fruitore non abbia già la risorsa in prestito 
	 * @param fruitore il fruitore che richede il prestito
	 * @param risorsa la risorsa oggetto del prestito
	 * @return true se il fruitore non ha già la risorsa in prestito (quindi prestito fattibile)
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
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che esegue il rinnovo di un prestito
	 * @param fruitore il fruitore che richiede il rinnovo di un prestito
	 */
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
				else if(GestioneDate.DATA_CORRENTE.after(prestitoSelezionato.getDataPerRichiestaProroga()))
//				è necessariamente precedente alla data di scadenza prestito sennò sarebbe terminato
				{
					prestitoSelezionato.prorogaPrestito();
				}
				else//non si può ancora rinnovare prestito
				{
					System.out.println("Il tuo prestito non è ancora rinnovabile: ");
					System.out.println("potrai effettuare questa operazione tra il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataPerRichiestaProroga()) + 
																			" e il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataScadenza()));
				}
			}
		}
	}
}