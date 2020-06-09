package model.lz78;


import java.io.IOException;

/***
 * Driver que servix per provar la classe LZ78
 * @author Marc Simó Guzmán
 */
public class Driver_LZ78
{
    /***
     * Si args[0]=="codifica"
     * Si args[0]=="decodifica"
     * Altrament escribim per la sortida d'escritura estàndard l'Usage
     */
    public static void main(String[] args) throws IOException {
        LZ78 lz78= new LZ78();
        if (args[0].equals("codifica")){
            lz78.pasa_parametres("encode",args[1]);
        }
        else if (args[0].equals("decodifica")){
            lz78.pasa_parametres("decode",args[1]);
        }
        else System.out.println("Usage: java package......Driver_LZ78 'codifica'/'decodifica' 'fitxer_entrada'.txt");
    }
}
