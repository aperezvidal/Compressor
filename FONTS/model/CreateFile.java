package model;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileOutputStream;

/**
 * @author Marc Jímenez Gimeno
 * Clase Crear Fitxer
 */
public class CreateFile {

    /***
     * Constructora sense paramentres.
     */
    public CreateFile() {
    }

    /***
     * Crea un fitxer comprimit amb el mateix nom de entrada i la extensio .LZ (algorisme utilitzat per comprimir).
     * @param encoded_values: ArrayList debytes que conte el fitxer comprimit per el algorisme LZ.
     * @param File_Input: Nom del fitxer que s'ha comprimit.
     * @param LZ: Algorisme que s'ha utilitzat per a fer la compressio.
     * @throws IOException
     */
    public void CreateLZFile(ArrayList<Byte> encoded_values, String File_Input, String LZ) throws IOException {
        String FileName = File_Input.substring(0, File_Input.indexOf(".")) + LZ;
        BinaryOut bin_out = new BinaryOut(FileName);

        Iterator<Byte> Itr = encoded_values.iterator();
        while (Itr.hasNext()) {
            bin_out.write(Itr.next());
        }

        bin_out.flush();
        bin_out.close();
    }


    /***
     * Crea un nou fitxer amb el text descomprimit amb nom de "File_Input"_decoded.txt
     * @param decoded_values: ByteArray amb el text del fitxer un cop descomprimit.
     * @param File_Input: Nom del fitxer que s'ha descomprimit.
     * @param alg: Per saber amb quin fitxer s'ha descomprimit.
     * @throws IOException
     */
    public void CreateDecodedFile(byte[] decoded_values, String File_Input, String alg) throws IOException {
        String FileName = File_Input.substring(0, File_Input.indexOf(".")) + "_decoded" + alg + ".txt";

        FileOutputStream outputStream = new FileOutputStream(FileName);
        outputStream.write(decoded_values);
        outputStream.close();
    }
}