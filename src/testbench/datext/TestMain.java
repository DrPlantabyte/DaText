/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbench.datext;

import datext.*;
import datext.util.*;
import java.util.*;

/**
 *
 * @author cybergnome
 */
public class TestMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		// TODO code test logic here
		System.out.println("Current locale is " + Locale.getDefault().toLanguageTag());
		
		System.out.println(Arrays.toString(BinaryConverter.stringToBytes("A0 00 0F 3E DE AD BE EF FE ED")));
		
		byte[] tester = {1,64,33,-23,-126,127,0,24,54,-34,-42,-1};
		testBinaryConversion(tester);
		testListReader("[herp, derp,,  burp,\n{\n\tsir=spamalot\n\trank=knight\n\tsays=ni!\n},\n]");
	}
	
	public static void testBinaryConversion(byte[] tester){
		System.out.println("Input: "+Arrays.toString(tester));
		String str = BinaryConverter.byteToString(tester);
		System.out.println("As String: "+str);
		byte[] out = BinaryConverter.stringToBytes(/*"DE AD   CAF EB AB");//*/str);
		System.out.println("Output: "+Arrays.toString(out));
		boolean pass = true;
		if(tester.length != out.length){
			pass = false;
		} else {
			for(int i = 0; i < tester.length; i++){
				pass = pass && (tester[i] == out[i]);
			}
		}
		System.out.println("Binary conversion test result: "+ pass);
	}

	private static void testListReader(String listWithBrackets) {
		int start = listWithBrackets.indexOf("[");
		int end = listWithBrackets.indexOf("]",start+1);
		System.out.println("Parsing list:\n"+listWithBrackets);
		List<DaTextVariable> l = ListHandler.parseListString(listWithBrackets.substring(start, end));
		
		boolean first = true;
		StringBuilder postProcessed = new StringBuilder();
		postProcessed.append("[");
		for(DaTextVariable v : l){
			if(!first){
				postProcessed.append(", ");
				first = false;
			}
			postProcessed.append(v.asText());
		}
		postProcessed.append("]\n");
		System.out.println("parsed result:\n"+postProcessed.toString());
		String s = postProcessed.toString();
		int start2 = s.indexOf("[");
		int end2 = s.indexOf("]",start2+1);
		List<DaTextVariable> l2 = ListHandler.parseListString(s.substring(start2, end2));
		
		boolean theSame =(l.size() == l2.size());
		for(int i = 0; i < l.size() && i < l2.size(); i++){
			theSame = theSame && l.get(i).asText().equals(l2.get(i).asText());
		}
		System.out.println("Test result:"+theSame);
	}
}
