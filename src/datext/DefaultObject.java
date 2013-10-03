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

	/**
	 * HashMap to hold all members
	 */
	private final Map<String, DaTextVariable> variables;

	/**
	 * Default constructor
	 */
	public DefaultObject() {
		variables = new HashMap<>();
	}

	/**
	 * Examines all of the member variables and returns a list of only those
	 * that are DaText objects.
	 *
	 * @return Returns a (read-only) collection of member variables that are
	 * objects.
	 */
	@Override
	public Collection<DaTextObject> getChildObjects() {
		readLock.lock();
		try {
			List<DaTextObject> children = new ArrayList<>();
			for (DaTextVariable v : variables.values()) {
				if (v instanceof DaTextObject) {
					children.add((DaTextObject) v);
				}
			}
			return Collections.unmodifiableList(children);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Gets the child object by the given variable name, if it exists.
	 *
	 * @param name Variable name that identifies the object being referenced.
	 * @return Returns a DaTextVariable representing the indicated variable, 	 * or <code>null</code> if no such object exists.
	 */
	@Override
	public DaTextVariable get(String name) {
		readLock.lock();
		try {
			return variables.get(name);
		} finally {
			readLock.unlock();
		}

	}

	/**
	 * Creates a copy that recursively copies all member variables. The copy is
	 * the root of the copied data tree
	 *
	 * @return A deep copy of this object
	 */
	@Override
	public DaTextObject deepCopy() {
		// TODO: deep copy
		readLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Stores an empty object in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 */
	@Override
	public void put(String key) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, DaTextObject value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, List value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, String value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, int value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, long value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, double value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	@Override
	public void put(String key, byte[] value) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Deletes a stored variable
	 *
	 * @param key Name of the variable to remove.
	 */
	@Override
	public void remove(String key) {
		writeLock.lock();
		try {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Enumerates all variables of this object. If there are no variables, an
	 * empty collection must be returned.
	 *
	 * @return A list of all variables (list will have size of 0 if there are
	 * none).
	 */
	@Override
	public Collection<DaTextVariable> getAllVariables() {
		readLock.lock();
		try {
			return Collections.unmodifiableCollection(variables.values());
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Returns a collection of variable names containing the names of every
	 * member variable.
	 *
	 * @return
	 */
	@Override
	public Collection<String> getVariableNames() {
		readLock.lock();
		try {
			return Collections.unmodifiableSet(variables.keySet());
		} finally {
			readLock.unlock();
		}
	}
}
