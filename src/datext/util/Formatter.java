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
	
	final static String[] CONTROL_CHARS = {"{","}","[","]","\n"};
	
	public static String unescape(String input){
		return unescape(input,CONTROL_CHARS);
	}
	
	public static String escape(String input){
		return escape(input,CONTROL_CHARS);
	}
	
	public static String escape(String input, String... escapers){
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
}
