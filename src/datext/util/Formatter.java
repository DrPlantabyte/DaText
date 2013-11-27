/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import datext.DaText;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author Christopher Collin Hall
 */
public class Formatter {
	
	final static String[] CONTROL_CHARS = {"{","}","[","]","\n"};
	final static String[] TEXT_CONTROL_CHARS = {"{","}","[","]","\n","'","\""};
	final static String[] LIST_CONTROL_CHARS = {"[","]","\n",";"};
	
	public static String unescape(String input){
		return unescape(input,CONTROL_CHARS);
	}
	
	public static String escape(String input){
		return escape(input,CONTROL_CHARS);
	}
	
	public static String unescapeText(String input){
		return unescape(input,TEXT_CONTROL_CHARS);
	}
	
	public static String escapeText(String input){
		return escape(input,TEXT_CONTROL_CHARS);
	}
	
	public static String unescapeList(String input){
		return unescape(input,LIST_CONTROL_CHARS);
	}
	
	public static String escapeList(String input){
		return escape(input,LIST_CONTROL_CHARS);
	}
	
	public static String escape(String input, String... escapers){
		if(input == null){return new String();}
		String output = input.replace("\r\n", "\n");
		output = output.replace("\\", "\\\\");
		for(int i = 0; i < escapers.length; i++){
			output = output.replace(escapers[i], "\\"+escapers[i]);
		}
		return output;
	}
	
	public static String unescape(String input, String... escapers){
		String output = input.replace("\r\n", "\n");
		for(int i = escapers.length-1; i >= 0; i--){
			output = output.replace("\\"+escapers[i], escapers[i]);
		}
		output = output.replace("\\\\", "\\");
		return output;
	}
	
	
	public static String[] concatonateStringArrays(String[] A, String... B){
		String[] C = new String[A.length + B.length];
		int i = 0;
		for(int n = 0; n < A.length; n++){
			C[i] = A[n];
			i++;
		}
		for(int n = 0; n < B.length; n++){
			C[i] = B[n];
			i++;
		}
		return C;
	}
	
	
	/** Uses localized parser for decimal number parsing.
	 * 
	 * @param input String of a number. Must use either comma or period as 
	 * decimal mark and use whitespace as thousands mark
	 * @return The decimal value represented by the string
	 * @throws NumberFormatException Thrown if the number format does not 
	 * match the locale (or isn't even a number)
	 */
	public static double parseLocalizedNumber(String input) throws NumberFormatException{
		try {
			input = removeWhiteSpace(input);
			int numCommas = 0;
			int numPeriods = 0;
			int lastComma = -1;
			int lastPeriod = -1;
			for(int i = 0; i < input.length();  i++){
				char c = input.charAt(i);
				if(c == ','){
					numCommas++;
					lastComma = i;
				} else if(c == '.'){
					numPeriods++;
					lastPeriod = i;
				}
			}
			if((numCommas > 0 && numPeriods > 0)
					|| (numCommas > 1)
					|| (numPeriods > 1)){
				throw new NumberFormatException("'"+input+"' is not a valid number. "
					+ "Numbers should use whitespace as the thousands mark and "
					+ "either a comma or period as the decimal mark.");
			}
		/*	if(numCommas > 0 && numPeriods > 0){
				// uh-oh, trouble! assume whichever appears last is the decimal mark
				if(lastComma > lastPeriod){
					input = input.replace(".", "");
				} else {
					input = input.replace(",", "");
				}
			} else if(numCommas > 1){
				// US style thousands marks
				input = input.replace(",", "");
			} else if(numPeriods > 1){
				// Euro style thousands marks
				input = input.replace(".", "");
			} */ // its better to error than to guess
			return java.text.NumberFormat.getInstance(Locale.US).parse(input.replace(',', '.')).doubleValue();
		} catch (ParseException ex) {
			throw new NumberFormatException("'"+input+"' is not a valid number. "
					+ "Numbers should use whitespace as the thousands mark and "
					+ "either a comma or period as the decimal mark.");
			
		}
	}
	
	/**
	 * Converts a decimal to a String, using a comma as the decimal 
	 * marker iff <code>DaText.useCommaAsDecimalMark</code> is 
	 * <code>true</code>.
	 * @param num Number to convert to text
	 * @return The String representation of the number, in a format that can be 
	 * parsed by DaText parsers.
	 */
	public static String formatNumber(double num){
		String output = Double.toString(num);
		if(DaText.useCommaAsDecimalMark){
			output = output.replace('.', ',');
		}
		return output;
	}
	/**
	 * Removes whitespace characters from string.
	 * @param s
	 * @return 
	 */
	public static String removeWhiteSpace(String s){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < s.length(); i++){
			if(!Character.isWhitespace(s.charAt(i))){
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}
}
