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
	private Fruitori fruitori;
	private Archivio archivio;
	private Prestiti prestiti; 
	
	private static final String PATH_FRUITORI = "Fruitori.dat";
	private static final String PATH_ARCHIVIO= "Archivio.dat";
	private static final String PATH_PRESTITI = "Prestiti.dat";
	
	private static File fileFruitori;
	private static File fileArchivio;
	private static File filePrestiti;
	
	public GestoreSalvataggi()
	{
		this.fruitori = new Fruitori();
		this.archivio = new Archivio();
		this.prestiti = new Prestiti();
		load();
	}
	
	private void load()
	{
//		se non ci sono i file nel percorso li crea (vuoti) e salva all'interno l'oggetto corrispondente.
		checkFiles();
//		avviato il programma carico i fruitori, i libri e i prestiti da file
		fruitori = caricaFruitori();
		archivio = caricaArchivio();
		prestiti = caricaPrestiti();	
	}

	/**
	 * se non ci sono i file nel percorso li crea (vuoti) e salva all'interno l'oggetto corrispondente.
	 * così quando dopo si fa il caricamento non ci sono eccezioni
	 */
	public void checkFiles() 
	{
		fileFruitori = new File(PATH_FRUITORI);
		fileArchivio = new File(PATH_ARCHIVIO);
		filePrestiti = new File(PATH_PRESTITI);
		try 
		{
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
	private void checkFile(File file, Object obj) throws IOException 
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

	public void salvaFruitori() 
	{
		ServizioFile.salvaSingoloOggetto(fileFruitori, fruitori, false);	
	}

	public void salvaPrestiti() 
	{
		ServizioFile.salvaSingoloOggetto(filePrestiti, prestiti, false);	
	}
	
	public void salvaArchivio() 
	{
		ServizioFile.salvaSingoloOggetto(fileArchivio, archivio, false);	
	}
	
	public Fruitori getFruitori() 
	{
		return fruitori;
	}

	public void setFruitori(Fruitori fruitori) 
	{
		this.fruitori = fruitori;
	}
	
	public Archivio getArchivio() 
	{
		return archivio;
	}

	public Prestiti getPrestiti()
	{
		return prestiti;
	}
}
