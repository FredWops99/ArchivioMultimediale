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
import java.util.Vector;
import myLib.ServizioFile;

public class Main implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	
//	far diventare FRUITORI una classe con dentro tutti i metodi che ora sono presenti in ISCRIZIONI?
	static Vector<Fruitore> fruitori = new Vector<>();
	
	private final static String PATH = "Fruitori.dat";
	static File fileFruitori = new File(PATH);	
	
	@SuppressWarnings("unchecked")	//per il cast in Vector<>
	public static void main(String[] args)
	{
//		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file

		try 
		{
			checkFile(fileFruitori);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		fruitori = (Vector<Fruitore>)ServizioFile.caricaSingoloOggetto(fileFruitori); //carico i fruitori da file
		
		Iscrizioni.controlloIscrizioni(fruitori);		
		
		
		Iscrizioni.addFruitore(fruitori);
		Iscrizioni.stampaFruitori(fruitori);
		
				
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori); // salvo i fruitori nel file

	}

//	da mettere in ServizioFile?
	private static void checkFile(File file) throws IOException 
	{
		if (file.exists())
            System.out.println("Il file " + file.getPath() + " esiste");
        else if (file.createNewFile())
            System.out.println("Il file " + file.getPath() + " è stato creato");
        else
            System.out.println("Il file " + file.getPath() + " non può essere creato");
	}


}


