/**
VERSIONE 5.0
*Quest’ultima versione dell’applicazione deve supportare la conservazione in archivio di
*informazioni storiche relative a:
*fruitori, iscrizioni, rinnovi di iscrizione e decadenze;
*risorse (ad esempio, si deve tenere traccia di risorse che sono state prestabili in
*passato ma ora non lo sono più);
*prestiti e proroghe degli stessi.
**/

package parte5;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import myLib.MyMenu;
import myLib.BelleStringhe;
import myLib.InputDati;
import myLib.ServizioFile;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
														"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Annulla prestiti"};
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
															"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	private static final String[] CATEGORIE = {"Libri","Film"};//Films, ecc
	private static final String PATH_FRUITORI = "Fruitori.dat";
	private static final String PATH_ARCHIVIO= "Archivio.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
 	private static final String MESSAGGIO_ADDIO = "\nGrazie per aver usato ArchivioMultimediale!";
	private static final String MESSAGGIO_PASSWORD = "Inserire la password per accedere all'area riservata agli operatori: ";
	
	private static boolean continuaMenuAccesso;
	private static boolean continuaMenuFruitore;
	private static boolean continuaMenuOperatore;
	private static boolean continuaMenuPersonale;
	
	private static File fileFruitori = new File(PATH_FRUITORI);
	private static File fileArchivio = new File(PATH_ARCHIVIO);
	private static File filePrestiti = new File(PATH_PRESTITI);

	private static Fruitori fruitori = new Fruitori();
	private static Archivio archivio = new Archivio();
	private static Prestiti prestiti = new Prestiti();
	
	private static Fruitore utenteLoggato = null;
	
	public static void main(String[] args)
	{				
		try 
		{
//			se non c'è il file lo crea (vuoto) e salva all'interno l'oggetto corrispondente.
//			così quando dopo si fa il caricamento non ci sono eccezioni
			ServizioFile.checkFile(fileFruitori, fruitori);
			ServizioFile.checkFile(fileArchivio, archivio);
			ServizioFile.checkFile(filePrestiti, prestiti);
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori);
		archivio = (Archivio)ServizioFile.caricaSingoloOggetto(fileArchivio);
		prestiti = (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti);
		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		ricostruisciPrestiti();
		
//		segna come "decadute" le iscrizioni in archivio che sono scadute
		Vector<Fruitore>utentiScaduti = fruitori.controlloIscrizioni();
//		rimuovo i prestiti che gli utenti scaduti avevano attivi
		prestiti.annullaPrestitiDi(utentiScaduti);
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);
		
