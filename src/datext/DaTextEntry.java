/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.Objects;

/**
 * A container to hold key=value pair with associated annotation.
 * @author Christopher Collin Hall
 */
public class DaTextEntry {
	public final String annotation;
	public final String key;
	public final String value;
	/**
	 * Constructs a new String-based DaTextEntry
	 * @param annotation The annotation
	 * @param key The variable key
	 * @param Value The variable value
	 */
	public DaTextEntry(String annotation, String key, String value){
		this.annotation = annotation;
		this.key = key;
		this.value = value;
	}
	/**
	 * Forwards to the hashCode() method of the key value
	 */
	@Override public int hashCode(){
		return key.hashCode();
	}

	/** WARNING: only the key value is check for equivalency (HashMap behavior). */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DaTextEntry other = (DaTextEntry) obj;
		if (!Objects.equals(this.key, other.key)) {
			return false;
		}
		return true;
	}
}
