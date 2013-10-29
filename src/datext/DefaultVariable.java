/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.List;
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
			return datext.util.Localizer.parseLocalizedNumber(value, this.getLocale());
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
	public List<DaTextVariable> asList() throws UnsupportedOperationException {
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
	public void set(List<DaTextVariable> value) {
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
	
}
