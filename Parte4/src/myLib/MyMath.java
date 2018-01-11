package myLib;

public class MyMath {

	/**
	 * Arrotondamento di un double a due cifre decimali (troncamento successive)
	 * @param x double da arrotondare
	 * @return il double arrotondato a due cifre decimali
	 */
	
	public static double arrotondamento(double x){
		x = Math.floor(x*100);
		x = x/100;
		return x;
		}
}
