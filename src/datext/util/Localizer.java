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
	 * @param input String of a number
	 * @param locale The locale to use for parsing
	 * @return The decimal value represented by the string
	 * @throws NumberFormatException Thrown if the number format does not 
	 * match the locale (or isn't even a number)
	 */
	public static double parseLocalizedNumber(String input, Locale locale) throws NumberFormatException{
		try {
			return java.text.NumberFormat.getInstance(locale).parse(input).doubleValue();
		} catch (ParseException ex) {
			throw new NumberFormatException("'"+input+"' is not a valid number in locale '"+locale.getDisplayName()+"'");
			
		}
		
	}
}
