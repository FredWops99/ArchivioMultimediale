package testing;

import controllerMVC.FruitoriController;
import interfaces.ISavesManager;
import model.Fruitori;
import service.GestoreSalvataggi;

/**
 * per verificare funzionamento gestoreSalvataggi
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class TestSalvataggio 
{
	public static void main(String[] args)
	{
		ISavesManager gestoreSalvataggi = new GestoreSalvataggi();

		Fruitori fruitori = new Fruitori();
		FruitoriController fc = new FruitoriController(fruitori);
		
		fc.addFruitore();
		gestoreSalvataggi.salvaFruitori();
		Fruitori fruitori2 = new Fruitori();
		fruitori2 = gestoreSalvataggi.caricaFruitori();
		System.out.println(fruitori2.getFruitori().get(0).toString());
	}
	

}
