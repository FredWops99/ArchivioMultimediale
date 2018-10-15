package interfaces;

import model.Fruitori;
import model.Prestiti;
import model.Risorse;

public interface ISavesManager 
{
	public void checkFiles();
	public Fruitori caricaFruitori();
	public Risorse caricaRisorse();
	public Prestiti caricaPrestiti();
	public void salvaFruitori();
	public void salvaPrestiti();
	public void salvaRisorse();
	public Fruitori getFruitori();
	public Risorse getRisorse();
	public Prestiti getPrestiti();
}
