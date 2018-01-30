package parte3;

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
	public void controlloPrestiti() 
	{
		int rimossi = 0;
		for (int i = 0; i < prestiti.size(); i++) 
		{
			if(prestiti.get(i).getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza è precedente a oggi ritorna -1
			{
				prestiti.get(i).getRisorsa().tornaDalPrestito();
				prestiti.remove(i);
				rimossi++;				
			}
		}
		System.out.println("Risorse tornate dal prestito: " + rimossi);
	}
	
	public void stampaPrestiti()
	{
		for(int i = 0; i < prestiti.size(); i++)
		{
			System.out.println(prestiti.get(i).getRisorsa().getNome());
		}
	}
	
	/**
	 * stampa tutti i prestiti attivi di un utente
	 * @param username lo username dell'utente di cui stampare i prestiti
	 */
	public void stampaPrestitiDi(String username) 
	{		
		int totPrestiti = 0;
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(username))
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
	 * annulla tutti i prestiti attivi di un utente
	 * @param utente l'utente del quale eliminare tutti i prestiti
	 */
	public void annullaPrestitiDi(Fruitore utente) 
	{		
		int j = 0;
//		dal fondo perchè se elimino dall'inizio si sballano le posizioni
		for(int i = prestiti.size()-1; i >= 0; i--)
		{
			if(prestiti.get(i).getFruitore().getUser().equals(utente.getUser()))
			{
				prestiti.get(i).getRisorsa().tornaDalPrestito();
				prestiti.remove(i);
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
	 * annulla tutti i prestiti di un insieme di utenti
	 * @param utenti l'elenco degli utenti dei quali rimuovere tutti i prestiti
	 */
	public void annullaPrestitiDi(Vector<Fruitore>utenti)
	{
		for(int i = 0; i < utenti.size(); i++)
		{
			annullaPrestitiDi(utenti.get(i));
		}
	}
	
	/**
	 * annulla tutti i prestiti relativi ad un determinato libro.
	 * @param idLibro l'id della risorsa da eliminare
	 */
	public void annullaPrestitiConLibro(int idLibro)
	{
		for(int i = 0; i < prestiti.size(); i++)
		{
			if(prestiti.get(i).getRisorsa() instanceof Libro && prestiti.get(i).getRisorsa().getId() == idLibro)
			{
				prestiti.remove(i);
			}
		}
	}
	
	/**
	 * crea e aggiunge un prestito all'elenco
	 * @param fruitore il fruitore che richiede il prestito
	 * @param risorsa la risorsa chiesta in prestito
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
	 * conta il numero di prestiti di un utente, relativi ad una categoria
	 * @param username lo username del fruitore
	 * @param categoria la categoria nella quale cercare i prestiti
	 * @return il numero di prestiti dell'utente relativi alla categoria indicata
	 */
	public int numPrestitiDi(String username, String categoria)
	{
		int risorse = 0;
		
		if(categoria.equals("Libri"))
		{
			for(Prestito prestito : prestiti)
			{
				
				if(prestito.getFruitore().getUser().equals(username) && prestito.getRisorsa() instanceof Libro)
				{
					risorse++;
				}
			}
		}
//		else if(categoria.equals("Films"))
//		{
//			for(Prestito prestito : prestiti)
//			{
//				
//				if(prestito.getFruitore().getUser().equals(username) && prestito.getRisorsa() instanceof Film)
//				{
//					risorse++;
//				}
//			}
//		}
		return risorse;
	}

	/**
	 * stampa l'elenco di tutti i prestiti attivi
	 */
	public void visualizzaTuttiPrestiti() 
	{
		if(prestiti.size()==0)
		{
			System.out.println("Al momento non sono presenti prestiti attivi");
		}
		else
		{
			for(Prestito prestito : prestiti)
			{
				System.out.println(BelleStringhe.CORNICE);
				prestito.visualizzaPrestito();
			}
		}
	}

	/**
	 * precondizione: fruitore != null & risorsa != null
	 * controlla se il fruitore possiede già in prestito la risorsa richiesta
	 * @param fruitore il fruitore che richiede il prestito
	 * @param libro la risorsa richiesta
	 * @return true se il prestito è fattibile (il fruitore non possiede già la risorsa in prestito)
	 */
	public boolean prestitoFattibile(Fruitore fruitore, Libro libro) 
	{
		for(Prestito prestito : prestiti)
		{
			if(prestito.getRisorsa().getId()==libro.getId() && prestito.getFruitore().getUser().equals(fruitore.getUser()))
			{
				return false;
			}
		}
//		se arriva qua l'utente non ha già la risorsa in prestito
		return true;
		}

	/**
	 * procedura per rinnovare un prestito
	 * @param fruitore il fruitore che richiede il rinnovo di un prestito
	 */
	public void rinnovaPrestito(Fruitore fruitore) 
	{
		Vector<Prestito>prestitiUtente = new Vector<>();
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(fruitore.getUser()))
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
					("\nSeleziona il libro per il quale chiedere il rinnovo del prestito (0 per annullare): ", 0, prestitiUtente.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiUtente.get(selezione-1);
				
				if(GestioneDate.DATA_CORRENTE.after(prestitoSelezionato.getDataRichiestaProroga()))
//				è necessariamente precedente alla data di scadenza prestito sennò sarebbe stato rimosso
				{
					prestitoSelezionato.setDataInizio(GestioneDate.DATA_CORRENTE);
					prestitoSelezionato.setProrogato(true);
				}
				else//non si può ancora rinnovare prestito
				{
					System.out.println("Il tuo prestito non è ancora rinnovabile: ");
					System.out.println("potrai effettuare questa operazione tra il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataInizio()) + 
																			" e il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataScadenza()));
				}
			}
		}
	}
}
