package model.lzw;

import model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Daniel Boté Mayer
 * Classe LZW
 */
public class LZW 
{

	private static String File_Input = null;
	private static Encoder encode;
	private static Decoder decode;
	private static CreateFile cf;


	public void pasa_parametres(String accio, String fitxer) throws IOException
	{
		if(accio.equals("decode")) {
			decodifica(fitxer);
		}

		if(accio.equals("encode")) {
			codifica(fitxer);
		}

	}

	/**
	* Decodifica el fitxer que li pasem com a parametre.
	* @param fitxer_entrada. És el nom del fitxer que volem decodificar
	* @throws IOException
	*/

	public void decodifica (String fitxer_entrada) throws IOException
	{
		decode = new Decoder();
		decode.modifica_file_input(fitxer_entrada);
		StringBuffer sb = decode.Decode_String(fitxer_entrada);
		cf.CreateDecodedFile(sb.toString().getBytes(), fitxer_entrada, "lzw");
		//decode.Create_decoded_file(sb);

	}

	/**
	* Codifica el fitxer que se li passa com a paramatre. 
	* @param fitxer_entrada. És el nom del fitxer que volem codificar
	* @throws IOException
	*/
	public void codifica (String fitxer_entrada) throws IOException
	{
		StringBuffer input_string1 = new StringBuffer();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fitxer_entrada), StandardCharsets.UTF_8))
		{
			for (String line = null; (line = br.readLine()) != null;)
				input_string1 = input_string1.append(line);
		}
		encode = new Encoder();
		encode.modifica_file_input(fitxer_entrada);
		ArrayList<Byte> llista = encode.Encode_string(input_string1.toString());
		cf.CreateLZFile(llista, fitxer_entrada, ".lzw");
	}
}
