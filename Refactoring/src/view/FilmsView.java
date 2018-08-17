package view;

import java.util.Vector;
import model.Film;
import myLib.GestioneDate;
import myLib.InputDati;

public class FilmsView 
{
	
	public static void stampaDati(Film film, boolean perPrestito) 
	{
		System.out.println(film.toString(perPrestito));
	}

	public static String chiediTitolo() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del film: ");
	}

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

	public static int chiediNumeroLicenze() 
	{
		return InputDati.leggiInteroPositivo("Inserisci il numero di licenze disponibili: ");
	}

	public static void aggiuntaRiuscita() 
	{
		System.out.println("Film aggiunto con successo!");	
	}

	public static String chiediFilmDaRimuovere() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il titolo del film da rimuovere: ");
	}

	public static void filmNonPresente() 
	{
		System.out.println("Siamo spiacenti, il film non è presente nell'archivio");	
	}

	public static void filmNonPresente(String titolo) 
	{
		System.out.println("In archivio non sono presenti film il cui titolo è " + titolo);
	}
	
	public static void rimozioneAvvenuta() 
	{
		System.out.println("Rimozione avvenuta con successo!");	
	}

	public static void piùFilmStessoTitolo(String titolo) 
	{
		System.out.println("Sono presenti più films dal titolo \"" + titolo + "\": ");	
	}

	public static void numeroRicorrenza(int pos) 
	{
		System.out.println("\nRicorrenza " + ++pos + ":");
	}

	public static int chiediRicorrenzaDaRimuovere(Vector<Integer> posizioniRicorrenze) 
	{
		return InputDati.leggiIntero("\ninserisci il numero della ricorrenza da rimuovere (0 per annullare): ", 0, posizioniRicorrenze.size());
	}

	public static void annoNonPresente(int annoUscita) 
	{
		System.out.println("In archivio non sono presenti film il cui anno di uscita è " + annoUscita);	
	}

	public static void registaNonPresente(String nomeRegista) 
	{
		System.out.println("In archivio non sono presenti film il cui regista è " + nomeRegista);	
	}

	public static void stampaDati(Vector<Film> filmDaStampare) 
	{
		if(filmDaStampare.isEmpty())
		{
			System.out.println("In archivio non sono presenti film disponibili");
		}
		else
		{
			if(filmDaStampare.size()==1)
			{
				System.out.println("\nE' presente un film in archivio: ");
			}
			else
			{
				System.out.println("\nSono presenti " + filmDaStampare.size() + " films in archivio: ");
			}
			
			for(int i = 0; i < filmDaStampare.size(); i++)
			{
//				stampa la sottocategoria solo se è il primo elemento o se il sottogenere è cambiato (sono in ordine nel vettore)
				if(i == 0 || filmDaStampare.get(i).getSottoCategoria() != filmDaStampare.get(i-1).getSottoCategoria())
				{
					MessaggiSistemaView.cornice();
					System.out.println(filmDaStampare.get(i).getSottoCategoria());
					MessaggiSistemaView.cornice();
				}
				System.out.println("titolo: " + filmDaStampare.get(i).getTitolo());
			}
		}
	}

	public static void stampaPosizione(int i) 
	{
		System.out.println("\n" + (i+1) + ") ");
	}

	public static int selezionaPrestito(Vector<Film> films) 
	{
		return InputDati.leggiIntero("Seleziona il Film che vuoi ricevere in prestito (0 per annullare): ", 0, films.size());

	}

	public static void copieTutteInPrestito(String titolo) 
	{
		System.out.println("Tutte le copie di \"" + titolo + "\" sono in prestito!");	
	}

	public static void nessunaCorrispondenza() 
	{
		System.out.println("Nessun film corrispondente al criterio di ricerca");	
	}

	public static void noFilmsDisponibili() 
	{
		System.out.println("In archivio non sono presenti film disponibili");
	}

	public static void filmInArchivio() 
	{
		System.out.println("\nFilm in archivio: \n");	
	}

	public static void unFilmInArchivio() 
	{
		System.out.println("\nE' presente un film in archivio: ");	
	}

	public static void numeroFilmInArchivio(Vector<Film> films) 
	{
		System.out.println("\nSono presenti " + films.size() + " films in archivio: ");	
	}

	public static void stampaCategoria(Film film) 
	{
		System.out.println(film.getSottoCategoria());
	}

	public static void stampaTitolo(Film film) 
	{
		System.out.println("titolo: " + film.getTitolo());
	}
}
