package controller;

import java.util.Vector;
import exceptions.RaggiunteRisorseMaxException;
import exceptions.RisorsaGi‡Posseduta;
import handler.prestiti.RichiediPrestitoHandler;
import handler.prestiti.TerminaPrestitiHandler;
import model.Fruitore;
import model.Prestiti;
import model.Prestito;
import model.Risorsa;
import myLib.GestioneDate;
import myLib.MyMenu;
import view.MessaggiSistemaView;
import view.PrestitiView;

public class PrestitiController 
{
	private final String[] CATEGORIE = {"Libri","Film"};

	Prestiti model;

	public PrestitiController(Prestiti prestiti) 
	{
		model = prestiti;
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
		PrestitiView.numeroRisorseTornateDaPrestito(rimossi);
	}
	
	public void menuRichiediPrestito(Fruitore utenteLoggato, ArchivioController archivioController) 
	{
		final String SCELTA_CATEGORIA = "scegli la categoria di risorsa: ";
		MyMenu menu = new MyMenu(SCELTA_CATEGORIA, CATEGORIE);
		int scelta = menu.scegliBase();
		
		RichiediPrestitoHandler.richiedi(scelta, utenteLoggato, this, archivioController);
	}	

	//addPrestito puÚ lanciare due eccezioni diverse: o il fruitore possiede gi‡ la risorsa o ha raggiunto il limite di risorse possedute
	public void effettuaPrestito(Fruitore utenteLoggato, Risorsa risorsa) 
	{
		try
		{
			model.addPrestito(utenteLoggato, risorsa);
			PrestitiView.prenotazioneEffettuata(risorsa);
		}
		catch(RaggiunteRisorseMaxException e)
		{
			PrestitiView.raggiunteRisorseMassime(risorsa.getSottoCategoria());
		}
		catch(RisorsaGi‡Posseduta e1)
		{
			PrestitiView.risorsaPosseduta();
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
			PrestitiView.noPrestitiAttivi();
		}
		else
		{
			for (Prestito prestito : prestitiAttivi) 
			{
				MessaggiSistemaView.cornice();
				PrestitiView.visualizzaPrestito(prestito);
			}
		}
	}
	
	//stampa i prestiti attivi di un fruitore selezionato
	public void stampaPrestitiAttiviDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttiviDi(fruitore);
		
		if(prestitiAttivi.size() == 0)
		{
			PrestitiView.noPrestitiAttivi();
		}
		else
		{
			for (Prestito prestito : prestitiAttivi) 
			{
				PrestitiView.visualizzaPrestito(prestito);
				MessaggiSistemaView.cornice();
			}
		}
	}
	
	public void menuTerminaPrestiti(Fruitore utenteLoggato, ArchivioController archivioController) 
	{
		String[] scelte = new String[] {"tutti","solo uno"};
		String messaggioEliminaPrestiti = "Vuoi eliminare tutti i prestiti o solo uno?";
		MyMenu menuPrestiti = new MyMenu(messaggioEliminaPrestiti, scelte, true);
		int scelta = menuPrestiti.scegliBase();
		
		TerminaPrestitiHandler.terminaPrestiti(scelta, utenteLoggato, this);
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che permette al fruitore di scegliere quale dei suoi prestiti attivi terminare
	 * @param fruitore il fruitore al quale chiedere quale prestito terminare
	 */
	public void terminaPrestitoDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.prestitiAttiviDi(fruitore);
		MessaggiSistemaView.cornice();	
		
		if(prestitiAttivi.size() == 0)
		{
			PrestitiView.noPrestiti();
		}
		else
		{
			PrestitiView.prestitoDaTerminare();
			
			for(int i = 0; i < prestitiAttivi.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				PrestitiView.visualizzaPrestito(prestitiAttivi.get(i));
				MessaggiSistemaView.cornice();
			}
			
			int selezione = PrestitiView.chiediRisorsaDaTerminare(prestitiAttivi.size());
			if(selezione != 0)
			{				
				(prestitiAttivi.get(selezione-1)).terminaPrestito();
				PrestitiView.prestitoTerminato();
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
			PrestitiView.noPrestiti();
		}
		else 
		{
			PrestitiView.prestitiEliminati();
		}
	}	
	
	/**
	 * (precondizione: utenti != null)
	 * permette di terminare tutti i prestiti di vari fruitori.
	 * Metodo utilizzato quando l'operatore decide che una risorsa non Ë pi˘
	 * disponibile per il prestito.
	 * @param utenti gli utenti a cui verranno terminati tutti i prestiti 
	 */
	public void terminaTuttiPrestitiDi(Vector<Fruitore>utenti)
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
			PrestitiView.noRinnovi();
		}
		else
		{
			PrestitiView.selezionaRinnovo();
			
			for(int i = 0; i < prestitiAttivi.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);				
				MessaggiSistemaView.cornice();
				PrestitiView.visualizzaPrestito(prestitiAttivi.get(i));
				MessaggiSistemaView.cornice();
			}
			
			int selezione = PrestitiView.chiediRisorsaDaRinnovare(prestitiAttivi.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiAttivi.get(selezione-1);
				
				if(prestitoSelezionato.isProrogato())
				{
					PrestitiView.prestitoGi‡Prorogato();
				}
				else if(prestitoSelezionato.isRinnovabile())
//				Ë necessariamente precedente alla data di scadenza prestito sennÚ sarebbe terminato
				{
					prestitoSelezionato.prorogaPrestito();
				}
				else//non si puÚ ancora rinnovare prestito
				{
					PrestitiView.prestitoNonRinnovabile(prestitoSelezionato);
				}
			}
		}
	}

//	public boolean raggiunteRisorseMassime(Fruitore utenteLoggato, String categoria) 
//	{
//		if(categoria == CATEGORIE[0])// == "Libri"
//		{
//			if(model.numPrestitiAttiviDi(utenteLoggato, categoria) == Libro.PRESTITI_MAX)
//			{
//				PrestitiView.raggiunteRisorseMassime(categoria);
//				return true;
//			}
//		}
//		else if(categoria == CATEGORIE[1])// == "Films"
//		{
//			if(model.numPrestitiAttiviDi(utenteLoggato, categoria) == Film.PRESTITI_MAX)
//			{
//				PrestitiView.raggiunteRisorseMassime(categoria);
//				return true;
//			}
//		}
//		return false;
//	}
}
