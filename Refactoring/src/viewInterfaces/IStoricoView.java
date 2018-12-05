package viewInterfaces;

import model.Fruitore;
import model.Prestito;

public interface IStoricoView 
{
	public abstract void dateRinnovo(String s);
	public abstract void filmPrestabileInPassato(String film);
	public abstract void filmsPrestabiliInPassato();
	public abstract void fruitoreDecaduto(Fruitore f);
	public abstract void fruitoreRiiscritto(String s);
	public abstract void fruitoriDecaduti();
	public abstract void iscrizioniRinnovate();
	public abstract void libriPrestabiliInPassato();
	public abstract void libroPrestabileInPassato(String s);
	public abstract void nessunFruitoreHaRinnovato();
	public abstract void nessuno();
	public abstract void noIscrizioniDecadute();
	public abstract void noPrestitiInAnnoSelezionato();
	public abstract void noPrestitiRinnovati();
	public abstract void noPrestitiTerminati();
	public abstract void noPrestitiTerminatiInAnticipo();
	public abstract void numeroPrestitiPerUtente(String utente, int nPrestiti);
	public abstract void prestitiPerAnnoSolare(int nPrestiti);
	public abstract void prestitiProrogati();
	public abstract void prestitiTerminati();
	public abstract void prestitiTerminatiInAnticipo();
	public abstract void prestitoProrogato(Prestito prestito);
	public abstract void prestitoTerminato(Prestito prestito);
	public abstract void prestitoTerminatoInAnticipo(Prestito prestito);
	public abstract void proroghePerAnnoSolare(int n);
	public abstract void risorsaConPiùPrenotazioniInAnnoSelezionato(String titolo, int max);
	public abstract int selezionaAnno();
}
