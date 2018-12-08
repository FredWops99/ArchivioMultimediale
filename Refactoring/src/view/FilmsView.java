package view;

import java.util.Vector;

import interfaces.Risorsa;
import model.Film;
import myLib.GestioneDate;
import myLib.InputDati;
import viewInterfaces.IFilmsView;
import viewInterfaces.IMessaggiSistemaView;

public class FilmsView extends RisorseView implements IFilmsView
{
	private IMessaggiSistemaView messaggiSistemaView = MessaggiSistemaView.getInstance();
	public IMessaggiSistemaView getMessaggiSistemaView() 
	{
		return messaggiSistemaView;
	}
	
	public int chiediDurata() 
	{
		return InputDati.leggiInteroPositivo("Inserisci la durata del film (in minuti): ");
	}

	public int chiediAnnoUscita() 
	{
		return InputDati.leggiIntero("Inserisci l'anno di uscita: ", Film.getAnnoPrimaPellicola(), GestioneDate.ANNO_CORRENTE);
	}

	public String chiediLingua() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la lingua del film: ");
	}

	public String chiediRegista() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il regista: ");	
	}
	
	public void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti film il cui anno di uscita è " + annoUscita);	
	}

	public void registaNonPresente(String nomeRegista) 
	{
		System.out.println("In archivio non sono presenti film il cui regista è " + nomeRegista);	
	}

//	non posso spostarlo in RisorseView perchè Film e Libro hanno campi diversi
//	potrebbe essere come abstract e poi FilmsView e LibriView devono implementarlo
//	metodi statici non possono essere astratti :(
	public void stampaDati(Vector<Risorsa> filmDaStampare) 
	{
		if(filmDaStampare.isEmpty())
		{
			noRisorseDisponibili("films");
		}
		else
		{
			if(filmDaStampare.size() == 1)
			{
				unaRisorsaInArchivio(Film.class);
			}
			else
			{
				numeroRisorseInArchivio(filmDaStampare.size(), "films");
			}
			
			for(int i = 0; i < filmDaStampare.size(); i++)
			{
//				stampa la sottocategoria solo se è il primo elemento o se il sottogenere è cambiato (sono in ordine nel vettore)
				if(i == 0 || filmDaStampare.get(i).getSottoCategoria() != filmDaStampare.get(i-1).getSottoCategoria())
				{
					messaggiSistemaView.cornice(true, false);
					stampaSottoCategoria(filmDaStampare.get(i));
					messaggiSistemaView.cornice();
				}
				System.out.println("titolo: " + filmDaStampare.get(i).getTitolo());
			}
		}
	}
}
