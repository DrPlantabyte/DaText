/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbench.datext;

import datext.util.BinaryConverter;
import java.util.Arrays;
import java.util.Locale;

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
}
