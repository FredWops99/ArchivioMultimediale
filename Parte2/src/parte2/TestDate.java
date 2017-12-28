package parte2;

import java.util.GregorianCalendar;
import myLib.GestioneDate;

/**
 * Classe di test per testare il funzionamento dei metodi GregorianCalendar.add e GregorianCalendar.compare
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class TestDate 
{
	public static void main(String[] args) 
	{
//		acquisisco data da tastiera
		GregorianCalendar data = GestioneDate.creaDataGuidata(0, 9999);
		System.out.println(GestioneDate.visualizzaData(data));
		
//		le creo uguali a data e poi modifico i campi
//		non faccio "sottraggoGiorni (o aggiungoAnni) = data" sennò dopo si modifica anche data
		GregorianCalendar sottraggoGiorni = new GregorianCalendar(data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH), data.get(GregorianCalendar.DAY_OF_MONTH));
		GregorianCalendar aggiungoAnni = new GregorianCalendar(data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH), data.get(GregorianCalendar.DAY_OF_MONTH));
		
//		sottraggo dieci giorni: tiene conto di anni bisestili e giorni dei mesi (10-3-2016 -> 29-2-2016)
		sottraggoGiorni.add(GregorianCalendar.DAY_OF_MONTH, -10);
		System.out.println("sottratti 10 giorni alla data:\n" + GestioneDate.visualizzaData(sottraggoGiorni));
		
//		aggiungo 5 anni: fatto il 29 febbraio ritorna il 28
		aggiungoAnni.add(GregorianCalendar.YEAR, 5);
		System.out.println("aggiunti 5 anni alla data:\n" + GestioneDate.visualizzaData(aggiungoAnni));
		
//		confronto con oggi:
//		ritorna -1 se data è precedente a oggi, 1 se è futura
		int compare = data.compareTo(GestioneDate.DATA_CORRENTE);
		System.out.print(compare + ": ");
		if(compare==-1) 
			System.out.println("data inserita precendete a oggi");
		else 
			System.out.println("data inserita antecedente a oggi");
	}
}
