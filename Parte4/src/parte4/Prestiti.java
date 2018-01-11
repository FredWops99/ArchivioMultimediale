package parte4;

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
	 * @return il numero di libri attualmente in prestito all'utente
	 */
	public void stampaPrestitiDi(String username) 
	{		
		int totPrestiti = 0;
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(username) && prestito.getRisorsa() instanceof Libro)
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
	
	public void annullaPrestitiDi(Vector<Fruitore>utenti)
	{
		for(int i = 0; i < utenti.size(); i++)
		{
			annullaPrestitiDi(utenti.get(i));
		}
	}
	
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
	
	public void annullaPrestitiConFilm(int idFilm)
	{
		for(int i = 0; i < prestiti.size(); i++)
		{
			if(prestiti.get(i).getRisorsa() instanceof Film && prestiti.get(i).getRisorsa().getId() == idFilm)
			{
				prestiti.remove(i);
			}
		}
	}
	
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
		else if(categoria.equals("Films"))
		{
			for(Prestito prestito : prestiti)
			{
				
				if(prestito.getFruitore().getUser().equals(username) && prestito.getRisorsa() instanceof Film)
				{
					risorse++;
				}
			}
		}
		return risorse;
	}

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
	 * precondizione: libro è diverso da null
	 * @param utenteLoggato
	 * @param libro
	 * @return
	 */
	public boolean prestitoFattibileLibri(Fruitore utente, Libro libro) 
	{
		for(Prestito prestito : prestiti)
		{
			if(prestito.getRisorsa().getId()==libro.getId() && prestito.getFruitore().getUser().equals(utente.getUser()))
			{
				return false;
			}
		}
//		se arriva qua l'utente non ha già la risorsa in prestito
		return true;
		}
	
	public boolean prestitoFattibileFilm(Fruitore utente, Film film) 
	{
		for(Prestito prestito : prestiti)
		{
			if(prestito.getRisorsa().getId()==film.getId() && prestito.getFruitore().getUser().equals(utente.getUser()))
			{
				return false;
			}
		}
//		se arriva qua l'utente non ha già la risorsa in prestito
		return true;
		}

	public void rinnovaPrestito(Fruitore utente) 
	{
		Vector<Prestito>prestitiUtente = new Vector<>();
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(utente.getUser()))
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
