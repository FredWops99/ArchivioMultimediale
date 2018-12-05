package viewInterfaces;

import java.util.Vector;
import interfaces.Risorsa;

public interface ILibriView extends IRisorseView
{
	public abstract void annoNonPresente(int anno);
	public abstract void autoreNonPresente(String autore);
	public abstract int chiediAnnoPubblicazione();
	public abstract String chiediAutore();
	public abstract String chiediCasaEditrice();
	public abstract String chiediLingua();
	public abstract int chiediPagine();
	public abstract Boolean ciSonoAltriAutori();
	public abstract void stampaDatiPerCategorie(Vector<Risorsa> risorse);
	public abstract void stampaGenere(Risorsa risorsa);
	public abstract IMessaggiSistemaView getMessaggiSistemaView();
}
