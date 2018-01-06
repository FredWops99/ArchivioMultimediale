package parte3;

import java.io.Serializable;
import java.util.Vector;
import myLib.BelleStringhe;
import myLib.GestioneDate;

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
	
	public void annullaPrestiti(Fruitore utente) 
	{		
		int j = 0;
//		dal fondo perchè se elimino dall'inizio si sballano le posizioni
		for(int i = prestiti.size()-1; i >= 0; i--)
		{
			if(prestiti.get(i).getFruitore().getUser().equals(utente.getUser()))
			{
				if(j == 0)
				{
					System.out.println("prestiti eliminati: ");
				}
				prestiti.get(i).getRisorsa().stampaDati(true);
				prestiti.get(i).getRisorsa().tornaDalPrestito();
				prestiti.remove(i);
				j++;
			}
		}
		if(j == 0)
		{
			System.out.println("Al momento non hai prestiti attivi");
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

	/**
	 * stampa tutti i prestiti attivi di un utente
	 * @param username lo username dell'utente di cui stampare i prestiti
	 * @return il numero di libri attualmente in prestito all'utente
	 */
	public void stampaPrestitiAttiviDi(String username) 
	{		
		int totPrestiti = 0;
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(username) && prestito.getRisorsa() instanceof Libro)
			{
				if(totPrestiti == 0)//all'inizio
				{
					System.out.println("Prestiti in corso: ");
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
	
	public int prestitiAttiviDi(String username, String categoria)
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
	public boolean prestitoFattibile(Fruitore utente, Libro libro) 
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
		
	
}
