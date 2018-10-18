package controllerMVC;

import java.util.GregorianCalendar;
import java.util.Vector;

import handler.StoricoHandler;
import interfaces.Risorsa;
import model.*;
import myLib.MyMenu;
import view.StoricoView;

public class StoricoController 
{
	Storico model;
	StoricoHandler storicoHandler;

	public StoricoController(Storico storico) 
	{
		model = storico;
	}
	
	public void menuStorico() 
	{
		final String[] VOCI_MENU_STORICO = {"Numero prestiti per anno solare", "Numero proroghe per anno solare",
				"Risorsa che è stata oggetto del maggior numero di prestiti per anno solare",
				"Numero di prestiti per fruitore per anno solare", "Risorse prestabili in passato", "Iscrizioni decadute",
				"Iscrizioni rinnovate", "Prestiti prorogati", "Prestiti terminati", "Prestiti terminati in anticipo"};
		final String INTESTAZIONE = "\nScegli cosa visualizzare: ";
		
		boolean terminato = false;
		do
		{
			MyMenu menuStorico = new MyMenu(INTESTAZIONE, VOCI_MENU_STORICO, true);
			int scelta = menuStorico.scegliBase();
			
			if(storicoHandler==null)
			{
				storicoHandler = new StoricoHandler(this);
			}
			
			terminato = storicoHandler.mostra(scelta);
		}
		while(!terminato);
	}	
	
	public int prestitiAnnoSolare()
	{
		int annoSelezionato = StoricoView.selezionaAnno();
		return model.prestitiAnnoSolare(annoSelezionato);
	}
	
	public int prorogheAnnoSolare() 
	{
		int annoSelezionate = StoricoView.selezionaAnno();
		return model.prorogheAnnoSolare(annoSelezionate);
	}
	
	public void risorsaPiùInPrestito()
	{
		int maxGenerale = 0;
		String titoloRisorsaMax = "";
		int annoSelezionato = StoricoView.selezionaAnno();

		Vector<Risorsa> risorseAnnue = new Vector<Risorsa>();
		
		//seleziono solo le risorse che sono state prenotate nell'anno corrente
		for (Prestito prestito : model.getPrestiti().getPrestiti())
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				risorseAnnue.add(prestito.getRisorsa());
			}
		}
		for(int i = 0; i < risorseAnnue.size(); i++)
		{
			int maxRisorsa = 1;
			String idInConsiderazione = risorseAnnue.get(i).getId();
			for (int j = risorseAnnue.size()-1; j > i; j--) 
			{
				if(idInConsiderazione.equals(risorseAnnue.get(j).getId()))
				{
					maxRisorsa++;
					risorseAnnue.remove(j);
				}
			}
			if(maxRisorsa > maxGenerale) 
			{
				maxGenerale = maxRisorsa;
				titoloRisorsaMax = risorseAnnue.get(i).getTitolo();
			}
		}
		if(maxGenerale==0)
		{
			StoricoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			StoricoView.risorsaConPiùPrenotazioniInAnnoSelezionato(titoloRisorsaMax, maxGenerale);
		}
	}
	
	public void prestitiAnnuiPerFruitore()
	{
		int annoSelezionato = StoricoView.selezionaAnno();
		Vector<Prestito> prestitiAnnui = model.prestitiAnnui(annoSelezionato);
		if(prestitiAnnui == null)
		{
			StoricoView.noPrestitiInAnnoSelezionato();
		}
		else//ci sono prestiti nell'anno selezionato
		{
			Vector<String>fruitoriDellAnno = new Vector<>();
			for(Prestito prestito : prestitiAnnui)
			{
				if(!fruitoriDellAnno.contains(prestito.getFruitore().getUser()))
				{
					fruitoriDellAnno.add(prestito.getFruitore().getUser());
				}
			}
//			una volta per ogni fruitore che ha avuto un prestito nell'anno selezionato
			for(String user : fruitoriDellAnno)
			{
				StoricoView.numeroPrestitiPerUtente(user, model.prestitiPerFruitore(prestitiAnnui, user));
			}
		}
	}
	
	public void risorsePrestabiliInPassato()
	{
		StoricoView.libriPrestabiliInPassato();
		
		Vector<Risorsa> libriPrestabili = model.risorsePrestabiliInPassato("Libri");
		if(libriPrestabili == null)
		{
			StoricoView.nessuno();
		}
		else
		{
			for(int i = 0; i < libriPrestabili.size(); i++)
			{
				StoricoView.libroPrestabileInPassato(libriPrestabili.get(i).getTitolo());
			}
		}
		
		StoricoView.filmsPrestabiliInPassato();
		
		Vector<Risorsa> filmsPrestabili = model.risorsePrestabiliInPassato("Films");
		if(filmsPrestabili==null)
		{
			StoricoView.nessuno();
		}
		else
		{
			for(int i = 0;i<filmsPrestabili.size();i++)
			{
				StoricoView.filmPrestabileInPassato(filmsPrestabili.get(i).getTitolo());
			}
		}
	}
	
	public void fruitoriDecaduti()
	{
		StoricoView.fruitoriDecaduti();
		Vector<Fruitore> fruitoriDecaduti = model.fruitoriDecaduti();
		if(fruitoriDecaduti == null)
		{
			StoricoView.noIscrizioniDecadute();
		}
		else
		{
			for (int i=0;i<fruitoriDecaduti.size();i++) 
			{
				StoricoView.fruitoreDecaduto(fruitoriDecaduti.get(i));
			}
		}
	}
	
	public void fruitoriRinnovati()
	{
		StoricoView.iscrizioniRinnovate();
		Vector<String> dateRinnovi = model.fruitoriRinnovati();
		if(dateRinnovi==null)
		{
			StoricoView.nessunFruitoreHaRinnovato();
		}
		else
		{
			for (int i=0;i<dateRinnovi.size();i++) 
			{			
				StoricoView.dateRinnovo(dateRinnovi.get(i));
			}
		}
	}
	
	public void prestitiProrogati()
	{
		StoricoView.prestitiProrogati();
		Vector<Prestito> prestitiProrogati = model.prestitiProrogati();
		if(prestitiProrogati==null)
		{
			StoricoView.noPrestitiRinnovvati();
		}
		else
		{
			for (int i=0;i<prestitiProrogati.size();i++) 
			{
				StoricoView.prestitoProrogato(prestitiProrogati.get(i));
			}
		}
	}
	public void prestitiTerminati()
	{
		StoricoView.prestitiTerminati();
		Vector<Prestito> prestitiTerminati = model.prestitiTerminati();
		if(prestitiTerminati==null)
		{
			StoricoView.noPrestitiTerminati();
		}
		else
		{
			for (int i=0;i<prestitiTerminati.size();i++) 
			{
				StoricoView.prestitoTerminato(prestitiTerminati.get(i));
			}
		}
	}
	
	public void prestitiTerminatiInAnticipo()
	{
		StoricoView.prestitiTerminatiInAnticipo();
		Vector<Prestito> prestitiTerminatiInAnticipo = model.prestitiTerminatiInAnticipo();
		if(prestitiTerminatiInAnticipo==null)
		{
			StoricoView.noPrestitiTerminatiInAnticipo();
		}
		else
		{
			for (int i=0;i<prestitiTerminatiInAnticipo.size();i++) 
			{
				StoricoView.prestitoTerminatoInAnticipo(prestitiTerminatiInAnticipo.get(i));
			}
		}
	}
}
