package view;

import java.util.Vector;

import model.Libri;
import model.Libro;
import myLib.GestioneDate;
import myLib.InputDati;

public class LibriView extends RisorseView
{
//	public static void stampaDati(Libro libro, boolean perPrestito) 
//	{
//		System.out.println(libro.toString(perPrestito));	
//	}
//	
//	public static String chiediTitolo() 
//	{
//		return InputDati.leggiStringaNonVuota("Inserisci il titolo del libro: ");
//	}
	
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
	
//	public static int chiediNumeroLicenze()
//	{
//		return InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
//	}
	
//	public static void aggiuntaRiuscita()
//	{
//		System.out.println("Libro aggiunto con successo!");
//	}
	
	public static void aggiuntaNonRiuscita()
	{
		System.out.println("Il libro è già presente in archivio");
	}
	
//	public static String chiediLibroDaRimuovere() 
//	{
//		return InputDati.leggiStringaNonVuota("Inserisci il titolo del libro da rimuovere: ");
//	}
	
//	public static void libroNonPresente()
//	{
//		System.out.println("Siamo spiacenti, il libro non è presente nell'archivio");
//	}
	
//	public static void libroNonPresente(String s)
//	{
//		System.out.println("In archivio non sono presenti libri il cui titolo è: " + s );
//	}
	
//	public static void rimozioneAvvenuta()
//	{
//		System.out.println("La risorsa è stata eliminata dalle risorse prestabili (verrà conservata in archivio per motivi storici)");
//	}
	
//	public static void piùLibriStessoTitolo(String titolo)
//	{
//		System.out.println("Sono presenti più libri dal titolo \"" + titolo + "\": ");
//	}
	
//	public static void numeroRicorrenza(int pos) 
//	{
//		System.out.println("\nRicorrenza " + ++pos + ":");
//	}
	
//	public static int chiediRicorrenzaDaRimuovere(Vector<Integer> posizioniRicorrenze) 
//	{
//		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
//	}
	
	public static void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti libri il cui anno di pubblicazione è " + annoUscita);	
	}
	
	public static void autoreNonPresente(String nomeAutore)
	{
		System.out.println("In archivio non sono presenti libri il cui autore è " + nomeAutore);
	}
	
//	public static void noLibriDisponibili()
//	{
//		System.out.println("In archivio non sono presenti libri disponibili");
//	}
	
//	public static void unLibroInArchivio()
//	{
//		System.out.println("\nE' presente un libro in archivio: ");
//	}
	
	public static void numeroLibriInArchivio(Vector<Libro> libri)
	{
		System.out.println("\nSono presenti " + libri.size() + " libri in archivio: ");
	}
	
	public static void sottocategoria(Libro l)
	{
		System.out.println("Sottocategoria: " + l.getSottoCategoria());
	}
	
	public static void genere(Libro l)
	{
		System.out.println("Genere: " + l.getGenere());
	}
	
	public static void titolo(Libro l)
	{
		
		System.out.println("Titolo: " + l.getTitolo());
	}
	
	public static void stampaPosizione(int i)
	{
		System.out.println("\n" + (i+1) + ") ");
	}
	
	public static int selezionaPrestito(Vector<Libro> libri)
	{
		return InputDati.leggiIntero("Seleziona il libro che vuoi ricevere in prestito (0 per annullare): ", 0, libri.size());
	}
	
	public static void copieTutteInPrestito(String titolo)
	{
		System.out.println("Tutte le copie di \"" + titolo + "\" sono in prestito!");	
	}
	
//	public static void nessunaCorrispondenza()
//	{
//		System.out.println("Nessun libro corrispondente al criterio di ricerca");
//	}
	
	public static void libriInArchivio()
	{
		System.out.println("\nLibri in archivio: \n");	
	}

	public static void stampaDati(Vector<Libro> libriDaStampare) 
	{
		if(libriDaStampare.size() == 0)
		{
			noRisorseDisponibili(Libri.class);
			return;
		}
		
		if(libriDaStampare.size() == 1)
		{
			LibriView.unaRisorsaInArchivio(Libro.class);
		}
		else//piu di un libro prestabile in archivio
		{
			LibriView.numeroLibriInArchivio(libriDaStampare);
		}
		for(int j = 0; j < libriDaStampare.size(); j++)
		{				
			MessaggiSistemaView.cornice();
			if(!libriDaStampare.get(j).getGenere().equals("-"))
			{
				LibriView.sottocategoria(libriDaStampare.get(j));
				LibriView.genere(libriDaStampare.get(j));
				MessaggiSistemaView.cornice();
				LibriView.titolo(libriDaStampare.get(j));
//				conteggio al contrario così quando elimino un elemento non salto il successivo
				for(int i = libriDaStampare.size()-1; i >= j+1; i--) 
				{
					if(libriDaStampare.get(j).getSottoCategoria().equals(libriDaStampare.get(i).getSottoCategoria()))
					{
						if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
						{
							LibriView.titolo(libriDaStampare.get(j));
							libriDaStampare.remove(i);
						}
					}
				}
			}
			else
			{
				LibriView.sottocategoria(libriDaStampare.get(j));
				MessaggiSistemaView.cornice();
				LibriView.titolo(libriDaStampare.get(j));
//				conteggio al contrario così quando elimino un elemento non salto il successivo
				for(int i = libriDaStampare.size()-1; i >= j+1; i--)
				{
					if(libriDaStampare.get(j).getGenere().equals(libriDaStampare.get(i).getGenere()))
					{
						LibriView.titolo(libriDaStampare.get(j));
						libriDaStampare.remove(i);
					}
				}
			}
		}	
	}
}

