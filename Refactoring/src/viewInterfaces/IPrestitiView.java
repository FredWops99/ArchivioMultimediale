package viewInterfaces;

import interfaces.Risorsa;
import model.Prestito;

public interface IPrestitiView 
{
	public abstract int chiediRisorsaDaRinnovare(int size);
	public abstract int chiediRisorsaDaTerminare(int size);
	public abstract void messaggioUtenteMinorenne();
	public abstract void noPrestiti();
	public abstract void noPrestitiAttivi();
	public abstract void noRinnovi();
	public abstract void numeroRisorseTornateDaPrestito(int tornati);
	public abstract void prenotazioneEffettuata(Risorsa risorsa);
	public abstract void prestitiEliminati();
	public abstract void prestitoDaTerminare();
	public abstract void prestitoGi‡Prorogato();
	public abstract void prestitoNonRinnovabile(Prestito prestito);
	public abstract void prestitoTerminato();
	public abstract void raggiunteRisorseMassime(String categoria);
	public abstract void risorsaPosseduta();
	public abstract void selezionaRinnovo();
	public abstract void visualizzaPrestito(Prestito prestito);
}
