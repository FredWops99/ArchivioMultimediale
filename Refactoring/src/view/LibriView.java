package view;

import java.util.Vector;

import interfaces.Risorsa;
import model.Libro;
import myLib.GestioneDate;
import myLib.InputDati;

public class LibriView extends RisorseView
{	
	public static int chiediPagine()
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di pagine: ");
	}
	
	public static int chiediAnnoPubblicazione()
	{
		return InputDati.leggiInteroConMassimo("Inserisci l'anno di pubblicazione: ", GestioneDate.ANNO_CORRENTE);
	}
	
	public static String chiediCasaEditrice()
	{
		return InputDati.leggiStringaNonVuota("Inserisci la casa editrice: ");
	}
	
	public static String chiediLingua() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la lingua del testo: ");
	}
	
	public static String chiediAutore()
	{
		return InputDati.leggiStringaNonVuota("Inserisci l'autore: ");
	}
	
	public static Boolean ciSonoAltriAutori()
	{
		return InputDati.yesOrNo("ci sono altri autori? ");
	}
	
	public static void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoUscita);	
	}
	
	public static void autoreNonPresente(String nomeAutore)
	{
		System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
	}
	
	private static void stampaGenere(Risorsa risorsa) 
	{
		System.out.println("Genere: " + ((Libro) risorsa).getGenere());	
	}
	
	public static void piùRisorseStessoTitolo(String titolo) 
	{
		System.out.println("Sono presenti più libri dal titolo \"" + titolo + "\": ");	
	}
	
	public static void noRisorseDisponibili() 
	{
		System.out.println("In archivio non sono presenti libri disponibili");
	}
	
	public static void risorsaNonPresente(String s)
	{
		System.out.println("In archivio non sono presenti libri il cui titolo è: " + s );
	}

	/**
	 * stampa i libri raggruppandoli per sottocategorie
	 * @param libriDaStampare
	 */
	public static void stampaDatiPerCategorie(Vector<Libro> libriDaStampare) 
	{
		if(libriDaStampare.isEmpty())
		{
			noRisorseDisponibili();
		}
		else
		{
			if(libriDaStampare.size() == 1)
			{
				RisorseView.unaRisorsaInArchivio(Libro.class);
			}
			else//piu di un libro prestabile in archivio
			{
				RisorseView.numeroRisorseInArchivio(libriDaStampare.size(), "libri");
			}
			
			for(int j = 0; j < libriDaStampare.size(); j++)
			{				
				MessaggiSistemaView.cornice(true, false);
				if(!libriDaStampare.get(j).getGenere().equals("-"))
				{
					stampaSottoCategoria(libriDaStampare.get(j));
					stampaGenere(libriDaStampare.get(j));
					MessaggiSistemaView.cornice();
					
					RisorseView.stampaTitolo(libriDaStampare.get(j));
//					conteggio al contrario così quando elimino un elemento non salto il successivo
//					diverso da Films perchè i libri vengono ordinati anche per Genere (che Film non ha)
					for(int i = libriDaStampare.size() - 1; i >= j + 1; i--) 
					{
						if(libriDaStampare.get(j).getSottoCategoria().equals(libriDaStampare.get(i).getSottoCategoria()))
						{
							if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
							{
								RisorseView.stampaTitolo(libriDaStampare.get(j));
								libriDaStampare.remove(i);
							}
						}
					}
				}
				else
				{
					stampaSottoCategoria(libriDaStampare.get(j));
					MessaggiSistemaView.cornice();
					RisorseView.stampaTitolo(libriDaStampare.get(j));
//					conteggio al contrario così quando elimino un elemento non salto il successivo
					for(int i = libriDaStampare.size()-1; i >= j+1; i--)
					{
						if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
						{
							RisorseView.stampaTitolo(libriDaStampare.get(j));
							libriDaStampare.remove(i);
						}
					}
				}
			}	
		}		
	}
}