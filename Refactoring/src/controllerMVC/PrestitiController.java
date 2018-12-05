package controllerMVC;

import java.util.Vector;
import exceptions.RaggiunteRisorseMaxException;
import exceptions.RisorsaGi‡PossedutaException;
import interfaces.Risorsa;
import model.Fruitore;
import model.Prestiti;
import model.Prestito;
import myLib.GestioneDate;
import view.MessaggiSistemaView;
import view.PrestitiView;
import viewInterfaces.IMessaggiSistemaView;
import viewInterfaces.IPrestitiView;

public class PrestitiController 
{
	private Prestiti model;
	private IPrestitiView prestitiView;
	private IMessaggiSistemaView messaggiSistemaView;

	public PrestitiController(Prestiti prestiti) 
	{
		model = prestiti;
		prestitiView = new PrestitiView();
		messaggiSistemaView = new MessaggiSistemaView();
	}
	
	public IPrestitiView getPrestitiView() 
	{
		return prestitiView;
	}
	
	/**
	 * controllo per tutti i prestiti presenti se sono scaduti (li rimuovo) oppure no
	 * @return numero prestiti rientrati dal prestito
	 */
	public void controlloPrestitiScaduti() 
	{
		int rimossi = 0;
		for (Prestito prestito : model.getPrestiti()) 
		{
//			controllo solo i prestiti che sono attivi
			if(!prestito.isTerminato())
			{
				if(prestito.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza Ë precedente a oggi ritorna -1
				{
					prestito.getRisorsa().tornaDalPrestito();
					prestito.terminaPrestito();
					rimossi++;				
				}
			}	
		}
		prestitiView.numeroRisorseTornateDaPrestito(rimossi);
	}

	//addPrestito puÚ lanciare due eccezioni diverse: o il fruitore possiede gi‡ la risorsa o ha raggiunto il limite di risorse possedute
	public void effettuaPrestito(Fruitore fruitore, Risorsa risorsa) 
	{
		try
		{
			model.addPrestito(fruitore, risorsa);
			prestitiView.prenotazioneEffettuata(risorsa);
		}
		catch(RaggiunteRisorseMaxException e)
		{
			prestitiView.raggiunteRisorseMassime(risorsa.getSottoCategoria());
		}
		catch(RisorsaGi‡PossedutaException e1)
		{
			prestitiView.risorsaPosseduta();
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
		for(Prestito prestito : model.getPrestiti())
		{
			if((!prestito.isTerminato()) && prestito.getRisorsa().getId().equals(id))
			{
				prestito.terminaPrestito();
			}
		}
	}
	
	//stampai prestiti attivi
	public void stampaPrestitiAttivi()
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttivi();
		if(prestitiAttivi.size() == 0)
		{
			prestitiView.noPrestitiAttivi();
		}
		else
		{
			for (Prestito prestito : prestitiAttivi) 
			{
				messaggiSistemaView.cornice();
				prestitiView.visualizzaPrestito(prestito);
			}
		}
	}
	
	//stampa i prestiti attivi di un fruitore selezionato
	public void stampaPrestitiAttiviDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttiviDi(fruitore);
		
		if(prestitiAttivi.size() == 0)
		{
			prestitiView.noPrestitiAttivi();
		}
		else
		{
			for (Prestito prestito : prestitiAttivi) 
			{
				prestitiView.visualizzaPrestito(prestito);
				messaggiSistemaView.cornice();
			}
		}
	}
	
//	public void menuTerminaPrestiti(Fruitore utenteLoggato, ArchivioController archivioController) 
//	{
		
//	}
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che permette al fruitore di scegliere quale dei suoi prestiti attivi terminare
	 * @param fruitore il fruitore al quale chiedere quale prestito terminare
	 */
	public void terminaPrestitoDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttiviDi(fruitore);
		messaggiSistemaView.cornice();	
		
		if(prestitiAttivi.size() == 0)
		{
			prestitiView.noPrestiti();
		}
		else
		{
			prestitiView.prestitoDaTerminare();
			
			for(int i = 0; i < prestitiAttivi.size(); i++)
			{
				messaggiSistemaView.stampaPosizione(i);
				messaggiSistemaView.cornice();
				prestitiView.visualizzaPrestito(prestitiAttivi.get(i));
				messaggiSistemaView.cornice();
			}
			
			int selezione = prestitiView.chiediRisorsaDaTerminare(prestitiAttivi.size());
			if(selezione != 0)
			{				
				(prestitiAttivi.get(selezione-1)).terminaPrestito();
				prestitiView.prestitoTerminato();
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
		int rimossi = 0;
		for(Prestito prestito : model.getPrestiti())
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestito.terminaPrestito();
				rimossi++;
			}
		}
		if(rimossi == 0)
		{
			prestitiView.noPrestiti();
		}
		else 
		{
			prestitiView.prestitiEliminati();
		}
	}	
	
	/**
	 * (precondizione: utenti != null)
	 * permette di terminare tutti i prestiti di vari fruitori.
	 * Metodo utilizzato quando l'operatore decide che una risorsa non Ë pi˘
	 * disponibile per il prestito.
	 * @param utenti gli utenti a cui verranno terminati tutti i prestiti 
	 */
	public void terminaTuttiPrestitiDi(Vector<Fruitore> utenti)
	{
		for(Fruitore fruitore : utenti)
		{
			terminaTuttiPrestitiDi(fruitore);
		}
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che esegue il rinnovo di un prestito
	 * @param fruitore il fruitore che richiede il rinnovo di un prestito
	 */
	public void rinnovaPrestito(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttiviDi(fruitore);
		
		if(prestitiAttivi.isEmpty())
		{
			prestitiView.noRinnovi();
		}
		else
		{
			prestitiView.selezionaRinnovo();
			
			for(int i = 0; i < prestitiAttivi.size(); i++)
			{
				messaggiSistemaView.stampaPosizione(i);				
				messaggiSistemaView.cornice();
				prestitiView.visualizzaPrestito(prestitiAttivi.get(i));
				messaggiSistemaView.cornice();
			}
			
			int selezione = prestitiView.chiediRisorsaDaRinnovare(prestitiAttivi.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiAttivi.get(selezione-1);
				
				if(prestitoSelezionato.isProrogato())
				{
					prestitiView.prestitoGi‡Prorogato();
				}
				else if(prestitoSelezionato.isRinnovabile())
//				Ë necessariamente precedente alla data di scadenza prestito sennÚ sarebbe terminato
				{
					prestitoSelezionato.prorogaPrestito();
				}
				else//non si puÚ ancora rinnovare prestito
				{
					prestitiView.prestitoNonRinnovabile(prestitoSelezionato);
				}
			}
		}
	}
}
