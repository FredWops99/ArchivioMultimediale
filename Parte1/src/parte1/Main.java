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
package parte1;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import myLib.MyMenu;
import myLib.ServizioFile;

public class Main implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Login", "Rinnova iscrizione", "Visualizza fruitori"};
	private static final String PATH = "Fruitori.dat";
	
	private static boolean continuaMenuIniziale;
	private static File fileFruitori = new File(PATH);	
//	serve a tutti i metodi ???
	private static Fruitori fruitori = new Fruitori();
	private static Fruitore utenteLoggato = null;
	
	public static void main(String[] args)
	{		
//		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file

		try 
		{
//			se non c'è il file lo crea
			ServizioFile.checkFile(fileFruitori);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
//		avviato il programma carico i fruitori dal file
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori); 
		
//		elimino i fruitori scaduti (elimino o no??)
		fruitori.controlloIscrizioni();		
		
		MyMenu menuIniziale=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE);
		continuaMenuIniziale=true;
		do
		{
			gestisciMenuIniziale(menuIniziale.scegli());
		}
		while(continuaMenuIniziale);
	}
	
	private static void gestisciMenuIniziale(int scelta)
	{
		continuaMenuIniziale=true;
		
		switch(scelta)
		{
			case 0:	//EXIT
			{
				System.out.println("Grazie per aver usato ArchivioMultimediale!");
				
				continuaMenuIniziale=false;
				break;
			}
			case 1:	//registrazione nuovo fruitore
			{					
				fruitori.addFruitore();
						
				ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file "fileFruitori"
				
				continuaMenuIniziale=true;//torna al menu
				
				break;				
			}
			case 2:	//login
			{
				System.out.println("WORK IN PROGRESS");
				
//				va assegnato utenteLoggato
				
				continuaMenuIniziale=true;
				
				break;
			}
			
			case 3:	//rinnovo iscrizione
			{
//				
				System.out.println("WORK IN PROGRESS");
				
				if(utenteLoggato == (null))
				{
					System.out.println("devi effettuare il login per accedere a questa sezione");
				}
				
//				dopo login utente può accedere a questa sezione

				else
				{
					
				}
				continuaMenuIniziale = true;
				
				break;
			}
			
			case 4:	//visualizza fruitori
			{
				fruitori.stampaFruitori();
				
				continuaMenuIniziale = true;
				
				break;
			}
		}	
	}
}