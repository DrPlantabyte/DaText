/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class Localizer {
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
			if(input.contains(",") && input.contains(".")){
				// uh-oh, trouble! assume whichever appears last is the decimal mark
				int comma = input.lastIndexOf(",");
				int period = input.lastIndexOf(".");
				if(comma > period){
					input = input.replace(".", "");
				} else {
					input = input.replace(",", "");
				}
			}
			return java.text.NumberFormat.getInstance(Locale.US).parse(input.replace(',', '.')).doubleValue();
		} catch (ParseException ex) {
			throw new NumberFormatException("'"+input+"' is not a valid number. "
					+ "Numbers should use whitespace as teh thousands mark and "
					+ "either a comma or period as the decimal mark.");
			
		}
		
	}
	/**
	 * Removes whitespace characters from string.
	 * @param s
	 * @return 
	 */
	private static String removeWhiteSpace(String s){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < s.length(); i++){
			if(!Character.isWhitespace(s.charAt(i))){
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}
}
