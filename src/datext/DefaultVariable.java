/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import datext.util.*;

/**
 * The default variable implementation just stores the string value of 
 * the variable and parses it at every request.
 * @author Christopher Collin Hall
 */
public class DefaultVariable extends DaTextVariable{
	private String value = "";
	/**
	 * 
	 * @param contents The value of this variable as text.
	 */
	public DefaultVariable(String contents){
		value = contents;
	}
	
	public DefaultVariable(String contents, String annotation){
		value = contents;
		this.setAnnotation(annotation);
	}
	
	@Override
	public String asText() throws UnsupportedOperationException {
		readLock.lock();
		try{
			return value;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int asInt() throws UnsupportedOperationException, NumberFormatException {
		readLock.lock();
		try{
			return Integer.parseInt(value);
		} finally {
			readLock.unlock();
		}
	}
	
	@Override
	public DaTextObject asObject(){
		// TODO: parse text as object
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public long asLong() throws UnsupportedOperationException, NumberFormatException {
		readLock.lock();
		try{
			return Long.parseLong(value);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public double asNumber() throws UnsupportedOperationException, NumberFormatException {
		readLock.lock();
		try{
			return datext.util.Formatter.parseLocalizedNumber(value);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public byte[] asBinary() throws UnsupportedOperationException, NumberFormatException {
		readLock.lock();
		try{
			return BinaryConverter.stringToBytes(value);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public DaTextList asList() throws UnsupportedOperationException {
		// TODO: parse text as list
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(String value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(int value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(long value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(double value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(byte[] value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(DaTextList value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void set(DaTextObject value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DaTextVariable clone() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	/**
	 * Writes the value of this variable as a string to the given 
	 * writer, applying the appropriate escape charafcters to make the 
	 * output readable by a DaTextParser. 
	 * Usually this is just forwarded to the asText() method, but some 
	 * implementations may override the default behavior for improved 
	 * performance in a streaming environment.
	 * @param outputStream A writer to the stream.
	 * @param doIndent If <code>true</code>, then tab characters will be 
	 * inserted at the beginning of each line to improve human legibility.
	 * @param indent If <code>doIndent</code> is <code>true</code>, then 
	 * this is the number of tabs to indent (typically the toplevel DaText 
	 * object would be written with an indent of 0)
	 * @throws java.io.IOException Thrown if there was an error writing to the 
	 * stream.
	 */
	@Override public void serialize(java.io.Writer outputStream, boolean doIndent, int indent) throws java.io.IOException{
		readLock.lock();
		try {
			if(this.asText().contains("\n")){
				// multiline, use quotes
				outputStream.write('\"');
				outputStream.write(Formatter.escapeQuotedText(this.asText()));
				outputStream.write('\"');
			} else {
				outputStream.write(Formatter.escapeText(this.asText()));
			}
		} finally {
			readLock.unlock();
		}
	}
}
