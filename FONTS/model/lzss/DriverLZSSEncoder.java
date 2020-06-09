package model.lzss;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marc Jímenez Gimeno
 * Driver per probar la classe LZSSEncoder
 */
public class DriverLZSSEncoder
{
    private static String fitxer;
    private static LZSSEncoder encoder;

    public static void main(String[] args) throws IOException
    {
        System.out.println("Introdueix nom del Fitxer a comprimir");
        Scanner scanner = new Scanner(System.in);
        fitxer = scanner.nextLine();
        File af = new File(fitxer);
        while(!af.exists()) {
            System.out.println("El nom del fitxer introduit no existeix.");
            System.out.println("Introdueix un nom de fitxer valid.");
            fitxer = scanner.nextLine();
            af = new File(fitxer);
        }

        encoder = new LZSSEncoder();

        try {
            encoder.encode(fitxer);
            System.out.println("Ha codificat el fitxer amb exit");
        } catch (Exception e) {
            System.out.println("No s'ha pogut codificar.");
        }

    }
}
