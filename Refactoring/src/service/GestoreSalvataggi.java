package service;

import java.io.File;
import java.io.IOException;

import interfaces.ISavesManager;
import model.Archivio;
import model.Fruitori;
import model.Prestiti;
import myLib.ServizioFile;

public class GestoreSalvataggi implements ISavesManager
{
	private static final String PATH_FRUITORI = "Fruitori.dat";
	private static final String PATH_ARCHIVIO= "Archivio.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	
	private static File fileFruitori = new File(PATH_FRUITORI);
	private static File fileArchivio = new File(PATH_ARCHIVIO);
	private static File filePrestiti = new File(PATH_PRESTITI);
	
	public void checkFiles(Fruitori fruitori, Archivio archivio, Prestiti prestiti) 
	{
		try 
		{
//			se non c'è il file nel percorso lo crea (vuoto) e salva all'interno l'oggetto corrispondente.
//			così quando dopo si fa il caricamento non ci sono eccezioni
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
	public void checkFile(File file, Object obj) throws IOException 
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

	public Fruitori caricaFruitori() 
	{
		return (Fruitori)ServizioFile.caricaSingoloOggetto(fileFruitori, false);
	}

	public Archivio caricaArchivio() 
	{
		return (Archivio)ServizioFile.caricaSingoloOggetto(fileArchivio, false);
	}

	public Prestiti caricaPrestiti() 
	{
		return (Prestiti)ServizioFile.caricaSingoloOggetto(filePrestiti, false);
	}

	public void salvaFruitori(Fruitori fruitori) 
	{
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);	
	}

	public void salvaPrestiti(Prestiti prestiti) 
	{
		ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);	
	}
	
	public void salvaArchivio(Archivio archivio) 
	{
		ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);	
	}
}
