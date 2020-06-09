package model.lzss;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Marc Jímenez Gimeno
 * classe String2Byte2String
 */
public class String2Byte2String
{
    /***
     * Transforma un String binari de 0 i 1, a la seva codificacio en Bytes corresponents i els retorna en un ArrayList<Bytes>.
     * @param encodedText: String binari de 0 i 1.
     */
    public ArrayList<Byte> String2Byte(String encodedText)
    {

        ArrayList<Byte> salida=new ArrayList<>();

        int length = encodedText.length();
        int nBytes = length/8;
        nBytes = nBytes*8;

        byte b = 0x00;

        for(int i = 0; i < nBytes; i = i+8) {
            b = 0x00;
            for (int j = 0; j < 8; j++){
                byte nextB = (byte) encodedText.charAt(i+j);
                if (nextB == 0x30) nextB = 0x00;
                else nextB = 0x01;
                b = (byte) (b | nextB);
                if(j != 7)
                    b = (byte) (b << 1);
            }
            salida.add(b);
        }
        b = 0x00;
        int last = length%8;
        if(last != 0) {
            for(int i = 0; i < last; i++) {
               byte nextB = (byte) encodedText.charAt(nBytes + i);
               if(nextB == 0x30) nextB = 0x00;
               else nextB = 0x01;
               b = (byte) (b | nextB);
               b = (byte) (b << 1);
            }
            if(last != 7) {
                b = (byte) (b << (7 - last));
            }
            salida.add(b);
        }
        return salida;
    }

    /***
     * Transforma un fitxer codificat en bytes, a un string binari de 0 i 1s corresponents per a fer la descompressio.
     * @param encodedText: Nom d'un fitxer codificat en Bytes.
     */
    public String Byte2String(String encodedText)
    {
        StringBuilder sb = new StringBuilder();

        try(FileInputStream fis = new FileInputStream(encodedText)){
            int b = 0x00;
            while( (b = fis.read()) != -1) {
                int aux = b;
                for(int i = 0; i < 8; i++) {
                    aux = aux >> (7-i);
                    aux = aux & 0x01;
                    if(aux == 0x01) sb.append("1");
                    else sb.append("0");
                    aux = b;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
