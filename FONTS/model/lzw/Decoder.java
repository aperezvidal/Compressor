package model.lzw; 

import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Boté Mayer
 * Classe Decoder
 */
public class Decoder 
{
	
	private static String File_Input = null;
	private static double MAX_TABLE_SIZE; 
	private static String LZWfilename;
		
	/***
	* Decodes the the compressed file to a decoded input file. 
	* @param file_Input2 //The name of compressed file.
	* @throws IOException
	* @return Decoded values. 
	 */
	public StringBuffer Decode_String(String file_Input2) throws IOException 
	{		
		
		MAX_TABLE_SIZE = Math.pow(2, 16);
		List<Byte> get_compress_values = new ArrayList<Byte>();
		int table_Size = 255;

		BinaryIn bin_in = null;
		bin_in = new BinaryIn(file_Input2);
		Byte value = 0;

		while(!bin_in.isEmpty())
		{
			value = bin_in.readByte();
			get_compress_values.add(value);
		}

         			
		Map<Byte, String> TABLE = new HashMap<Byte, String>();
		for (int i = 0; i < 255; i++)
		{
			TABLE.put((byte) i, "" + (char) i);
		}
		String Encode_values = new String(new byte[0], StandardCharsets.UTF_8) + (char) (byte) get_compress_values.remove(0);
		
		StringBuffer decoded_values = new StringBuffer(Encode_values);
		
		String get_value_from_table = null;
		for (byte check_key : get_compress_values)
		{
			if (TABLE.containsKey(check_key)) {
				get_value_from_table = TABLE.get(check_key);
			}
			else if (check_key == table_Size) {
				get_value_from_table = Encode_values + Encode_values.charAt(0);
			}
			
			decoded_values.append(get_value_from_table);
			
			if(table_Size < MAX_TABLE_SIZE ) {
				TABLE.put((byte) table_Size++, Encode_values + get_value_from_table.charAt(0));
			}
			Encode_values = get_value_from_table;
		}

		return decoded_values;
	}

	/**
	* Modifica el nom del fitxer d'entrada
	* @param s. Es el nom del fitxer.
	*/
	public void modifica_file_input(String s) 
	{
		File_Input = s;
	}
}
