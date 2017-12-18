import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Iscrizioni implements Serializable{


	public static void  addFruitore(Vector<Fruitore> fruitori){
		
		
		String nome = InputDati.leggiStringa("Inserisci il tuo nome \n");
		String cognome = InputDati.leggiStringa("Inserisci il tuo cognome \n");
		Data dataNascita = InputDati.leggiData();
		
		if(dataNascita.età() < 18){
			System.out.println("Ci dispiace ma non puoi accedere per questioni di età");
		return;
		}
		
		GregorianCalendar gc = new GregorianCalendar();
		int anno = gc.get(Calendar.YEAR);
		int mese = gc.get(Calendar.MONTH) + 1;
		int giorno = gc.get(Calendar.DATE);
		
		Data dataIscrizione = new Data();
		dataIscrizione.setData(""+giorno,""+mese,""+anno);
		
		Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione);
		
		fruitori.add(f);
		
	}
	
	public static void stampaFruitori(Vector<Fruitore> fruitori){
		
		System.out.println(fruitori.size());
		for(int i = 0;i<fruitori.size();i++){
			
			System.out.println("-----------------------------");
			System.out.println("Nome: " + fruitori.get(i).getNome() + "\n");
			System.out.println("Cognome: " + fruitori.get(i).getCognome() + "\n");
			System.out.println("Data di nascita: " + fruitori.get(i).getDataNascita().stampaData() + "\n");
			System.out.println("Data di iscrizione: " + fruitori.get(i).getDataIscrizione().stampaData() + "\n");
		}
	}
	
	public static void controlloIscrizioni(Vector<Fruitore> fruitori){
		
		for (int i=0; i<fruitori.size();i++) {
			
			GregorianCalendar gc = new GregorianCalendar();
			int annoCorrente = gc.get(Calendar.YEAR);
			int meseCorrente = gc.get(Calendar.MONTH) + 1;
			int giornoCorrente = gc.get(Calendar.DATE);
			
			Data dataCorrente = new Data();
			dataCorrente.setData(""+giornoCorrente,""+meseCorrente,""+annoCorrente);
			
			if(Data.comparaDate(dataCorrente, fruitori.get(i).getDataIscrizione())){
				fruitori.remove(i); // se è scaduta l'iscrizione rimuovo il fruitore
			}
			
		}
	}
	
	public static void rinnovo(Vector<Fruitore> fruitori){  // String nome,String Cognome
		
		System.out.println("");
	}
}
