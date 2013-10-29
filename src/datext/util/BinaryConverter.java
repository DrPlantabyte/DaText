/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class BinaryConverter {
	
	final static String[] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	
	public static String byteToString(byte b){
		return (hex[(b >> 4)&0x0F]) + (hex[(b)&0x0F]);
	}
	
	public static String bytesToString(byte[] b){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < b.length; i++){
			sb.append(byteToString(b[i]));
			if(i != b.length-1 ){
				sb.append(' ');
			}
		}
		return sb.toString();
	}
	
	public static byte[] stringToBytes(String s) throws NumberFormatException {
		int i = 0;
		byte[] ba = new byte[(s.length() / 2 + 1)];
		int size = 0;
		while (i < s.length()) {
			while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
				i++;
			}
			if (i >= s.length()) {
				break;
			}
			byte b = 0;
			char c = s.charAt(i);
			switch (c) {
				case '0':
					b = 0x00;
					break;
				case '1':
					b = 0x10;
					break;
				case '2':
					b = 0x20;
					break;
				case '3':
					b = 0x30;
					break;
				case '4':
					b = 0x40;
					break;
				case '5':
					b = 0x50;
					break;
				case '6':
					b = 0x60;
					break;
				case '7':
					b = 0x70;
					break;
				case '8':
					b = (byte) (0x80);
					break;
				case '9':
					b = (byte) (0x90);
					break;
				case 'A':
				case 'a':
					b = (byte) (0xA0);
					break;
				case 'B':
				case 'b':
					b = (byte) (0xB0);
					break;
				case 'C':
				case 'c':
					b = (byte) (0xC0);
					break;
				case 'D':
				case 'd':
					b = (byte) (0xD0);
					break;
				case 'E':
				case 'e':
					b = (byte) (0xE0);
					break;
				case 'F':
				case 'f':
					b = (byte) (0xF0);
					break;
				default:
					throw new NumberFormatException("'" + c + "' is not a valid hexadecimal number");
			}
			i++;
			while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
				i++;
			}
			if (i >= s.length()) {
				// unpaired hex digit! only had half of a byte at the end!
				b = (byte)((b >>> 4) & 0x0F);
				ba[size] = b;
				size++;
				break;
			}
			c = s.charAt(i);
			switch (c) {
				case '0':
					b += 0;
					break;
				case '1':
					b += 1;
					break;
				case '2':
					b += 2;
					break;
				case '3':
					b += 3;
					break;
				case '4':
					b += 4;
					break;
				case '5':
					b += 5;
					break;
				case '6':
					b += 6;
					break;
				case '7':
					b += 7;
					break;
				case '8':
					b += 8;
					break;
				case '9':
					b += 9;
					break;
				case 'A':
				case 'a':
					b += (0x0A);
					break;
				case 'B':
				case 'b':
					b += (0x0B);
					break;
				case 'C':
				case 'c':
					b += (0x0C);
					break;
				case 'D':
				case 'd':
					b += (0x0D);
					break;
				case 'E':
				case 'e':
					b += (0x0E);
					break;
				case 'F':
				case 'f':
					b += (0x0F);
					break;
				default:
					throw new NumberFormatException("'" + c + "' is not a valid hexadecimal number");
			}
			ba[size] = b;
			size++;
			i++;
		}

		return Arrays.copyOfRange(ba, 0, size);
	}
}
