package model;

import java.io.File;
import java.io.IOException;
import myLib.ServizioFile;

public class GestoreSalvataggi 
{
	private static final String PATH_FRUITORI = "Fruitori.dat";
	private static final String PATH_ARCHIVIO= "Archivio.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	
	private static File fileFruitori = new File(PATH_FRUITORI);
	private static File fileArchivio = new File(PATH_ARCHIVIO);
	private static File filePrestiti = new File(PATH_PRESTITI);
	
	public static void checkFiles(Fruitori fruitori, Archivio archivio, Prestiti prestiti) 
	{
		try 
		{
//			se non c'� il file nel percorso lo crea (vuoto) e salva all'interno l'oggetto corrispondente.
//			cos� quando dopo si fa il caricamento non ci sono eccezioni
			checkFile(fileFruitori, fruitori);
			checkFile(fileArchivio, archivio);
			checkFile(filePrestiti, prestiti);
		}	
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * passo anche un oggetto da salvare nel file quando non esiste e viene creato: utile quando il programma inizia con un caricamento da file: se l'oggetto viene creato vuoto si genera una eccezione
	 * @param file il file del quale si controlla l'esistenza
	 * @param obj l'oggetto da salvare nel file nel caso in cui non esista e debba essere creato
	 * @throws Exception 
	 */
	public static void checkFile(File file, Object obj) throws IOException 
	{
		if (file.exists())
		{
			return;
		}
        else if (file.createNewFile())
        {
    		ServizioFile.salvaSingoloOggetto(file, obj, false);
        }
        else 
        {
        	throw new IOException();
        }
	}

	public static Fruitori caricaFruitori() 
	{
		return (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori, false);
	}

	public static Archivio caricaArchivio() 
	{
		return (Archivio)ServizioFile.caricaSingoloOggetto(fileArchivio, false);
	}

	public static Prestiti caricaPrestiti() 
	{
		return (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti, false);
	}

	public static void salvaFruitori(Fruitori fruitori) 
	{
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);	
	}

	public static void salvaPrestiti(Prestiti prestiti) 
	{
		ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);	
	}
	
	public static void salvaArchivio(Archivio archivio) 
	{
		ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);	
	}
}
