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
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Richiedi prestito", "Proroga prestito",
													"Visualizza prestiti in corso","Annulla prestito"};
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String[] MENU_OPERATORE_VOCI = {"Visualizza fruitori","Aggiungi un libro","Rimuovi un libro","Visualizza l'elenco dei libri",
													"Visualizza dettagli libro", "Visualizza tutti i prestiti attivi"};
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	private static final String[] CATEGORIE = {"Libri"};//Films, ecc

	private static final String PATH_FRUITORI = "Fruitori.dat";
//	poi salverò solo archivio in "Archivio.dat", dentro al quale ci sarà Libri, Films, ecc
	private static final String PATH_LIBRI = "Libri.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	
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
		
//		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
//		avviato il programma carico i fruitori dal file + 
//		carico i Libri dal file
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori);
		libri = (Libri)ServizioFile.caricaSingoloOggetto(fileLibri);
		prestiti = (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti);
		
//		elimino i fruitori scaduti
		fruitori.controlloIscrizioni();	
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
				System.out.println(BelleStringhe.incornicia("\nGrazie per aver usato ArchivioMultimediale!"));
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
				String passwordOperatore = InputDati.leggiStringaNonVuota("Inserire la password per accedere all'area riservata agli operatori: ");
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
				libri.removeLibro();
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
			case 3: //RICHIEDI PRESTITO
			{
				MyMenu menu = new MyMenu("scegli la categoria di risorsa: ", CATEGORIE);
				String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
				if(categoria == "Libri")
				{
					Libro libro = libri.scegliLibro();
					if(libro != null)
					{
						prestiti.addPrestito(utenteLoggato, libro);	
					}
				}
				
				
				ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, false);
				
				continuaMenuPersonale = true;
				break;

			}
			case 4: //PROROGA PRESTITO
			{
				
				continuaMenuPersonale = true;
				break;
			}
			case 5: //VISUALIZZA PRESTITI IN CORSO
			{
				prestiti.prestitiAttiviDi(utenteLoggato.getUser());
				
				continuaMenuPersonale = true;
				break;
			}
			case 6://ANNULLA PRESTITO
			{
				System.out.println("verranno eliminati i tuoi prestiti");
				prestiti.annullaPrestiti(utenteLoggato);
				
				ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, true);
				ServizioFile.salvaSingoloOggetto(fileLibri, libri, true);
			}
		}
	}
}