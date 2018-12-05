package viewInterfaces;

import java.util.Vector;
import interfaces.Risorsa;

public interface IFilmsView extends IRisorseView
{
	public abstract void annoNonPresente(int anno);
	public abstract int chiediAnnoUscita();
	public abstract int chiediDurata();
	public abstract String chiediLingua();
	public abstract String chiediRegista();
	public abstract void registaNonPresente(String regista);
	public abstract void stampaDati(Vector<Risorsa> risorse);
	public abstract IMessaggiSistemaView getMessaggiSistemaView();
}
