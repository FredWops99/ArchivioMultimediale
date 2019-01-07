package service;

import java.io.FileInputStream;
import java.io.IOException;
import interfaces.ISavesManager;
import model.Fruitori;
import model.Prestiti;
import model.Risorse;
import model.Storico;

/**
 * Classe main del programma Archivio Multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Main 
{
	public static void main(String[] args)
	{		
//		carico il file delle PROPRIETA' DI SISTEMA, nel quale ci sono coppie "chiave-valore" che utilizzo per definire la classe da instanziare quando questa
//		implementa un'interfaccia:
//		usato per esempio per tutte le view
//		Fornisce Protected Variation rispetto a cambiamenti nella scelta della classe da usare (basta che implementi stessa interfaccia)
		try 
		{
			System.getProperties().load(new FileInputStream("config.properties"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		ISavesManager gestoreSalvataggi = null;
//		per gestoreSalvataggi: così Main dipende solo da interface ISavesManager e non dall'implementazione GestoreSalvataggi (cosa che succederebbe con l'instanziamento)
		try 
		{
			gestoreSalvataggi = (ISavesManager)Class.forName(System.getProperty("SavesManager")).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		Fruitori fruitori = gestoreSalvataggi.getFruitori();
		assert fruitori != null;
		Risorse risorse = gestoreSalvataggi.getRisorse();
		assert risorse != null;
		Prestiti prestiti = gestoreSalvataggi.getPrestiti();
		assert prestiti != null;
	
//		associa risorsa in Prestiti a risorsa in Archivio: quando si salva e carica i riferimenti si modificano (verificato con hashcode)
		prestiti.ricostruisciPrestiti(risorse);
		
		Storico storico = new Storico(prestiti, fruitori, risorse);
		
//		coordina i compiti
		MainFacade mainFacade = new MainFacade(risorse, fruitori, prestiti, storico, gestoreSalvataggi);
		mainFacade.start();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}