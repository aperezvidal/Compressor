package model.jpeg;


import java.util.Scanner;
import java.io.IOException;

public class DriverJPEG
{
	public static void main(String[] args) throws IOException
	{

		System.out.println("Escull acció");
		System.out.println("h2 - Decodificar");
		Scanner scanner = new Scanner(System.in);
		String accio = scanner.nextLine();
		System.out.println("Introdueix fitxer:");
		String fitxer = scanner.nextLine();
		JPEG jpeg = new JPEG();
		if(accio.equals("1")) {
			System.out.println("Codificant");
			jpeg.pasa_parametres("encode", fitxer);
		}
		else if(accio.equals("2")) {
			System.out.println("Decodificant");
			jpeg.pasa_parametres("decode", fitxer);
		}

		
	}
}
