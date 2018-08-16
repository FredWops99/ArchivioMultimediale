package menus;

import model.Archivio;
import model.Film;
import model.Libro;
import model.Main;
import model.Prestiti;
import myLib.MyMenu;
import utility.GestoreSalvataggi;
import view.FruitoriView;
import view.PrestitiView;

public class MenuPersonale 
{
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
														"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Termina prestiti"};
	private static final String[] CATEGORIE = {"Libri","Film"};
	private static final String SCELTA_CATEGORIA = "scegli la categoria di risorsa: ";
	
	private static boolean continuaMenuPersonale;

	
	public static void show(Archivio archivio, Prestiti prestiti)
	{
		MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
		continuaMenuPersonale=true;
		do
		{
			gestisciMenuPersonale(menuPersonale.scegli(), archivio, prestiti);
		}
		while(continuaMenuPersonale);
	}
	
	/**
	 * menu che compare dopo che un fruitore esegue il login
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuPersonale(int scelta, Archivio archivio, Prestiti prestiti) 
	{
		continuaMenuPersonale=true;
		
		switch(scelta)
		{
			case 0:	//TORNA A MENU PRINCIPALE
			{
				continuaMenuPersonale=false;
				break;
			}
			case 1:	//RINNOVA ISCRIZIONE
			{
				Main.getUtenteLoggato().rinnovo();
				
				continuaMenuPersonale = true;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				FruitoriView.stampaDatiFruitore(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 3://CERCA UNA RISORSA
			{
				archivio.cercaRisorsa(CATEGORIE);
				
				continuaMenuPersonale=true;
				break;
			}
			case 4: //RICHIEDI PRESTITO (non in prestiti perchè devo poter accedere alle risorse)
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
						else//può chiedere un altro prestito
						{
							Libro libro = archivio.getLibri().scegliLibro();
							
							if(libro != null)
							{
								if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), libro))
								{
									prestiti.addPrestito(Main.getUtenteLoggato(), libro);
								    
									PrestitiView.prenotazioneEffettuata(libro);
								}
								else//!prestitoFattibile se l'utente ha già una copia in prestito
								{
									PrestitiView.risorsaPosseduta();
								}
							}
//							qui libro==null: vuol dire che l'utente non ha selezionato un libro (0: torna indietro)
						}
					}
					else if(categoria == CATEGORIE[1])// == "Films"
					{
						if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Film.PRESTITI_MAX)
						{
							PrestitiView.raggiunteRisorseMassime(categoria);
						}
						else//può chiedere un altro prestito
						{
							Film film = archivio.getFilms().scegliFilm();
							
							if(film != null)
							{
								if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), film))
								{
									prestiti.addPrestito(Main.getUtenteLoggato(), film);
								
									PrestitiView.prenotazioneEffettuata(film);
								}
								else//!prestitoFattibile se l'utente ha già una copia in prestito
								{
									PrestitiView.risorsaPosseduta();
								}
							}
//							qui film==null: vuol dire che l'utente non ha selezionato un libro (ha selezionato 0: torna indietro)
						}
					}
					GestoreSalvataggi.salvaPrestiti(prestiti);
					GestoreSalvataggi.salvaArchivio(archivio);	
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
//					se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//					corrisponde ad ANNULLA, non va fatto nulla
				}
				
				continuaMenuPersonale = true;
				break;
			}
			case 5: //RINNOVA PRESTITO
			{
				prestiti.rinnovaPrestito(Main.getUtenteLoggato());
				continuaMenuPersonale = true;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				prestiti.stampaPrestitiAttiviDi(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 7://TERMINA PRESTITI
			{
				MenuTerminaPrestiti.show(prestiti, archivio);
				
				continuaMenuPersonale = true;
				break;
			}
		}
	}
	
}
