package model.jpeg;

import java.io.IOException;

public class Driver_readPPM
{

    public static void main(String[] args)
    {
        try {
            readPPM ppm = new readPPM("test256.ppm");
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
            System.out.println("error");

        }
        
    }
}
