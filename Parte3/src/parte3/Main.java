/**
 * VERSIONE 3.0
 * 	La versione corrente dell’applicazione deve consentire all’operatore di archiviare le descrizioni delle risorse e visualizzare il contenuto dell’archivio,
 *  secondo le specifiche seguenti: 
 *  aggiunta (della descrizione) di una risorsa, completa in ogni suo campo, a una (sotto) categoria in archivio; 
 *  rimozione (della descrizione) di una risorsa dall’archivio; visualizzazione dell’elenco delle risorse per (sotto)categoria.
 */

package parte3;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import myLib.BelleStringhe;
import myLib.InputDati;
import myLib.MyMenu;
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
	private static final String[] MENU_OPERATORE_VOCI = {"Visualizza fruitori","Aggiungi un libro","Rimuovi un libro","Visualizza l'elenco dei libri",
															"Cerca un libro", "Visualizza tutti i prestiti attivi"};
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	private static final String[] CATEGORIE = {"Libri"};//Films, ecc

	private static final String PATH_FRUITORI = "Fruitori.dat";
//	poi salverò solo archivio in "Archivio.dat", dentro al quale ci sarà Libri, Films, ecc
	private static final String PATH_LIBRI = "Libri.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	private static final String MESSAGGIO_ADDIO = "\nGrazie per aver usato ArchivioMultimediale!";
	private static final String MESSAGGIO_PASSWORD = "Inserire la password per accedere all'area riservata agli operatori: ";
	
	private static boolean continuaMenuAccesso;
	private static boolean continuaMenuFruitore;
	private static boolean continuaMenuOperatore;
	private static boolean continuaMenuPersonale;
	
	private static File fileFruitori = new File(PATH_FRUITORI);
	private static File fileLibri = new File(PATH_LIBRI);
	private static File filePrestiti = new File(PATH_PRESTITI);
//	serve a tutti i metodi (va qua?)
	private static Fruitori fruitori = new Fruitori();
	private static Libri libri = new Libri();
	private static Prestiti prestiti = new Prestiti();
	private static Fruitore utenteLoggato = null;
	
	public static void main(String[] args)
	{				
		try 
		{
//			se non c'è il file lo crea (vuoto) e salva all'interno "fruitori", un vector per adesso vuoto.
//			così quando dopo si fa il caricamento non ci sono eccezioni
			ServizioFile.checkFile(fileFruitori, fruitori);
			ServizioFile.checkFile(fileLibri, libri);
			ServizioFile.checkFile(filePrestiti, prestiti);
		} 
				
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori);
		libri = (Libri)ServizioFile.caricaSingoloOggetto(fileLibri);
		prestiti = (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti);
		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		ricostruisciPrestiti();
		
//		elimino i fruitori con iscrizione scaduta (controlloIscrizini)
		Vector<Fruitore>utentiScaduti = fruitori.controlloIscrizioni();
//		rimuovo i prestiti che avevano attivi
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
					
					MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_VOCI, true);
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
				fruitori.stampaFruitori();
				
				continuaMenuOperatore=true;
				break;
			}
			case 2://AGGIUNGI LIBRO
			{
				libri.addLibro();
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, false);
				
				continuaMenuOperatore=true;
				break;
			}
			case 3://RIMUOVI LIBRO
			{
				System.out.println("ATTENZIONE! Se il libro che si desidera rimuovere ha copie attualmente in prestito, queste verranno sottratte ai fruitori");
				int idSelezionato = libri.removeLibro();
				if(idSelezionato != -1)//removeLibro ritorna -1 se l'utente annulla la procedura
				{
					prestiti.annullaPrestitiConLibro(idSelezionato);
				}
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, false);
				
				continuaMenuOperatore=true;
				break;
			}
			case 4://VISUALIZZA ELENCO LIBRI
			{
				libri.stampaLibri();
				
				continuaMenuOperatore=true;
				break;
			}
			case 5://VISUALIZZA DETTAGLI LIBRO
			{
				libri.dettagliLibro();
				
				continuaMenuOperatore=true;
				break;
			}
			case 6://VIUSALIZZA TUTTI I PRESTITI ATTIVI
			{
				prestiti.visualizzaTuttiPrestiti();
				
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
				ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false); // salvo i fruitori nel file "fileFruitori"
				
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
			case 2:	//VISUALIZZA INFO
			{
				System.out.println("Informazioni personali:");
				utenteLoggato.stampaDati();
				
				continuaMenuPersonale = true;
				break;
			}
			case 3://CERCA UNA RISORSA
			{
				MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE);
				String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
				if(categoria == CATEGORIE[0])// == "Libri"
				{
					libri.dettagliLibro();
				}
//				else if(categoria == CATEGORIE[1])// == "Films"
//				{
//					...	
//				}
				
				continuaMenuPersonale=true;
				break;
			}
			case 4: //RICHIEDI PRESTITO
			{
				MyMenu menu = new MyMenu("scegli la categoria di risorsa: ", CATEGORIE);
				String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
				if(categoria == CATEGORIE[0])// == "Libri"
				{
					if(prestiti.numPrestitiDi(utenteLoggato.getUser(), categoria) == Libro.PRESTITI_MAX)
					{
						System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
								+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
					}
					else//può chiedere un altro prestito
					{
						Libro libro = libri.scegliLibro();
						
						if(libro != null)
						{
							if(prestiti.prestitoFattibile(utenteLoggato, libro))
							{
								prestiti.addPrestito(utenteLoggato, libro);
								System.out.println(libro.getNome() + " prenotato con successo!");
							}
							else//!prestitoFattibile se l'utente ha già una copia in prestito
							{
								System.out.println("Prenotazione rifiutata: possiedi già questa risorsa in prestito");
							}
						}
//						qui libro==null: vuol dire che l'utente non ha selezionato un libro (0: torna indietro)
					}
					
				}
//				else if(categoria == CATEGORIE[1])// == "Films"
//				{
//					...	
//				}
				
				ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, false);
				
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
				prestiti.stampaPrestitiDi(utenteLoggato.getUser());
				
				continuaMenuPersonale = true;
				break;
			}
			case 7://ANNULLA PRESTITI
			{
				prestiti.annullaPrestitiDi(utenteLoggato);
				
				
				ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, false);
			}
		}
	}
	
	/**
	 * quando salvo oggetti in un file e poi li ricarico, i libri di "Prestiti" non corrispondono più a quelli in "Libri" (verificato con hashcode che cambia, da 
	 * uguale prima del caricamento diventa diverso dopo il caricamento)
	 * in questo metodo ricollego gli elementi in modo da farli riferire allo stesso oggetto (tramite ID univoco):
	 * quando dico che il libro in "Prestito" torna dal prestito, si aggiornano anche le copie disponibili in "Libri"
	 */
	public static void ricostruisciPrestiti()
	{
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.getRisorsa() instanceof Libro)
			{
				for(Libro libro : libri.getLibri())
				{
					if(prestito.getRisorsa().getId() == (libro.getId()))
					{
						prestito.setRisorsa(libro);
					}
				}
			}
//			PER LE PROSSIME CATEGORIE
//			else if(prestito.getRisorsa() instanceof Film)
//			{
//				for(Film film : films.getFilms())
//				{
//					if(prestito.getRisorsa().getId() == (film.getId()))
//					{
//						prestito.setRisorsa(film);
//					}
//				}
//			}
//			else if(altra categoria)
//			{
//				...
//			}
			
		}
	}
}