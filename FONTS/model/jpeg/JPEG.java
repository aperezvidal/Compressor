package model.jpeg;


import java.io.IOException;

//import java.util.Scanner;
public class JPEG
{
    /***
     * passa_parametres. Crea una instància de la classe Imatge. Si q = 0, comprimeix. Altrament, si q = 1, descomprimeix.
     * @param args
     */
    /*public static void main(String[] args) {
        /*try {
            System.out.println("Escull acció:");
            System.out.println("0 - Comprimir");
            System.out.println("1 - descomprimir");
            Scanner scanner = new Scanner(System.in);
            String accio = scanner.nextLine();
            Integer a = Integer.parseInt(accio);
            System.out.println("Escull fitxer");
            String fitxer = scanner.nextLine();
            //System.out.println("creo instancia");
            Imatge i =  new Imatge(fitxer, a);



        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            System.out.println("error");

        }
    }*/

    public void pasa_parametres(String accio, String fitxer) throws IOException
    {
        int num = 0;
        if(accio.equals("encode"))
        {
            num = 0;
        }
        else if(accio.equals("decode"))
        {
            num = 1;
        }
        try {
            Imatge i = new Imatge(fitxer, num);
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            System.out.println("error");

        }
    }
}
