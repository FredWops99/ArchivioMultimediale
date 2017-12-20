package parte1;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;
import myLib.GestioneDate;
import myLib.InputDati;

public class Iscrizioni implements Serializable
{
	private static final long serialVersionUID = 1L;
	

	public static void addFruitore(Vector<Fruitore> fruitori)
	{
		String nome = InputDati.leggiStringa("Inserisci il tuo nome \n");
		String cognome = InputDati.leggiStringa("Inserisci il tuo cognome \n");
		GregorianCalendar dataNascita = GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
		
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			System.out.println("Ci dispiace ma non puoi accedere per questioni di età");
			return;
		}
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;
		
		Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione);
		
		fruitori.add(f);
	}
	
	public static void stampaFruitori(Vector<Fruitore> fruitori)
	{
		System.out.println(fruitori.size());
		for(int i = 0; i<fruitori.size(); i++)
		{
			System.out.println("-----------------------------");
			System.out.println("Nome: " + fruitori.get(i).getNome() + "\n");
			System.out.println("Cognome: " + fruitori.get(i).getCognome() + "\n");
			System.out.println("Data di nascita: " + GestioneDate.visualizzaData(fruitori.get(i).getDataNascita())+ "\n");
			System.out.println("Data di iscrizione: " + GestioneDate.visualizzaData(fruitori.get(i).getDataIscrizione()) + "\n");
		}
	}
	
	public static void controlloIscrizioni(Vector<Fruitore> fruitori)
	{
		for (int i=0; i<fruitori.size();i++) 
		{
			if(GestioneDate.differenzaAnniDaOggi(fruitori.get(i).getDataIscrizione()) >= 5)
			{
				fruitori.remove(i);
			}	
		}
	}
	
	public static void rinnovo(Vector<Fruitore> fruitori)
	{  // String nome,String Cognome
		
		System.out.println("");
	}
}