//		elimino i prestiti scaduti
		prestiti.controlloPrestiti();
		ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
		
		MyMenu menuAccesso = new MyMenu(MENU_ACCESSO, MENU_ACCESSO_SCELTE);
		continuaMenuAccesso=true;
		do
		{
			gestisciMenuAccesso(menuAccesso.scegli());
		}
		while(continuaMenuAccesso);		
	}
	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuAccesso(int scelta) 
	{
		continuaMenuAccesso=true;

		switch(scelta)
		{
			case 0://EXIT
			{
				System.out.println(BelleStringhe.incornicia(MESSAGGIO_ADDIO));
				continuaMenuAccesso=false;
				break;
			}
			case 1://accesso FRUITORE
			{
				MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
				continuaMenuFruitore=true;
				do
				{
					gestisciMenuFruitore(menuFruitore.scegli());
				}
				while(continuaMenuFruitore);
				
				continuaMenuAccesso=true;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = InputDati.leggiStringaNonVuota(MESSAGGIO_PASSWORD);
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					System.out.println("Accesso eseguito con successo!");
					
					MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
					continuaMenuOperatore=true;
					do
					{
						gestisciMenuOperatore(menuOperatore.scegli());
					}
					while(continuaMenuOperatore);
				}
				else
				{
					System.out.println("Password errata!");
				}
				
				continuaMenuAccesso=true;
				break;
			}
		}
	}

	/**
	 * menu che compare una volta che si esegue l'accesso come operatore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuOperatore(int scelta) 
	{
		continuaMenuOperatore=true;
		switch(scelta)
		{
			case 0://EXIT
			{
				continuaMenuOperatore=false;
				break;
			}
			case 1://VISUALIZZA FRUITORI
			{
				fruitori.stampaFruitoriAttivi();
				
				continuaMenuOperatore=true;
				break;
			}
			case 2://AGGIUNGI RISORSA
			{
				archivio.aggiungiRisorsa(CATEGORIE);
				
				ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);
				
				continuaMenuOperatore=true;
				break;
			}
			case 3://RIMUOVI RISORSA
			{
				String idRimosso = archivio.rimuoviRisorsa(CATEGORIE);
//				se utente annulla procedura ritorna "-1"
				if(!idRimosso.equals("-1"))
				{
					prestiti.annullaPrestitiConRisorsa(idRimosso);
					ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);
				}
				
				continuaMenuOperatore=true;
				break;
			}
			case 4://VISUALIZZA ELENCO RISORSE
			{
				archivio.visualizzaRisorsePrestabili(CATEGORIE);
				
				continuaMenuOperatore=true;
				break;
			}
			case 5://CERCA RISORSA
			{
				archivio.cercaRisorsa(CATEGORIE);
				
				continuaMenuOperatore=true;
				break;
			}
			case 6://VIUSALIZZA TUTTI I PRESTITI ATTIVI
			{
				prestiti.visualizzaTuttiPrestitiAttivi();
				
				continuaMenuOperatore = true;
				break;
			}
			case 7://VISUALIZZA STORICO
			{
				Storico.menuStorico(prestiti,archivio,fruitori);
				
				continuaMenuOperatore = true;
				break;
			}
		}
	}

	/**
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuFruitore(int scelta)
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
				ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);

				continuaMenuFruitore=true;//torna al menu
				break;				
			}
			case 2:	//login
			{
				String user = InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
				String password = InputDati.leggiStringaNonVuota("Inserisci la tua password: ");
				
				utenteLoggato = fruitori.trovaUtente(user, password);
				
				if(utenteLoggato==null)
				{
					System.out.println("Utente non trovato! ");
					return;
				}
//				-> utente trovato
				System.out.println("Benvenuto " + utenteLoggato.getNome() + "!");
				
				MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
				continuaMenuPersonale=true;
				do
				{
					gestisciMenuPersonale(menuPersonale.scegli());
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
	private static void gestisciMenuPersonale(int scelta) 
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
				utenteLoggato.rinnovo();
				
				continuaMenuPersonale = true;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				System.out.println("Informazioni personali:");
				utenteLoggato.stampaDati();
				
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
						if(prestiti.numPrestitiAttiviDi(utenteLoggato, categoria) == Libro.PRESTITI_MAX)
						{
							System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
									+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
						}
						else//può chiedere un altro prestito
						{
							Libro libro = archivio.getLibri().scegliLibro();
							
							if(libro != null)
							{
								if(prestiti.prestitoFattibile(utenteLoggato, libro))
								{
									prestiti.addPrestito(utenteLoggato, libro);
								    
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
						if(prestiti.numPrestitiAttiviDi(utenteLoggato, categoria) == Film.PRESTITI_MAX)
						{
							System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
									+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
						}
						else//può chiedere un altro prestito
						{
							Film film = archivio.getFilms().scegliFilm();
							
							if(film != null)
							{
								if(prestiti.prestitoFattibile(utenteLoggato, film))
								{
									prestiti.addPrestito(utenteLoggato, film);
								
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
					ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
					ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);			
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
				prestiti.rinnovaPrestito(utenteLoggato);
				continuaMenuPersonale = true;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				prestiti.stampaPrestitiAttiviDi(utenteLoggato);
				
				continuaMenuPersonale = true;
				break;
			}
			case 7://ANNULLA PRESTITI
			{
				prestiti.annullaPrestitiDi(utenteLoggato);
				
				ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
				ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);
				
				continuaMenuPersonale = true;
				break;
			}
		}
	}
	
	/**
	 * quando salvo oggetti in un file e poi li ricarico, i libri di "Prestiti" non corrispondono più a quelli in "Libri" (verificato con hashcode che cambia, da 
	 * uguale prima del caricamento diventa diverso dopo il caricamento): Risorse e Prestiti vengono salvati in posti diversi e poi caricati come "diversi".
	 * Quando invece viene creato il prestito, la sua risorsa e quella in archivio sono lo stesso oggetto. Salvando e caricando in due posti diversi è come se "si sdoppiasse".
	 * Per non dover salvare tutto in unico file, in questo metodo ricollego gli elementi in modo da farli riferire allo stesso oggetto (tramite ID univoco):
	 * quando dico che il libro in "Prestito" torna dal prestito, si aggiornano anche le copie disponibili in "Libri"
	 */
	public static void ricostruisciPrestiti()
	{
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.getRisorsa() instanceof Libro)
			{
				for(Libro libro : archivio.getLibri().getLibri())
				{
					if(prestito.getRisorsa().equals(libro))
					{
						prestito.setRisorsa(libro);
					}
				}
			}
			else if(prestito.getRisorsa() instanceof Film)
			{
				for(Film film : archivio.getFilms().getfilms())
				{
					if(prestito.getRisorsa().equals(film))
					{
						prestito.setRisorsa(film);
					}
				}
			}
//			else if(altra categoria)
//			{
//				...
//			}
			
		}
	}
}