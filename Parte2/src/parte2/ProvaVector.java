package parte2;

import java.util.Vector;

public class ProvaVector {

	public static void main(String[] args) 
	{
		Vector<String> vector = new Vector<>();
		vector.add("ciao");
		vector.add("miao");
		vector.add("zaino");
		
		vector.add(1, "fff");
		
		for(String s : vector)
		{
			System.out.println(s);
		}
	}

}
