package view;

import java.util.Vector;

import interfaces.Risorsa;
import model.Libro;
import myLib.GestioneDate;
import myLib.InputDati;
import viewInterfaces.ILibriView;
import viewInterfaces.IMessaggiSistemaView;

public class LibriView extends RisorseView implements ILibriView
{	
	private IMessaggiSistemaView messaggiSistemaView = MessaggiSistemaView.getInstance();
	public IMessaggiSistemaView getMessaggiSistemaView() 
	{
		return this.messaggiSistemaView;
	}
	public int chiediPagine()
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di pagine: ");
	}
	
	public int chiediAnnoPubblicazione()
	{
		return InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: ", GestioneDate.ANNO_CORRENTE);
	}
	
	public String chiediCasaEditrice()
	{
		return InputDati.leggiStringaNonVuota("Inserisci la casa editrice: ");
	}
	
	public String chiediLingua() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la lingua del testo: ");
	}
	
	public String chiediAutore()
	{
		return InputDati.leggiStringaNonVuota("Inserisci l'autore: ");
	}
	
	public Boolean ciSonoAltriAutori()
	{
		return InputDati.yesOrNo("ci sono altri autori? ");
	}
	
	public void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoUscita);	
	}
	
	public void autoreNonPresente(String nomeAutore)
	{
		System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
	}
	
	public void stampaGenere(Risorsa risorsa) 
	{
		System.out.println("Genere: " + ((Libro) risorsa).getGenere());	
	}
	
	/**
	 * stampa i libri raggruppandoli per sottocategorie
	 * @param libriDaStampare
	 */
	public void stampaDatiPerCategorie(Vector<Risorsa> libriDaStampare) 
	{
		if(libriDaStampare.isEmpty())
		{
			noRisorseDisponibili("libri");
		}
		else
		{
			if(libriDaStampare.size() == 1)
			{
				unaRisorsaInArchivio(Libro.class);
			}
			else//piu di un libro prestabile in archivio
			{
				numeroRisorseInArchivio(libriDaStampare.size(), "libri");
			}
			
			for(int j = 0; j < libriDaStampare.size(); j++)
			{				
				messaggiSistemaView.cornice(true, false);
//				devo aggiungere cast in Libro perchè passo un vettore di risorse
				if(!((Libro)libriDaStampare.get(j)).getGenere().equals("-"))
				{
					stampaSottoCategoria(libriDaStampare.get(j));
					stampaGenere(libriDaStampare.get(j));
					messaggiSistemaView.cornice();
					
					stampaTitolo(libriDaStampare.get(j));
//					conteggio al contrario così quando elimino un elemento non salto il successivo
//					diverso da Films perchè i libri vengono ordinati anche per Genere (che Film non ha)
					for(int i = libriDaStampare.size() - 1; i >= j + 1; i--) 
					{
						if(libriDaStampare.get(j).getSottoCategoria().equals(libriDaStampare.get(i).getSottoCategoria()))
						{
							if(((Libro)libriDaStampare.get(j)).getGenere().equals(((Libro)libriDaStampare.get(i)).getGenere()))
							{
								stampaTitolo(libriDaStampare.get(j));
								libriDaStampare.remove(i);
							}
						}
					}
				}
				else
				{
					stampaSottoCategoria(libriDaStampare.get(j));
					messaggiSistemaView.cornice();
					stampaTitolo(libriDaStampare.get(j));
//					conteggio al contrario così quando elimino un elemento non salto il successivo
					for(int i = libriDaStampare.size()-1; i >= j+1; i--)
					{
						if(((Libro)libriDaStampare.get(j)).getGenere().equals(((Libro)libriDaStampare.get(i)).getGenere()))
						{
							stampaTitolo(libriDaStampare.get(j));
							libriDaStampare.remove(i);
						}
					}
				}
			}	
		}		
	}
}