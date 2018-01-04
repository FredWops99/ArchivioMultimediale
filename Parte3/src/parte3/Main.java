/**
 * 	Le risorse multimediali (file) vengono conservate in varie cartelle di un server, una per
	ogni categoria di risorse che possono essere concesse in prestito. Una categoria può
	articolarsi in più sottocategorie (a un solo livello di profondità), ciascuna delle quali
	corrisponde a una sottocartella della cartella relativa alla categoria di riferimento. Se una
	cartella - relativa a una categoria - contiene delle sottocartelle, essa non contiene
	direttamente delle risorse, cioè le risorse sono tutte collocate nelle sottocartelle. Una
	risorsa è collocata in una singola (sotto)cartella, cioè non esistono più copie della
	medesima risorsa collocate in (sotto)cartelle distinte.
	L’applicazione non deve occuparsi delle (sotto)cartelle del server né dei file in esse
	contenuti. L’applicazione deve invece occuparsi della conservazione di un “archivio”
	persistente locale che descriva le risorse multimediali, classificate per categorie ed
	eventuali sottocategorie, riflettendo la suddivisione delle risorse presenti sul server.
	Ciascuna risorsa è dotata di un suo numero di licenze d’uso (perenni), che può differire
	da quello di altre risorse. Esistono alcune informazioni che caratterizzano ciascuna
	risorsa, dipendenti dalla categoria della risorsa stessa. La seconda versione
	dell’applicazione considera una sola categoria di risorse, i libri. Ciascun libro è descritto
	da vari campi, ad esempio, titolo, autore/i, numero di pagine, anno di pubblicazione, casa
	editrice, lingua, genere.

	La versione corrente dell’applicazione deve consentire all’operatore di archiviare le
	descrizioni delle risorse e visualizzare il contenuto dell’archivio, secondo le specifiche
	seguenti:
		- aggiunta (della descrizione) di una risorsa, completa in ogni suo campo, a una
		(sotto)categoria in archivio;
		- rimozione (della descrizione) di una risorsa dall’archivio;
		- visualizzazione dell’elenco delle risorse per (sotto)categoria
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
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali"};//"modifica dati" ?
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String[] MENU_OPERATORE_VOCI = {"Visualizza fruitori","Aggiungi un libro","Rimuovi un libro","Visualizza l'elenco dei libri","Visualizza dettagli libro"};
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
			case 2://AGGIUNGI LIBRO
			{
				libri.addLibro();
				ServizioFile.salvaSingoloOggetto(fileArchivio, libri, false);
				
				continuaMenuOperatore=true;
				break;
			}
			case 3://RIMUOVI LIBRO
			{
				libri.removeLibro();
				ServizioFile.salvaSingoloOggetto(fileArchivio, libri, false);
				
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