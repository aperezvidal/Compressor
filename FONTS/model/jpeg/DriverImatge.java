package model.jpeg;

public class DriverImatge
{
    public static void main(String[] args)
    {
        try {
            System.out.println("creo instancia");
            //0--->compressio
            //1--->descompressio
            Imatge i =  new Imatge("prova.ppm", 1);



        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            System.out.println("error");
        }
    }

}
