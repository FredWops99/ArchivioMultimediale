package interfaces;

import model.Archivio;
import model.Fruitori;
import model.Prestiti;

public interface ISavesManager 
{
	public void checkFiles();
	public Fruitori caricaFruitori();
	public Archivio caricaArchivio();
	public Prestiti caricaPrestiti();
	public void salvaFruitori();
	public void salvaPrestiti();
	public void salvaArchivio();
}
