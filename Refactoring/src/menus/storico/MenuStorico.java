package menus.storico;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.StoricoController;
import model.Prestiti;
import model.Storico;
import myLib.MyMenu;
import view.StoricoView;

public class MenuStorico
{
	public static final String[] VOCI_MENU_STORICO = {"Numero prestiti per anno solare", "Numero proroghe per anno solare",
			"Risorsa che è stata oggetto del maggior numero di prestiti per anno solare",
			"Numero di prestiti per fruitore per anno solare", "Risorse prestabili in passato", "Iscrizioni decadute",
			"Iscrizioni rinnovate", "Prestiti prorogati", "Prestiti terminati", "Prestiti terminati in anticipo"};
	private static final String INTESTAZIONE = "\nScegli cosa visualizzare: ";
	
	
	/**
	 * (precondizione: prestiti != null && archivio != null && fruitori != null)
	 * menuStorico permette la consultazione dello storico. In particolare sono pesenti le seguenti opzioni:
	 * - Numero prestiti per anno solare
	 * - Numero proroghe per anno solare
	 * - Risorsa che è stata oggetto del maggior numero di prestiti per anno solare
	 * - Numero di prestiti per fruitore per anno solare
	 * - Risorse prestabili in passato
	 * - Iscrizioni decadute
	 * - Iscrizioni rinnovate
	 * - Prestiti prorogati
	 * - Prestiti terminati
	 * - Prestiti terminati in anticipo.
	 * Questo metodo viene chiamato in menuOperatore (nel main).
	 * @param prestiti l'elenco dei prestiti che serve ai vari metodi all'intreno del menù
	 * @param archivio l'elenco delle risorse in archivio che servono ai vari metodi presenti nel menù 
	 * @param fruitori l'elenco dei fruitori che è utilizzato dai vari metodi del menù
	 */
	
	public static void show(StoricoController storicoController, Prestiti prestiti, ArchivioController archivioController, FruitoriController fruitoriController)
	{
		boolean continuaMenuStorico = true;
		do
		{
			MyMenu menuStorico = new MyMenu(INTESTAZIONE, VOCI_MENU_STORICO, true);
			
			switch (menuStorico.scegliBase()) 
			{
				case 0:
				{
					continuaMenuStorico = false;
					break;
				}
				case 1://visualizza numero prestiti per anno solare
				{
					
					StoricoView.prestitiPerAnnoSolare(storicoController.prestitiAnnoSolare());
					continuaMenuStorico = true;
					break;
				}
				case 2://visualizza numero proroghe per anno solare
				{
					
					StoricoView.proroghePerAnnoSolare(storicoController.prorogheAnnoSolare());
					continuaMenuStorico = true;
					break;
				}
				case 3://visualizza la risorsa che è stata oggetto del maggior numero di prestiti per anno solare
				{
					Storico.risorsaPiùInPrestito(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 4://visualizza numero di prestiti per fruitore per anno solare
				{
					storicoController.prestitiAnnuiPerFruitore();
					
					continuaMenuStorico = true;
					break;
				}
				case 5://visualizza risorse prestabili in passato
				{
					storicoController.risorsePrestabili(archivioController);
					
					continuaMenuStorico = true;
					break;
				}
				case 6://visualizza iscrizioni decadute
				{
					storicoController.fruitoriDecaduti();
					
					continuaMenuStorico = true;
					break;
				}
				case 7://visualizza iscrizioni rinnovate
				{
					Storico.fruitoriRinnovati(fruitoriController.getModel());
					
					continuaMenuStorico = true;
					break;
				}
				case 8://visualizza prestiti prorogati
				{
					Storico.prestitiProrogati(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 9://visualizza prestiti scaduti
				{
					Storico.prestitiTerminati(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
				case 10://visualizza prestiti terminati in anticipo
				{
					Storico.prestitiTerminatiInAnticipo(prestiti);
					
					continuaMenuStorico = true;
					break;
				}
			}
		}
		while(continuaMenuStorico);
	}
}
