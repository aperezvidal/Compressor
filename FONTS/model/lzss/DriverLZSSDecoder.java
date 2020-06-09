package model.lzss;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marc Jímenez Gimeno
 * Driver per probar la classe LZSSDecoder
 */
public class DriverLZSSDecoder
{
    private static String fitxer;
    private static LZSSDecoder decoder;

    public static void main(String[] args) throws IOException
    {
        System.out.println("Introdueix Fitxer.lzss a descomprimir:");
        Scanner scanner = new Scanner(System.in);
        fitxer = scanner.nextLine();
        File af = new File(fitxer);
        while(!af.exists()) {
            System.out.println("El nom del fitxer introduit no existeix.");
            System.out.println("Introdueix un nom de fitxer valid.");
            fitxer = scanner.nextLine();
            af = new File(fitxer);
        }

        decoder = new LZSSDecoder();

        try {
            decoder.decode(fitxer);
            System.out.println("Ha decodificat el fitxer amb exit");
        } catch (Exception e) {
            System.out.println("No se ha pogut decodificar.");
        }

    }

}
