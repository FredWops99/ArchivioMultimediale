package controller;

import java.util.Vector;

import model.Fruitore;
import model.Fruitori;
import model.Prestiti;
import model.Prestito;
import myLib.GestioneDate;
import view.MessaggiSistemaView;
import view.PrestitiView;

public class PrestitiController 
{
	Prestiti modelPrestiti;
	Fruitori modelFruitori;

	public PrestitiController(Prestiti prestiti,Fruitori fruitori) 
	{
		modelPrestiti = prestiti;
		modelFruitori = fruitori;
	}
	
	//stampa il numero di risorse che sono rientrate dal prestito
	public void controlloPrestitiScaduti()
	{
		PrestitiView.numeroRisorseTornateDaPrestito(modelPrestiti.controlloPrestitiScaduti());
	}
	
	//stampai prestiti attivi
	public void stampaPrestitiAttivi()
	{
		Vector<Prestito> prestitiAttivi = modelPrestiti.stampaPrestitiAttivi(modelPrestiti);
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
		Vector<Prestito> prestitiAttivi = modelPrestiti.stampaPrestitiAttiviDi(fruitore,modelPrestiti);
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
	public void terminaPrestitoDi(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = modelPrestiti.stampaPrestitiAttiviDi(fruitore,modelPrestiti);
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
					modelPrestiti.terminaPrestitoSelezionato(selezione,prestitiAttivi);
					PrestitiView.prestitoTerminato();
				}
		}
		
		
	}
	
	public void terminaTuttiPrestitiDi(Fruitore fruitore)
	{
		
		int j = modelPrestiti.terminaTuttiPrestitiDi(fruitore);
		if(j == 0)
		{
			PrestitiView.noPrestiti();
		}
		else
		{
			PrestitiView.prestitiEliminati();
		}
	}
	
	public void rinnovaPrestito(Fruitore fruitore)
	{
		Vector<Prestito> prestitiAttivi = modelPrestiti.stampaPrestitiAttiviDi(fruitore,modelPrestiti);
		
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
					modelPrestiti.rinnovaPrestito(prestitoSelezionato);
				}
				else//non si può ancora rinnovare prestito
				{
					PrestitiView.prestitoNonRinnovabile(prestitoSelezionato);
				}
			}
		}
	}
	
		
}
