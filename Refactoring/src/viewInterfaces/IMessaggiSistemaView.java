package viewInterfaces;

public interface IMessaggiSistemaView 
{
	public abstract void accessoEseguito();
	public abstract void avvisoRimozioneRisorsa();
	public abstract String chiediPasswordOperatore();
	public abstract void cornice();
	public abstract void cornice(boolean spazioInizio, boolean spazioFine);
	public abstract void stampaAddio();
	public abstract void stampaPosizione(int posizione);
}
