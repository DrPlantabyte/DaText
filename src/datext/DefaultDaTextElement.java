/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.HashMap;
import java.util.List;

/**
 * This implementation of the DaTextElement uses strings for everything 
 * (no performance optimization). This is sufficient for configuration 
 * and saved-state files, but may not have enough performance for a run-time 
 * data container (use CachedDaTextElement or NativeDaTextElement instead).
 * 
 * @author Christopher Collin Hall
 */
public class DefaultDaTextElement extends DaTextElement {

	private final HashMap<String,DaTextEntry> map = new HashMap<>();
	
	@Override
	public DaTextElement getElement(String key) throws UnsupportedOperationException {
		readLock.lock();
		try{
			
		}finally {
			readLock.unlock();
		}throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<String> getList(String key) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getText(String key) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getInt(String key) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public long getLong(String key) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Double getNumber(String key) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte[] getBinary(String key) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DaTextElement getOrSetElement(String key) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<String> getOrSetList(String key, List<String> defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getOrSetText(String key, String defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getOrSetInt(String key, int defaultValue) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public long getOrSetLong(String key, long defaultValue) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Double getOrSetNumber(String key, Number defaultValue) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte[] getOrSetBinary(String key, byte[] defaultValue) throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putElement(String key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putCopyOf(String key, DaTextElement other) {
		writeLock.lock();
		try{
			//
		}finally {
			writeLock.unlock();
		}
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putValue(String key, List value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putValue(String key, Number[] value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putValue(String key, String value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putValue(String key, Number value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void putValue(String key, byte[] value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void remove(String key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<DaTextElement> getChildElements() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DaTextElement clone() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
