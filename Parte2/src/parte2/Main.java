/*
 * Un cittadino maggiorenne, per divenire fruitore dei servizi di prestito temporaneo, deve avanzare una richiesta interagendo con l’applicazione software. 
 * Tale richiesta determina l’attribuzione dello status di fruitore a partire dalla data di iscrizione del cittadino all’elenco dei fruitori fino alla data 
 * di scadenza, fissata esattamente 5 anni dopo quella di iscrizione. Lo status di fruitore può essere rinnovato, ogni volta per 5 anni, un numero indefinito
 * di volte purché l’interessato richieda il rinnovo entro la data di scadenza e non prima dei 10 giorni precedenti la data della scadenza stessa. 
 * La mancata richiesta di rinnovo nei termini prescritti comporta la decadenza dallo status di fruitore. Un fruitore decaduto può comunque effettuare una 
 * nuova richiesta di iscrizione. La prima versione dell’applicazione è focalizzata sulle funzionalità per la gestione di una minimale “anagrafica fruitori” 
 * elencate di seguito, che non prevedono alcun intervento da parte dell’operatore:
 * - iscrizione di un nuovo fruitore;
 * - decadenza (automatica) di un fruitore che non ha chiesto il rinnovo nei termini prescritti;
 * - rinnovo dell’iscrizione di un fruitore che ne ha fatto richiesta nei termini prescritti.
 * Inoltre la prima versione dell’applicazione deve consentire all’operatore di visualizzare l’elenco degli attuali fruitori.
 * */
package parte2;

import java.io.File;
import java.io.IOException;

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
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali"};//"modifica dati" ?
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String[] MENU_OPERATORE_VOCI = {"Visualizza fruitori","Aggiungi un libro","Rimuovi un libro","Visualizza l'elenco dei libri"};//archivia risorsa(aggiungi/elimina), visualizza risorse
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";

	private static final String PATHfruitori = "Fruitori.dat";
	private static final String PATHarchivio = "Archivio.dat";
	
	private static boolean continuaMenuAccesso;
	private static boolean continuaMenuFruitore;
	private static boolean continuaMenuOperatore;
	private static boolean continuaMenuPersonale;
	
	private static File fileFruitori = new File(PATHfruitori);
	private static File fileArchivio = new File(PATHarchivio);
//	serve a tutti i metodi (va qua?)
	private static Fruitori fruitori = new Fruitori();
	private static Libri libri = new Libri();
	
	private static Fruitore utenteLoggato = null;
	
	public static void main(String[] args)
	{		
		
		try 
		{
//			se non c'è il file lo crea (vuoto) e salva all'interno "fruitori", un vector per adesso vuoto.
//			così quando dopo si fa il caricamento non ci sono eccezioni
			ServizioFile.checkFile(fileFruitori, fruitori);
			ServizioFile.checkFile(fileArchivio, libri);
		} 
		
//		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
//		avviato il programma carico i fruitori dal file + 
//		carico i Libri dal file
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori);
		libri = (Libri)ServizioFile.caricaSingoloOggetto(fileArchivio);
		
		
//		elimino i fruitori scaduti (elimino o no??)
		fruitori.controlloIscrizioni();		
		
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
				System.out.println(BelleStringhe.incornicia("Grazie per aver usato ArchivioMultimediale!"));
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
				String passwordOperatore = InputDati.leggiStringa("Inserire la password per accedere all'area riservata agli operatori: ");
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
			case 2:
			{
				libri.addLibro();
				ServizioFile.salvaSingoloOggetto(fileArchivio, libri, false);
				continuaMenuOperatore=true;
				break;
			}
			case 3:
			{
				libri.removeLibro();
				ServizioFile.salvaSingoloOggetto(fileArchivio, libri, false);
				continuaMenuOperatore=true;
				break;
			}
			case 4:
			{
				//NULL POINTER EXCEPTION
				libri.stampaLibri();
				continuaMenuOperatore=true;
				break;
			}
		}
		ServizioFile.salvaSingoloOggetto(fileArchivio, libri, false);
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
				/////////// CREDENZIALI ////////////
				String user = InputDati.leggiStringa("Inserisci il tuo username: ");
				String password = InputDati.leggiStringa("Inserisci la tua password: ");
				
				utenteLoggato = fruitori.trovaUtente(user, password);
				
				if(utenteLoggato==null)
				{
					System.out.println("Utente non trovato! ");
					return;
				}
//				-> utente trovato
				System.out.println("Benvenuto " + utenteLoggato.getNome() + "!");
				
				///////// AREA RISERVATA ///////////
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
			case 0:	//TORNA A MENU PRINCIPALE (fare LOGOUT?)
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
		}
	}
}