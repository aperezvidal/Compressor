package model.lzw;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Daniel Boté Mayer
 * Classe Encoder
 */
public class Encoder 
{
	
	private static String File_Input = null;
	private static double MAX_TABLE_SIZE; //Max Table size is based on the bit length input.
	private static String LZWfilename;

	 /**
	 * Comprimeix un string en una llista de simbols i després els passa per a la creció del fitxer.
	 * @param input_string. Nom d'arxiu que es utilitzat per codificar
	 * @throws IOException
	 * @return List of encoded values
	 */
	public ArrayList<Byte> Encode_string(String input_string) throws IOException 
	{
		File_Input = input_string;
		MAX_TABLE_SIZE = Math.pow(2, 16);			
		double table_Size =  255;
		Map<String, Byte> TABLE = new HashMap<String, Byte>();
		
		for (int i = 0; i < 255 ; i++)
			TABLE.put("" + (char) i, (byte) i);

		String initString = new String(new byte[0], StandardCharsets.UTF_8);
		
		ArrayList<Byte> encoded_values = new ArrayList<Byte>();
		
		for (char symbol : input_string.toCharArray()) {
			String Str_Symbol = initString + symbol;
			if (TABLE.containsKey(Str_Symbol)) {
				initString = Str_Symbol;
			}
			else {
				//System.out.println("Inserting " + TABLE.get(initString) + " for " + initString);
				encoded_values.add(TABLE.get(initString));
			
				if(table_Size < MAX_TABLE_SIZE)
					TABLE.put(Str_Symbol, (byte) table_Size++);
				initString = "" + symbol;
			}
		}

		if (!initString.equals("")) {
			encoded_values.add(TABLE.get(initString));
		}
		//CreateLZWfile(encoded_values); 
		return encoded_values;
		
	}


	/**
	* Modifica el nom del fitxer d'entrada
	* @param a. Es el nom del ftixer.
	*/
	public void modifica_file_input(String a)
	{
		File_Input = a;
	}
}
