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
		
		testEscaping("Pown'd the \n\tmaster][ and his {\\deleted\\} dog too.");
		
		byte[] tester = {1,64,33,-23,-126,127,0,24,54,-34,-42,-1};
		testBinaryConversion(tester);
		testListReader("[herp,derp\\,ferp,,  burp,\n{\n\tsir=spamalot\n\trank=knight\n\tsays=ni!\n\tnestedList=[1, 2, [7,8,9,], 4, 5]\n},\n[A, B, C],\n]");
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
		System.out.println("Parsing list:\n"+listWithBrackets);
		List<DaTextVariable> l = ListHandler.parseListString(listWithBrackets);
		
	/*	boolean first = true;
		StringBuilder postProcessed = new StringBuilder();
		postProcessed.append("[");
		for(DaTextVariable v : l){
			if(!first){
				postProcessed.append(", ");
			}
			postProcessed.append(v.asText());
			first = false;
		}
		postProcessed.append("]\n");
		System.out.println("parsed result:\n"+postProcessed.toString());*/
		String output = ListHandler.listToString(l);//postProcessed.toString();
		System.out.println("parsed result:\n");//+output);
		for(int i = 0; i < l.size(); i++){
			System.out.println("["+i+"]\t"+l.get(i).asText());
		}
		List<DaTextVariable> l2 = ListHandler.parseListString(output);
		
		boolean theSame =(l.size() == l2.size());
		if(!theSame){
			System.out.println("Length mismatch: "+l.size() + " != " + l2.size());
		}
		for(int i = 0; i < l.size() && i < l2.size(); i++){
			theSame = theSame && l.get(i).asText().equals(l2.get(i).asText());
			if((l.get(i).asText().equals(l2.get(i).asText())) == false){
				System.out.println("mismatched list entry: '" + l.get(i).asText() + "' != '"+l2.get(i).asText()+"'");
			}
		}
		theSame = theSame && (ListHandler.listToString(l).equals(ListHandler.listToString(l2)));
		System.out.println("Test result:"+theSame);
	}
	
	static void testEscaping(String str){
		System.out.println("escaping string:\n"+str);
		System.out.println("\tas\n"+datext.util.Formatter.escape(str));
		System.out.println("escape test result: " + (
				datext.util.Formatter.unescape(datext.util.Formatter.escape(str)).equals(str)));
		
	}
}
