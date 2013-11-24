/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import datext.util.BinaryConverter;
import datext.util.ListHandler;
import java.util.*;

/**
 *
 * @author Christopher Collin Hall
 */
public class DefaultObject extends DaTextObject {

	// TODO: update documentation
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
		readLock.lock();
		try {
			DefaultObject copy = new DefaultObject();
			copy.setAnnotation(this.getAnnotation());
			for(String key : variables.keySet()){
				copy.put(key, new DefaultVariable(variables.get(key).asText(),variables.get(key).getAnnotation()));
			}
			return copy;
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
			variables.put(key, null);
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Adds a new child variable to this object, overwriting any 
	 * pre-existing field or object with the same name.
	 * @param key Name of the object for use in the corresponding get method.
	 * @param value The variable to store.
	 */
	@Override
	public void put(String key, DaTextVariable value) {
		writeLock.lock();
		try {
			variables.put(key, value);
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
		put(key,(DaTextVariable)value);
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, List value, String annotation) {
		StringBuilder listString = new StringBuilder();
		boolean first = true;
		listString.append('[');
		for(Object o : value){
			if(!first){
				listString.append(',');
			}
			listString.append(datext.util.Formatter.escape(o.toString(),ListHandler.LIST_CONTROL_CHARS));
			first = false;
		}
		listString.append(']');
		put(key,new DefaultVariable(listString.toString(),annotation));
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, String value, String annotation) {
		put(key,new DefaultVariable(value,annotation));
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, int value, String annotation) {
		put(key,new DefaultVariable(((Number)value).toString(),annotation));
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, long value, String annotation) {
		put(key,new DefaultVariable(((Number)value).toString(),annotation));
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, double value, String annotation) {
		put(key,new DefaultVariable(this.formatNumber(value),annotation));
	}

	/**
	 * Stores a variable in this object, overwriting any previous object or
	 * value that may have previously been stored under the same field name.
	 *
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	@Override
	public void put(String key, byte[] value, String annotation) {
		put(key,new DefaultVariable(BinaryConverter.bytesToString(value),annotation));
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
			variables.remove(key);
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
