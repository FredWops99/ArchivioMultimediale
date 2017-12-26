package parte1;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;
import myLib.GestioneDate;
import myLib.InputDati;

public class Fruitori implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Vector<Fruitore> fruitori;
	
	public Fruitori()
	{
		this.fruitori = new Vector<Fruitore>();
	}
	

	public Vector<Fruitore> getFruitori() 
	{
		return fruitori;
	}

	public void setFruitori(Vector<Fruitore> fruitori) 
	{
		this.fruitori = fruitori;
	}

	public void addFruitore()
	{
		String nome = InputDati.leggiStringa("Inserisci il tuo nome: ");
		String cognome = InputDati.leggiStringa("Inserisci il tuo cognome: ");
		GregorianCalendar dataNascita = GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
		
		//controllo che l'utente sia maggiorenne
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			System.out.println("Ci dispiace, non puoi accedere per questioni di età.");
			return;
		}
		
		String user = InputDati.leggiStringa("Inserisci il tuo username: ");
		String password1;
		//crea Passowrd + controllo password
		boolean corretta = false;
		do
		{
			password1 = InputDati.leggiStringa("Inserisci la password: ");
			String password2 = InputDati.leggiStringa("Inserisci nuovamente la password: ");
			
			if(password1.equals(password2)) 
			{corretta = true;}
			else
			{System.out.println("Le due password non coincidono, riprova");}
			
		}while(!corretta);
		
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;
		//creo il nuovo fruitore
		Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione,user,password1); 
		//aggiungo al vector fruitore il nuovo fruitore
		fruitori.add(f);
	}
	
	public void stampaFruitori()
	{
		System.out.println("Numero fruitori: " + fruitori.size());
		for(int i = 0; i<fruitori.size(); i++)
		{
			fruitori.get(i).stampaDati();
		}
	}
	
	public void controlloIscrizioni()
	{
		for (int i=0; i<fruitori.size(); i++) 
		{
			if(GestioneDate.differenzaAnniDaOggi(fruitori.get(i).getDataIscrizione()) >= 5)
			{
				fruitori.remove(i);
			}	
		}
	}
	
	public void rinnovo(Fruitore fruitore)
	{  
		if(fruitore.fruitoreRinnovabile())
		{
			GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;
			fruitore.setDataIscrizione(dataIscrizione);
			System.out.println("la tua iscrizione è stata rinnovata");
		}
		else
		{
			System.out.println("la tua iscrizione non può essere rinnovata");
		}
		
	}
		
}
