package view;

import java.util.Vector;

import interfaces.Risorsa;
import model.Film;
import myLib.GestioneDate;
import myLib.InputDati;

public class FilmsView extends RisorseView
{
	public static int chiediDurata() 
	{
		return InputDati.leggiInteroPositivo("Inserisci la durata del film (in minuti): ");
	}

	public static int chiediAnnoUscita() 
	{
		return InputDati.leggiIntero("Inserisci l'anno di uscita: ", Film.getAnnoPrimaPellicola(), GestioneDate.ANNO_CORRENTE);
	}

	public static String chiediLingua() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la lingua del film: ");
	}

	public static String chiediRegista() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il regista: ");	
	}
	
	public static void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti film il cui anno di uscita è " + annoUscita);	
	}

	public static void registaNonPresente(String nomeRegista) 
	{
		System.out.println("In archivio non sono presenti film il cui regista è " + nomeRegista);	
	}
	
	public static void piùRisorseStessoTitolo(String titolo) 
	{
		System.out.println("Sono presenti più films dal titolo \"" + titolo + "\": ");	
	}
	
	public static void noRisorseDisponibili() 
	{
		System.out.println("In archivio non sono presenti films disponibili");
	}
	
	public static void risorsaNonPresente(String s)
	{
		System.out.println("In archivio non sono presenti films il cui titolo è: " + s );
	}

//	non posso spostarlo in RisorseView perchè Film e Libro hanno campi diversi
//	potrebbe essere come abstract e poi FilmsView e LibriView devono implementarlo
//	metodi statici non possono essere astratti :(
	public static void stampaDati(Vector<Risorsa> filmDaStampare) 
	{
		if(filmDaStampare.isEmpty())
		{
			noRisorseDisponibili();
		}
		else
		{
			if(filmDaStampare.size() == 1)
			{
				unaRisorsaInArchivio(Film.class);
			}
			else
			{
				RisorseView.numeroRisorseInArchivio(filmDaStampare.size(), "films");
			}
			
			for(int i = 0; i < filmDaStampare.size(); i++)
			{
//				stampa la sottocategoria solo se è il primo elemento o se il sottogenere è cambiato (sono in ordine nel vettore)
				if(i == 0 || filmDaStampare.get(i).getSottoCategoria() != filmDaStampare.get(i-1).getSottoCategoria())
				{
					MessaggiSistemaView.cornice(true, false);
					RisorseView.stampaSottoCategoria(filmDaStampare.get(i));
					MessaggiSistemaView.cornice();
				}
				System.out.println("titolo: " + filmDaStampare.get(i).getTitolo());
			}
		}
	}
}
