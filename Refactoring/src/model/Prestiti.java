package model;

import java.io.Serializable;
import java.util.Vector;

import exceptions.RaggiunteRisorseMaxException;
import exceptions.RisorsaGi‡PossedutaException;
import interfaces.Risorsa;

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
	 * crea il prestito e lo aggiunge all'elenco 
	 * (precondizione: il fruitore non possiede gi‡ la risorsa in prestito & fruitore != null & risorsa != null)
	 * @param fruitore il fruitore che richede il prestito
	 * @param risorsa la risorsa oggetto del prestito
	 * @return true se il fruitore non ha gi‡ la risorsa in prestito (quindi prestito fattibile)
	 * @throws RaggiunteRisorseMaxException 
	 * @throws RisorsaGi‡PossedutaException 
	 */
	public void addPrestito(Fruitore fruitore, Risorsa risorsa) throws RaggiunteRisorseMaxException, RisorsaGi‡PossedutaException 
	{		
		prestitoFattibile(fruitore, risorsa);
//		se non lancia un'eccezione prosegue
		Prestito prestito = new Prestito(fruitore, risorsa);
		prestiti.add(prestito);
		risorsa.mandaInPrestito();//aggiorna il numero di copie attualmente in prestito	
	}	
	
	public void prestitoFattibile(Fruitore fruitore, Risorsa risorsa) throws RaggiunteRisorseMaxException, RisorsaGi‡PossedutaException
	{
		if(numPrestitiAttiviDi(fruitore, risorsa.getClass().getSimpleName()) == risorsa.getPrestitiMax())
		{
			throw new RaggiunteRisorseMaxException();
		}
		
		for(Prestito prestito : prestiti)
		{
			if((!prestito.isTerminato()) && prestito.getRisorsa().equals(risorsa) && prestito.getFruitore().equals(fruitore))
			{
				throw new RisorsaGi‡PossedutaException();
			}
		}
	}
	
	/**
	 * Metodo che restituisce un vettore dei prestiti che sono ancora attivi 
	 * @param prestiti
	 * @return Vector<Prestito> -> vettore dei prestiti che sono ancora attivi 
	 */
	public Vector<Prestito> prestitiAttivi() 
	{
		Vector<Prestito> prestitiAttivi = new Vector<Prestito>();
		for(Prestito prestito : prestiti)
		{
			if(!prestito.isTerminato())
			{
				prestitiAttivi.addElement(prestito);
			}	
		}
		return prestitiAttivi;
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * Metodo che restituisce un vettore di tutti i prestiti attivi di un utente
	 * @param fruitore lo username dell'utente di cui stampare i prestiti
	 */
	public Vector<Prestito> prestitiAttiviDi(Fruitore fruitore) 
	{		
		Vector<Prestito> prestitiAttiviDi = new Vector<Prestito>();
		for(Prestito prestito : prestiti)
		{
			if( (!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestitiAttiviDi.addElement(prestito);
			}
		}
		return prestitiAttiviDi;			
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
		
		if(categoria.equals("Libro"))
		{
			for(Prestito prestito : prestiti)
			{
				if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore) && prestito.getRisorsa() instanceof Libro)
				{
					risorse++;
				}
			}
		}
		else if(categoria.equals("Film"))
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
	 * quando salvo oggetti in un file e poi li ricarico, i libri di "Prestiti" non corrispondono pi˘ a quelli in "Libri" (verificato con hashcode che cambia, da 
	 * uguale prima del caricamento diventa diverso dopo il caricamento): Risorse e Prestiti vengono salvati in posti diversi e poi caricati come "diversi".
	 * Quando invece viene creato il prestito, la sua risorsa e quella in archivio sono lo stesso oggetto. Salvando e caricando in due posti diversi Ë come se "si sdoppiasse".
	 * Per non dover salvare tutto in unico file, in questo metodo ricollego gli elementi in modo da farli riferire allo stesso oggetto (tramite ID univoco):
	 * quando dico che il libro in "Prestito" torna dal prestito, si aggiornano anche le copie disponibili in "Libri"
	 */
	public void ricostruisciPrestiti(Risorse risorse)
	{
		for(Prestito prestito : prestiti) 
		{
			Risorsa risorsa = risorse.getRisorsa(prestito.getRisorsa().getId());
			
			prestito.setRisorsa(risorsa);
		}
	}
}