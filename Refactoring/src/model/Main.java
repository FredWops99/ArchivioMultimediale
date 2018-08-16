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

import java.util.Vector;
import Menus.MenuAccesso;
import Utility.GestoreSalvataggi;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	private static Fruitori fruitori = new Fruitori();
	private static Archivio archivio = new Archivio();
	private static Prestiti prestiti = new Prestiti();
	
	public static void main(String[] args)
	{		
		GestoreSalvataggi.checkFiles(fruitori, archivio, prestiti);
		
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = GestoreSalvataggi.caricaFruitori();
		archivio = GestoreSalvataggi.caricaArchivio();
		prestiti = GestoreSalvataggi.caricaPrestiti();
		
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		ricostruisciPrestiti();
		
//		segna come "decadute" le iscrizioni in archivio che sono scadute.
		Vector<Fruitore>utentiScaduti = fruitori.controlloIscrizioni();
		prestiti.terminaTuttiPrestitiDi(utentiScaduti);
		prestiti.controlloPrestitiScaduti();
		
		GestoreSalvataggi.salvaFruitori(fruitori);
		GestoreSalvataggi.salvaPrestiti(prestiti);
		
		MenuAccesso.show(fruitori, archivio, prestiti);
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