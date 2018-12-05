package controllerMVC;

import java.util.GregorianCalendar;
import java.util.Vector;
import handler.StoricoHandler;
import interfaces.Risorsa;
import model.*;
import myLib.MyMenu;
import viewInterfaces.IStoricoView;

public class StoricoController 
{
	private Storico model;
	private IStoricoView storicoView;

	private StoricoHandler storicoHandler;

	public StoricoController(Storico storico) 
	{
		model = storico;
//		per storicoView: sennò Controller dipenderebbe da StoricoView, a causa dell'instanziamento. così solo Interface
		try 
		{
			this.storicoView = (IStoricoView)Class.forName(System.getProperty("StoricoView")).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public IStoricoView getStoricoView() 
	{
		return storicoView;
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
		int annoSelezionato = storicoView.selezionaAnno();
		return model.prestitiAnnoSolare(annoSelezionato);
	}
	
	public int prorogheAnnoSolare() 
	{
		int annoSelezionate = storicoView.selezionaAnno();
		return model.prorogheAnnoSolare(annoSelezionate);
	}
	
	public void risorsaPiùInPrestito()
	{
		int maxGenerale = 0;
		String titoloRisorsaMax = "";
		int annoSelezionato = storicoView.selezionaAnno();

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
			storicoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			storicoView.risorsaConPiùPrenotazioniInAnnoSelezionato(titoloRisorsaMax, maxGenerale);
		}
	}
	
	public void prestitiAnnuiPerFruitore()
	{
		int annoSelezionato = storicoView.selezionaAnno();
		Vector<Prestito> prestitiAnnui = model.prestitiAnnui(annoSelezionato);
		if(prestitiAnnui == null)
		{
			storicoView.noPrestitiInAnnoSelezionato();
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
				storicoView.numeroPrestitiPerUtente(user, model.prestitiPerFruitore(prestitiAnnui, user));
			}
		}
	}
	
	public void risorsePrestabiliInPassato()
	{
		storicoView.libriPrestabiliInPassato();
		
		Vector<Risorsa> libriPrestabili = model.risorsePrestabiliInPassato("Libri");
		if(libriPrestabili == null)
		{
			storicoView.nessuno();
		}
		else
		{
			for(int i = 0; i < libriPrestabili.size(); i++)
			{
				storicoView.libroPrestabileInPassato(libriPrestabili.get(i).getTitolo());
			}
		}
		
		storicoView.filmsPrestabiliInPassato();
		
		Vector<Risorsa> filmsPrestabili = model.risorsePrestabiliInPassato("Films");
		if(filmsPrestabili==null)
		{
			storicoView.nessuno();
		}
		else
		{
			for(int i = 0;i<filmsPrestabili.size();i++)
			{
				storicoView.filmPrestabileInPassato(filmsPrestabili.get(i).getTitolo());
			}
		}
	}
	
	public void fruitoriDecaduti()
	{
		storicoView.fruitoriDecaduti();
		Vector<Fruitore> fruitoriDecaduti = model.fruitoriDecaduti();
		if(fruitoriDecaduti == null)
		{
			storicoView.noIscrizioniDecadute();
		}
		else
		{
			for (int i=0;i<fruitoriDecaduti.size();i++) 
			{
				storicoView.fruitoreDecaduto(fruitoriDecaduti.get(i));
			}
		}
	}
	
	public void fruitoriRinnovati()
	{
		storicoView.iscrizioniRinnovate();
		Vector<String> dateRinnovi = model.fruitoriRinnovati();
		if(dateRinnovi==null)
		{
			storicoView.nessunFruitoreHaRinnovato();
		}
		else
		{
			for (int i=0;i<dateRinnovi.size();i++) 
			{			
				storicoView.dateRinnovo(dateRinnovi.get(i));
			}
		}
	}
	
	public void prestitiProrogati()
	{
		storicoView.prestitiProrogati();
		Vector<Prestito> prestitiProrogati = model.prestitiProrogati();
		if(prestitiProrogati==null)
		{
			storicoView.noPrestitiRinnovati();
		}
		else
		{
			for (int i=0;i<prestitiProrogati.size();i++) 
			{
				storicoView.prestitoProrogato(prestitiProrogati.get(i));
			}
		}
	}
	public void prestitiTerminati()
	{
		storicoView.prestitiTerminati();
		Vector<Prestito> prestitiTerminati = model.prestitiTerminati();
		if(prestitiTerminati==null)
		{
			storicoView.noPrestitiTerminati();
		}
		else
		{
			for (int i=0;i<prestitiTerminati.size();i++) 
			{
				storicoView.prestitoTerminato(prestitiTerminati.get(i));
			}
		}
	}
	
	public void prestitiTerminatiInAnticipo()
	{
		storicoView.prestitiTerminatiInAnticipo();
		Vector<Prestito> prestitiTerminatiInAnticipo = model.prestitiTerminatiInAnticipo();
		if(prestitiTerminatiInAnticipo==null)
		{
			storicoView.noPrestitiTerminatiInAnticipo();
		}
		else
		{
			for (int i=0;i<prestitiTerminatiInAnticipo.size();i++) 
			{
				storicoView.prestitoTerminatoInAnticipo(prestitiTerminatiInAnticipo.get(i));
			}
		}
	}
}
