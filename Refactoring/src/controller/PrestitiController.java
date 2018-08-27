package controller;

import java.util.Vector;
import model.Film;
import model.Fruitore;
import model.Libro;
import model.Prestiti;
import model.Prestito;
import model.Risorsa;
import myLib.GestioneDate;
import view.MessaggiSistemaView;
import view.PrestitiView;

public class PrestitiController 
{
	private static final String[] CATEGORIE = {"Libri","Film"};

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
				if(prestito.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza è precedente a oggi ritorna -1
				{
					prestito.getRisorsa().tornaDalPrestito();
					prestito.terminaPrestito();
					rimossi++;				
				}
			}	
		}
		PrestitiView.numeroRisorseTornateDaPrestito(rimossi);
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
	
//	//stampa il numero di risorse che sono rientrate dal prestito
//	public void controlloPrestitiScaduti()
//	{
//		PrestitiView.numeroRisorseTornateDaPrestito(model.controlloPrestitiScaduti());
//	}
	
	//stampai prestiti attivi
	public void stampaPrestitiAttivi()
	{
		Vector<Prestito> prestitiAttivi = model.stampaPrestitiAttivi(model);
		if(prestitiAttivi==null)
		{
			PrestitiView.noPrestitiAttivi();
		}
		else
		{
			for (int i=0;i<prestitiAttivi.size();i++) 
			{
				MessaggiSistemaView.cornice();
				prestitiAttivi.get(i).visualizzaPrestito();
			}
		}
	}
	
	//stampai prestiti attivi di un fruitore selezionato
	public void stampaPrestitiAttiviDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.stampaPrestitiAttiviDi(fruitore,model);
		System.out.println("\nPrestiti in corso: \n");
		MessaggiSistemaView.cornice();	
		
		if(prestitiAttivi==null)
		{
			PrestitiView.noPrestitiAttivi();
		}
		else
		{
			for (int i=0;i<prestitiAttivi.size();i++) 
			{
				prestitiAttivi.get(i).visualizzaPrestito();
				MessaggiSistemaView.cornice();
			}
		}
	}
	
	/**
	 * (precondizione: fruitore != null)
	 * metodo che permette al fruitore di scegliere quale dei suoi prestiti attivi terminare
	 * @param fruitore il fruitore al quale chiedere quale prestito terminare
	 */
	public void terminaPrestitoDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.stampaPrestitiAttiviDi(fruitore,model);
		MessaggiSistemaView.cornice();	
		
		if(prestitiAttivi==null)
		{
			PrestitiView.noPrestitiAttivi();
		}
		else
		{
				PrestitiView.prestitoDaTerminare();
				
				for(int i = 0; i < prestitiAttivi.size(); i++)
				{
					MessaggiSistemaView.stampaPosizione(i);
					MessaggiSistemaView.cornice();
					prestitiAttivi.get(i).visualizzaPrestito();
					MessaggiSistemaView.cornice();
				}
				
				int selezione = PrestitiView.chiediRisorsaDaTerminare(prestitiAttivi.size());
				if(selezione != 0)
				{
					model.terminaPrestitoSelezionato(selezione,prestitiAttivi);
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
//		dal fondo perchè se elimino dall'inizio si sballano le posizioni
		for(Prestito prestito : model.getPrestiti())
		{
			if((!prestito.isTerminato()) && prestito.getFruitore().equals(fruitore))
			{
				prestito.getRisorsa().tornaDalPrestito();
				prestito.terminaPrestito();
			}
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
	 * (precondizione: fruitore != null)
	 * metodo che esegue il rinnovo di un prestito
	 * @param fruitore il fruitore che richiede il rinnovo di un prestito
	 */
	public void rinnovaPrestito(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = model.stampaPrestitiAttiviDi(fruitore,model);
		
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
				prestitiAttivi.get(i).visualizzaPrestito();
				MessaggiSistemaView.cornice();
			}
			
			int selezione = PrestitiView.chiediRisorsaDaRinnovare(prestitiAttivi.size());
			if(selezione != 0)
			{
				Prestito prestitoSelezionato = prestitiAttivi.get(selezione-1);
				
				if(!prestitoSelezionato.isRinnovabile())
				{
					PrestitiView.prestitoGiàProrogato();
				}
				else if(GestioneDate.DATA_CORRENTE.after(prestitoSelezionato.getDataPerRichiestaProroga()))
//				è necessariamente precedente alla data di scadenza prestito sennò sarebbe terminato
				{
					model.rinnovaPrestito(prestitoSelezionato);
				}
				else//non si può ancora rinnovare prestito
				{
					PrestitiView.prestitoNonRinnovabile(prestitoSelezionato);
				}
			}
		}
	}

	public boolean raggiunteRisorseMassime(Fruitore utenteLoggato, String categoria) 
	{
		if(categoria == CATEGORIE[0])// == "Libri"
		{
			if(model.numPrestitiAttiviDi(utenteLoggato, categoria) == Libro.PRESTITI_MAX)
			{
				PrestitiView.raggiunteRisorseMassime(categoria);
				return true;
			}
		}
		else if(categoria == CATEGORIE[1])// == "Films"
		{
			if(model.numPrestitiAttiviDi(utenteLoggato, categoria) == Film.PRESTITI_MAX)
			{
				PrestitiView.raggiunteRisorseMassime(categoria);
				return true;
			}
		}
		return false;
	}

	public void controllaRisorsa(Fruitore utenteLoggato, Risorsa risorsa) 
	{
		if(model.prestitoFattibile(utenteLoggato, risorsa))
		{
			model.addPrestito(utenteLoggato, risorsa);
		    
			PrestitiView.prenotazioneEffettuata(risorsa);
		}
		else//!prestitoFattibile se l'utente ha già una copia in prestito
		{
			PrestitiView.risorsaPosseduta();
		}	
	}	
}
