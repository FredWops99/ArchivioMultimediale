package interfaces;

import java.io.File;
import java.io.IOException;

import model.Archivio;
import model.Fruitori;
import model.Prestiti;

public interface ISavesManager 
{
	public void checkFiles(Fruitori fruitori, Archivio archivio, Prestiti prestiti);
	public void checkFile(File file, Object obj) throws IOException;
	public Fruitori caricaFruitori();
	public Archivio caricaArchivio();
	public Prestiti caricaPrestiti();
	public void salvaFruitori(Fruitori fruitori);
	public void salvaPrestiti(Prestiti prestiti);
	public void salvaArchivio(Archivio archivio);
}
