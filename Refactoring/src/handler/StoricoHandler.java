package handler;

import controllerMVC.StoricoController;
import view.StoricoView;

/**
 * (precondizione: prestiti != null && archivio != null && fruitori != null)
 * menuStorico permette la consultazione dello storico. 
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class StoricoHandler
{
	private StoricoController storicoController;
	
	public StoricoHandler(StoricoController storicoController)
	{
		this.storicoController = storicoController;
	}
	
	public boolean mostra(int scelta)
	{
		boolean terminato;
			
		switch (scelta) 
		{
			case 0:
			{
				terminato = true;
				break;
			}
			case 1://visualizza numero prestiti per anno solare
			{
				StoricoView.prestitiPerAnnoSolare(storicoController.prestitiAnnoSolare());
				terminato = false;
				break;
			}
			case 2://visualizza numero proroghe per anno solare
			{
				StoricoView.proroghePerAnnoSolare(storicoController.prorogheAnnoSolare());
				terminato = false;
				break;
			}
			case 3://visualizza la risorsa che è stata oggetto del maggior numero di prestiti per anno solare
			{
				storicoController.risorsaPiùInPrestito();
				terminato = false;
				break;
			}
			case 4://visualizza numero di prestiti per fruitore per anno solare
			{
				storicoController.prestitiAnnuiPerFruitore();
				terminato = false;
				break;
			}
			case 5://visualizza risorse prestabili in passato
			{
				storicoController.risorsePrestabiliInPassato();
				terminato = false;
				break;
			}
			case 6://visualizza iscrizioni decadute
			{
				storicoController.fruitoriDecaduti();
				terminato = false;
				break;
			}
			case 7://visualizza iscrizioni rinnovate
			{
				storicoController.fruitoriRinnovati();
				terminato = false;
				break;
			}
			case 8://visualizza prestiti prorogati
			{
				storicoController.prestitiProrogati();
				terminato = false;
				break;
			}
			case 9://visualizza prestiti scaduti
			{
				storicoController.prestitiTerminati();
				terminato = false;
				break;
			}
			case 10://visualizza prestiti terminati in anticipo
			{
				storicoController.prestitiTerminatiInAnticipo();
				terminato = false;
				break;
			}
			default:
			{
				terminato = true;
			}
		}
		return terminato;
	}
}
