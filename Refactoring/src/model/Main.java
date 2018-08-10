/**
VERSIONE 5.0
*Quest’ultima versione dell’applicazione deve supportare la conservazione in archivio di
*informazioni storiche relative a:
*fruitori, iscrizioni, rinnovi di iscrizione e decadenze;
*risorse (ad esempio, si deve tenere traccia di risorse che sono state prestabili in
*passato ma ora non lo sono più);
*prestiti e proroghe degli stessi.
**/

package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import myLib.ServizioFile;
import view.ShowMenus;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	
	
	
	private static final String PATH_FRUITORI = "Fruitori.dat";
	private static final String PATH_ARCHIVIO= "Archivio.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	
	
	
	private static File fileFruitori = new File(PATH_FRUITORI);
	private static File fileArchivio = new File(PATH_ARCHIVIO);
	private static File filePrestiti = new File(PATH_PRESTITI);

	private static Fruitori fruitori = new Fruitori();
	private static Archivio archivio = new Archivio();
	private static Prestiti prestiti = new Prestiti();
	
	
	
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
		fruitori = (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori, false);
		archivio = (Archivio)ServizioFile.caricaSingoloOggetto(fileArchivio, false);
		prestiti = (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti, false);
		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		ricostruisciPrestiti();
		
//		segna come "decadute" le iscrizioni in archivio che sono scadute.
		Vector<Fruitore>utentiScaduti = fruitori.controlloIscrizioni();
//		rimuovo i prestiti che gli utenti scaduti avevano attivi
		prestiti.terminaTuttiPrestitiDi(utentiScaduti);
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);
		
//		elimino i prestiti scaduti
		prestiti.controlloPrestiti();
		ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);
		
		ShowMenus.showMenuAccesso(fruitori, archivio, prestiti);
				
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