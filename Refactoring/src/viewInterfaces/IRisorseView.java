package viewInterfaces;

import interfaces.Risorsa;

public interface IRisorseView 
{
	public abstract void aggiuntaNonRiuscita(Class<?> c);
	public abstract void aggiuntaRiuscita(Class<?> c);
	public abstract int chiediNumeroLicenze();
	public abstract int chiediRicorrenzaDaRimuovere(int i);
	public abstract String chiediRisorsaDaRimuovere();
	public abstract String chiediTitolo();
	public abstract void copieTutteInPrestito(String risorsa);
	public abstract void nessunaCorrispondenza(Class<?> c);
	public abstract void noRisorseDisponibili(String risorsa);
	public abstract void numeroRicorrenza(int i);
	public abstract void numeroRisorseInArchivio(int size, String risorsa);
	public abstract void piùRisorseStessoTitolo(String categoria, String titolo);
	public abstract void rimozioneAvvenuta();
	public abstract void risorsaNonPresente();
	public abstract void risorsaNonPresente(String categoria, String titolo);
	public abstract void risorseInArchivio(Class<?> c);
	public abstract int selezionaRisorsa(int size, Class<?> c);
	public abstract void stampaDati(Risorsa risorsa, boolean perPrestito);
	public abstract void stampaSottoCategoria(Risorsa risorsa);
	public abstract void stampaTitolo(Risorsa risorsa);
	public abstract void unaRisorsaInArchivio(Class<?> c);
	public abstract IMessaggiSistemaView getMessaggiSistemaView();
}
