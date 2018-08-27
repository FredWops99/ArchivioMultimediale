package model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che racchiude l'elenco dei prestiti attivi degli utenti
 * @author Prandini Stefano
 * @author Landi Federico
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
	 * Metodo che restituisce un vettore dei prestiti che sono ancora attivi 
	 * @param prestiti
	 * @return Vector<Prestito> -> vettore dei prestiti che sono ancora attivi 
	 */
	public Vector<Prestito> stampaPrestitiAttivi(Prestiti prestiti) 
	{
		int i = 0;
		Vector<Prestito> prestitiAttivi = new Vector<Prestito>();
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(!prestito.isTerminato())
			{
				prestitiAttivi.addElement(prestito);
				//MessaggiSistemaView.cornice();
				//prestito.visualizzaPrestito();
				i++;
			}	
		}
		if(i == 0)
		{
			return null;
			//PrestitiView.noPrestitiAttivi();
		}
		else
		{
			return prestitiAttivi;
		}
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * Metodo che restituisce un vettore di tutti i prestiti attivi di un utente
	 * @param fruitore lo username dell'utente di cui stampare i prestiti
	 */
	public Vector<Prestito> stampaPrestitiAttiviDi(Fruitore fruitore,Prestiti prestiti) 
	{		
		int totPrestiti = 0;
		Vector<Prestito> prestitiAttiviDi = new Vector<Prestito>();
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				
				prestitiAttiviDi.addElement(prestito);			
				//prestito.visualizzaPrestito();
				//MessaggiSistemaView.cornice();	
				totPrestiti++;
			}
		}
		if(totPrestiti == 0)
		{
			return null;
			//PrestitiView.noPrestitiAttivi();			
		}
		else
		{
			return prestitiAttiviDi;			
		}
	}
	
	public void terminaPrestitoSelezionato(int selezione,Vector<Prestito> prestitiUtente)
	{
		Prestito prestitoSelezionato = prestitiUtente.get(selezione-1);
					
		prestitoSelezionato.getRisorsa().tornaDalPrestito();
		prestitoSelezionato.terminaPrestito();
		
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
	
	public void rinnovaPrestito(Prestito prestitoSelezionato) 
	{

		prestitoSelezionato.prorogaPrestito();
	}
}