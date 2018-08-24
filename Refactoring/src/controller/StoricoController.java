package controller;

import java.util.Vector;

import model.Film;
import model.Fruitore;
import model.Fruitori;
import model.Libro;
import model.Prestiti;
import model.Prestito;
import model.Storico;
import view.StoricoView;

public class StoricoController 
{
	Prestiti modelPrestiti;
	Fruitori modelFruitori;

	public StoricoController(Prestiti prestiti,Fruitori fruitori) 
	{
		this.modelPrestiti = prestiti;
		this.modelFruitori = fruitori;
	}
	
	public int prestitiAnnoSolare()
	{
		
		int annoSelezionato = StoricoView.AnnoSelezionato();
		return Storico.prestitiAnnoSolare(annoSelezionato, modelPrestiti);
	}
	
	public int prorogheAnnoSolare() 
	{
		int annoSelezionate = StoricoView.AnnoSelezionato();
		return Storico.prorogheAnnoSolare(annoSelezionate, modelPrestiti);
	}
	
	public void risorsaPiùInPrestito()
	{
		
	}
	
	public void prestitiAnnuiPerFruitore()
	{
		Fruitore fruitoreInConsiderazione = null;
		int nPrestiti = 0;
		int annoSelezionato = StoricoView.AnnoSelezionato();
		Vector<Prestito> prestitiAnnui = Storico.prestitiAnnui(annoSelezionato, modelPrestiti);
		if(prestitiAnnui == null)
		{
			StoricoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			for(int j = 0; j < prestitiAnnui.size(); j++)
			{
			
				fruitoreInConsiderazione = modelPrestiti.getPrestiti().get(j).getFruitore();
				
				nPrestiti = Storico.prestitiAnnuiPerFruitore(prestitiAnnui, fruitoreInConsiderazione, j);
			
			}
			StoricoView.numeroPrestitiPerUtente(fruitoreInConsiderazione.getUser(), nPrestiti);
		}
	}
	
	public void risorsePrestabili(ArchivioController archivioController)
	{
		StoricoView.libriPrestabiliInPassato();
		
		Vector<Libro> libriPrestabili = Storico.risorePrestabiliLibri(archivioController);
		if(libriPrestabili==null)
		{
			StoricoView.nessuno();
		}
		else
		{
			for(int i = 0;i<libriPrestabili.size();i++)
			{
				StoricoView.libroPrestabileInPassato(libriPrestabili.get(i).getTitolo());
			}
		}
		
		StoricoView.filmsPrestabiliInPassato();
		
		Vector<Film> filmsPrestabili = Storico.risorsePrestabiliFilms(archivioController);
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
		Vector<Fruitore> fruitoriDecaduti = Storico.fruitoriDecaduti(modelFruitori);
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
		Vector<String> dateRinnovi = Storico.fruitoriRinnovati(modelFruitori);
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
		Vector<Prestito> prestitiProrogati = Storico.prestitiProrogati(modelPrestiti);
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
		Vector<Prestito> prestitiTerminati = Storico.prestitiTerminati(modelPrestiti);
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
		Vector<Prestito> prestitiTerminatiInAnticipo = Storico.prestitiTerminatiInAnticipo(modelPrestiti);
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
