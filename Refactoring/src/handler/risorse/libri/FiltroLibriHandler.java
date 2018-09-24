package handler.risorse.libri;

import java.util.Vector;

import controller.LibriController;
import model.Libri;
import model.Libro;
import view.LibriView;
import view.MessaggiSistemaView;

public class FiltroLibriHandler 
{
	public static Vector<Libro> filtraLibri(int scelta, boolean daPrenotare, LibriController libriController) 
	{
		Vector<Libro> libriFiltrati = null;
		String titoloParziale = null;
		int annoPubblicazione = 0;
		String nomeAutore = null;
		
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return null;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = LibriView.chiediTitolo(Libro.class);
				libriFiltrati = libriController.filtraLibriPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO DI PUBBLICAZIONE
			{
				annoPubblicazione = LibriView.chiediAnnoPubblicazione();
				libriFiltrati = libriController.filtraLibriPerAnnoPubblicazione(annoPubblicazione);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeAutore = LibriView.chiediAutore();
				libriFiltrati = libriController.filtraLibriPerAutori(nomeAutore);
				break;
			}
		}
		if (daPrenotare == false)
		{
			if(scelta == 1 && libriFiltrati.isEmpty()) 
			{
				LibriView.risorsaNonPresente(Libri.class, titoloParziale);
				return null;
			}
			if(scelta == 2 && libriFiltrati.isEmpty())
			{
				LibriView.annoNonPresente(annoPubblicazione);
				return null;
			}
			if(scelta == 3 && libriFiltrati.isEmpty())
			{
				LibriView.autoreNonPresente(nomeAutore);
				return null;
			}
			
			for (int i=0; i <libriFiltrati.size(); i++) 
			{
				MessaggiSistemaView.cornice(true,false);
				libriController.stampaDatiLibro(libriFiltrati.get(i), false);
			}
		}
		
		else if (daPrenotare == true)
		{
			return libriFiltrati;
		}
		//se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
}