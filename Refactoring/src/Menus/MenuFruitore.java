package Menus;

import Utility.GestoreSalvataggi;
import model.Archivio;
import model.Film;
import model.Fruitori;
import model.Libro;
import model.Main;
import model.Prestiti;
import myLib.InputDati;
import myLib.MyMenu;
import myLib.ServizioFile;
import view.FruitoriView;

public class MenuFruitore 
{
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
														"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Termina prestiti"};
	private static final String[] CATEGORIE = {"Libri","Film"};

	
	public static void show(Fruitori fruitori, Archivio archivio, Prestiti prestiti)
	{
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		continuaMenuFruitore=true;
		do
		{
			gestisciMenuFruitore(menuFruitore.scegli(), fruitori, archivio, prestiti);
		}
		while(continuaMenuFruitore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuFruitore(int scelta, Fruitori fruitori, Archivio archivio, Prestiti prestiti)
	{
		continuaMenuFruitore=true;
		
		switch(scelta)
		{
			case 0:	//EXIT
			{
				continuaMenuFruitore=false;
				break;
			}
			case 1:	//registrazione nuovo fruitore
			{
				fruitori.addFruitore();
				GestoreSalvataggi.salvaFruitori(fruitori);

				continuaMenuFruitore=true;//torna al menu
				break;				
			}
			case 2:	//login
			{
				String user = InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
				String password = InputDati.leggiStringaNonVuota("Inserisci la tua password: ");
				
				Main.setUtenteLoggato(fruitori.trovaUtente(user, password));
				
				if(Main.getUtenteLoggato()==null)
				{
					System.out.println("Utente non trovato! ");
					return;
				}
//				-> utente trovato
				System.out.println("Benvenuto " + Main.getUtenteLoggato().getNome() + "!");
				
				MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
				continuaMenuPersonale=true;
				do
				{
					gestisciMenuPersonale(menuPersonale.scegli(), archivio, prestiti);
				}
				while(continuaMenuPersonale);
								
				continuaMenuFruitore=true;
				break;
			}
		}	
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
				System.out.println("Informazioni personali:");
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
				MyMenu menu = new MyMenu("scegli la categoria di risorsa: ", CATEGORIE);
				
				try
				{
					String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
					if(categoria == CATEGORIE[0])// == "Libri"
					{
						if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Libro.PRESTITI_MAX)
						{
							System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
									+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
						}
						else//può chiedere un altro prestito
						{
							Libro libro = archivio.getLibri().scegliLibro();
							
							if(libro != null)
							{
								if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), libro))
								{
									prestiti.addPrestito(Main.getUtenteLoggato(), libro);
								    
									System.out.println(libro.getTitolo() + " prenotato con successo!");
								}
								else//!prestitoFattibile se l'utente ha già una copia in prestito
								{
									System.out.println("Prenotazione rifiutata: possiedi già questa risorsa in prestito");
								}
							}
//							qui libro==null: vuol dire che l'utente non ha selezionato un libro (0: torna indietro)
						}
					}
					else if(categoria == CATEGORIE[1])// == "Films"
					{
						if(prestiti.numPrestitiAttiviDi(Main.getUtenteLoggato(), categoria) == Film.PRESTITI_MAX)
						{
							System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
									+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
						}
						else//può chiedere un altro prestito
						{
							Film film = archivio.getFilms().scegliFilm();
							
							if(film != null)
							{
								if(prestiti.prestitoFattibile(Main.getUtenteLoggato(), film))
								{
									prestiti.addPrestito(Main.getUtenteLoggato(), film);
								
									System.out.println(film.getTitolo() + " prenotato con successo!");
								}
								else//!prestitoFattibile se l'utente ha già una copia in prestito
								{
									System.out.println("Prenotazione rifiutata: possiedi già questa risorsa in prestito");
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
				MenuPrestiti.show(prestiti, archivio);
				
				
				continuaMenuPersonale = true;
				break;
			}
		}
	}
}
