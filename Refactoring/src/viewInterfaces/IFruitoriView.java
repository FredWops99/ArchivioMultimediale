package viewInterfaces;

import java.util.GregorianCalendar;
import java.util.Vector;
import model.Fruitore;

public interface IFruitoriView 
{
	public abstract void benvenuto(String s);
	public abstract String chiediCognome();
	public abstract GregorianCalendar chiediDataNascita();
	public abstract String chiediNome();
	public abstract String chiediPassword();
	public abstract String chiediUsername();
	public abstract Boolean confermaDati();
	public abstract void confermaIscrizione();
	public abstract String confermaPassword();
	public abstract void iscrizioneNonRinnovata(GregorianCalendar dataInizio, GregorianCalendar dataScandenza);
	public abstract void iscrizioneRinnovata();
	public abstract void messaggioUtenteMinorenne();
	public abstract void nonConfermaIscrizione();
	public abstract void passwordNonCoincidono();
	public abstract void passwordErrata();
	public abstract void stampaDati(Fruitore f);
	public abstract void stampaDati(Vector<Fruitore> fruitori);
	public abstract void UsernameNonDisponibile();
	public abstract void utenteNonTrovato();
	public abstract void utentiRimossi(int i);
}
