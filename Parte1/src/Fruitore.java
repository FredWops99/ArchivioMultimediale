import java.io.Serializable;

public class Fruitore implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	private String nome; 
	private String cognome;
	private Data dataNascita; 
	private Data dataIscrizione;
	
	
	
	public Fruitore(String nome, String cognome, Data dataNascita, Data dataIscrizione) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataIscrizione = dataIscrizione;
	}
	
	
	
	public String getNome() 
	{
		return nome;
	}
	public String getCognome() 
	{
		return cognome;
	}
	public Data getDataNascita() 
	{
		return dataNascita;
	}
	public Data getDataIscrizione() 
	{
		return dataIscrizione;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public void setDataNascita(Data dataNascita) 
	{
		this.dataNascita = dataNascita;
	}
	public void setDataIscrizione(Data dataIscrizione) 
	{
		this.dataIscrizione = dataIscrizione;
	}

}
