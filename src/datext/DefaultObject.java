/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.*;

/**
 *
 * @author Christopher Collin Hall
 */
public class DefaultObject extends DaTextObject {
	
	/** HashMap to hold all members */
	private final Map<String,DaTextVariable> variables;
	
	public DefaultObject(){
		variables = new HashMap<>();
	}
	
	public Collection<DaTextObject> getChildObjects(){
		readLock.lock();
		try{
			List<DaTextObject> children = new ArrayList<>();
			for(DaTextVariable v : variables.values()){
				if(v instanceof DaTextObject){
					children.add((DaTextObject)v);
				}
			}
			return Collections.unmodifiableList(children);
		}finally {
			readLock.unlock();
		}
	}

	/**
	 * Gets the child object by the given variable name, if it exists. 
	 * @param name Variable name that identifies the object being referenced.
	 * @return Returns a DaTextVariable representing the indicated variable, 
	 * or <code>null</code> if no such object exists.
	 */
	@Override
	public DaTextVariable get(String name) {
		readLock.lock();
		try{
			return variables.get(name);
		}finally{
			readLock.unlock();
		}
		
	}

	@Override
	public DaTextObject deepCopy() {
		// TODO: deep copy
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, DaTextObject value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, List value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, Number[] value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, String value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, Number value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void put(String key, byte[] value) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void remove(String key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Collection<DaTextVariable> getAllVariables() {
		readLock.lock();try{return Collections.unmodifiableCollection(variables.values());}finally{readLock.unlock();}
	}

	@Override
	public Collection<String> getVariableNames() {
		readLock.lock();try{return Collections.unmodifiableSet(variables.keySet());}finally{readLock.unlock();}
	}

	
	
}
