/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

/**
 *
 * @author Christopher Collin Hall
 */
public class Formatter {
	
	public static String unescape(String input){
		String output = input
				.replace("\r\n", "\n").replace("\\\n", "\n")
				.replace("\\[", "[").replace("\\]", "]")
				.replace("\\{", "{").replace("\\}", "}")
				.replace("\\\\", "\\");
		return output;
	}
	
	public static String escape(String input){
		String output = input
				.replace("\\", "\\\\")
				.replace("{", "\\{").replace("}", "\\}")
				.replace("[", "\\[").replace("]", "\\]")
				.replace("\r\n", "\n").replace("\n", "\\\n");
		return output;
	}
	
	public static String escape(String input, String... escapers){
		String output = input;
		output = output.replace("\\", "\\\\");
		for(int i = 0; i < escapers.length; i++){
			output = output.replace(escapers[i], "\\"+escapers[i]);
		}
		return output;
	}
	
	public static String unescape(String input, String... escapers){
		String output = input;
		for(int i = escapers.length-1; i >= 0; i--){
			output = output.replace("\\"+escapers[i], escapers[i]);
		}
		output = output.replace("\\\\", "\\");
		return output;
	}
}
