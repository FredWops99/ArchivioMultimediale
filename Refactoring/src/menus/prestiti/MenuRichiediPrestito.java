package menus.prestiti;

import controller.ArchivioController;
import model.Film;
import model.Libro;
import model.Main;
import model.Prestiti;
import model.Risorsa;
import myLib.MyMenu;
import model.GestoreSalvataggi;
import view.PrestitiView;

public class MenuRichiediPrestito 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	private static final String SCELTA_CATEGORIA = "scegli la categoria di risorsa: ";
	
	public static void show(Prestiti prestiti, ArchivioController archivioController) 
	{
		MyMenu menu = new MyMenu(SCELTA_CATEGORIA, CATEGORIE);
		
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
			
			if(categoria == CATEGORIE[0])// == "Libri"
			{
				if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Libro.PRESTITI_MAX)
				{
					PrestitiView.raggiunteRisorseMassime(categoria);
				}
				return;
			}
			else if(categoria == CATEGORIE[1])// == "Films"
			{
				if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Film.PRESTITI_MAX)
				{
					PrestitiView.raggiunteRisorseMassime(categoria);
				}
				return;
			}
			
			Risorsa risorsa = archivioController.scegliRisorsa(categoria);
			if(risorsa != null)
			{
				if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), risorsa))
				{
					prestiti.addPrestito(Main.getUtenteLoggato(), risorsa);
				    
					PrestitiView.prenotazioneEffettuata(risorsa);
				}
				else//!prestitoFattibile se l'utente ha già una copia in prestito
				{
					PrestitiView.risorsaPosseduta();
				}
			}
//			qui risorsa==null: vuol dire che l'utente non ha selezionato una risorsa (0: torna indietro)
			
//			if(categoria == CATEGORIE[0])// == "Libri"
//			{
//				if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Libro.PRESTITI_MAX)
//				{
//					PrestitiView.raggiunteRisorseMassime(categoria);
//				}
//				else//può chiedere un altro prestito
//				{
//					Libro libro = (Libro) archivioController.scegliRisorsa(categoria);
//					
//					if(libro != null)
//					{
//						if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), libro))
//						{
//							prestiti.addPrestito(Main.getUtenteLoggato(), libro);
//						    
//							PrestitiView.prenotazioneEffettuata(libro);
//						}
//						else//!prestitoFattibile se l'utente ha già una copia in prestito
//						{
//							PrestitiView.risorsaPosseduta();
//						}
//					}
////					qui libro==null: vuol dire che l'utente non ha selezionato un libro (0: torna indietro)
//				}
//			}
//			else if(categoria == CATEGORIE[1])// == "Films"
//			{
//				if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Film.PRESTITI_MAX)
//				{
//					PrestitiView.raggiunteRisorseMassime(categoria);
//				}
//				else//può chiedere un altro prestito
//				{
//					Film film = archivioController.scegliRisorsa(categoria);
//					Film film = archivioController.getFilmController().scegliFilm();
//					
//					if(film != null)
//					{
//						if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), film))
//						{
//							prestiti.addPrestito(Main.getUtenteLoggato(), film);
//						
//							PrestitiView.prenotazioneEffettuata(film);
//						}
//						else//!prestitoFattibile se l'utente ha già una copia in prestito
//						{
//							PrestitiView.risorsaPosseduta();
//						}
//					}
////					qui film==null: vuol dire che l'utente non ha selezionato un libro (ha selezionato 0: torna indietro)
//				}
//			}
			GestoreSalvataggi.salvaPrestiti(prestiti);
			Main.salvaArchivio();
//			GestoreSalvataggi.salvaArchivio(archivio);	
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
