package model.lzss;

import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Marc Jímenez Gimeno
 * classe LZSS
 */
public class LZSS
{
    private static String File_Input = null;
    private static CreateFile c;
    private static LZSSEncoder encoder;
    private static LZSSDecoder decoder;

    public LZSS()
    {
        encoder = new LZSSEncoder();
        decoder = new LZSSDecoder();
        c = new CreateFile();
    }


    /***
     * Rebem els parametres que hem passat de la classe algorisme
     * @param accio: encode / decode segons la funcionalitat que volem fer
     * @param fitxer Nom del fitxer que volem comprimir / descomprimir
     * @throws IOException
     */
    public void pasa_parametres(String accio, String fitxer) throws IOException
    {
        if(accio.equals("decode"))
        {
            decodifica(fitxer);
        }

        if(accio.equals("encode"))
        {
            codifica(fitxer);
        }

    }


    /***
     * Codifiquem fitxer File_input i creem una nova .lzss file amb el fitxer comprimit
     * @param File_Input: Nom del fitxer que passem com a parametre.
     * @throws IOException
     */
    public void codifica (String File_Input) throws IOException
    {
        ArrayList<Byte> encodedStr = encoder.encode(File_Input);

        c.CreateLZFile(encodedStr, File_Input, ".lzss");
    }


    /***
     * Funcio que decodifica el fitxer encodedText i crea un nou file de sortida _decodedlzss.txt
     * @param encodedText: Nom del fitxer amb extensio .lzss que volem descomprimir.
     * @throws IOException
     */
    public void decodifica (String encodedText) throws IOException
    {
        byte[] salida = decoder.decode(encodedText);

        c.CreateDecodedFile(salida, encodedText, "lzss");
    }

}
