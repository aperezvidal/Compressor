package model.lzw;

import java.io.IOException;
import java.util.*;

/**
 * @author Daniel Boté Mayer
 * Driver del Encoder
 */
public class DriverEncoder
{

	private static String fitxer;
	private static String accio;
	private static Encoder enc;
	private static ArrayList <Byte> llista;


	public static void main(String[] args) throws IOException
	{
		System.out.println("Introdueix acció:");
		System.out.println("1 - Modifica fitxer entrada");
		System.out.println("2 - Codifica paraula");
		Scanner scanner = new Scanner(System.in);
		accio = scanner.nextLine();
		System.out.println("Introdueix fitxer");
		fitxer = scanner.nextLine();
		Encoder enc = new Encoder();

		if(accio.equals("1")) {
			try {
				enc.modifica_file_input(fitxer);
				System.out.println("Ha modificat bé.");
			} catch (Exception e) {
				System.out.println("No ha modificat el fitxer.");
			}
		}

		if(accio.equals("2")) {
			try {
				llista = enc.Encode_string(fitxer);
				System.out.println("Ha codificat amb exit");

			} catch(Exception e) {
				System.out.println("No ha codificat la paraula");
			}
		}
	}
}
