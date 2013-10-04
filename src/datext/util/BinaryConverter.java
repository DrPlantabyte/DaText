/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class BinaryConverter {
	
	final String[] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	
	public String byteToString(byte b){
		return (hex[(b >> 4)&0x0F]) + (hex[(b)&0x0F]);
	}
	
	public String byteToString(byte[] b){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < b.length; i++){
			sb.append(byteToString(b[i]));
		}
		return sb.toString();
	}
	
	public byte[] stringToBytes(String s) throws NumberFormatException{
		int i = 0;
		List<Byte> ba = new ArrayList<>();
		while(Character.isWhitespace(s.charAt(i))){
			i++;
		}
		byte b =0;
		char c = s.charAt(i);
		switch (c){
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
				b = (byte)(0x80);
				break;
			case '9':
				b = (byte)(0x90);
				break;
			case 'A':
			case 'a':
				b = (byte)(0xA0);
				break;
			case 'B':
			case 'b':
				b = (byte)(0xB0);
				break;
			case 'C':
			case 'c':
				b = (byte)(0xC0);
				break;
			case 'D':
			case 'd':
				b = (byte)(0xD0);
				break;
			case 'E':
			case 'e':
				b = (byte)(0xE0);
				break;
			case 'F':
			case 'f':
				b = (byte)(0xF0);
				break;
			default:
				throw new NumberFormatException("'"+c+"' is not a valid hexadecimal number");
		}
		while(Character.isWhitespace(s.charAt(i))){
			i++;
		}
		c = s.charAt(i);
		switch (c){
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
				throw new NumberFormatException("'"+c+"' is not a valid hexadecimal number");
		}
		ba.add(b);
				
	}
}
