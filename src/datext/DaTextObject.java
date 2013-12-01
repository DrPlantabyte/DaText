/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class DaTextObject extends DaTextVariable{
	
	
	// TODO: update documentation

	
	/**
	 * Examines all of the member variables and returns a list of only those 
	 * that are DaText objects.
	 * @return Returns a (read-only) collection of member variables that are objects.
	 */
	public abstract Collection<DaTextObject> getChildObjects();
	/**
	 * Returns a collection of variable names containing the names of every 
	 * member variable.
	 * @return 
	 */
	public abstract Collection<String> getVariableNames();
	
	/**
	 * Gets the child object by the given variable name, if it exists. 
	 * @param name Variable name that identifies the object being referenced.
	 * @return Returns a DaTextVariable representing the indicated variable, 
	 * or <code>null</code> if no such object exists.
	 */
	public abstract DaTextVariable get(String name);
	/**
	 * Gets the child object by the given variable name, if it exists. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getObject("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the object being referenced.
	 * @return Returns a DaTextObject representing the indicated object, 
	 * or <code>null</code> if no such object exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * an object type.
	 */
	public DaTextObject getObject(String key) throws UnsupportedOperationException{
		readLock.lock();try{return this.get(key).asObject();}finally{readLock.unlock();}
	}
	
	/**
	 * Gets a field by the given field name, if it exists, 
	 * and treats it as a list. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>List&lt;String&gt; colors = docRoot.getList("availableColors");</b><br/>
	 * System.out.println("Colors:");<br/>
	 * for(String color : colors){<br/>
	 * &nbsp;&nbsp;System.out.println("\t" + color);<br/>
	 * }<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns a list of Strings representing the indicated variable, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * a list type.
	 */
	public DaTextList getList(String key) throws UnsupportedOperationException{
		readLock.lock();try{return this.get(key).asList();}finally{readLock.unlock();}
	}
	/**
	 * Gets a field by the given field name, if it exists. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String flv = docRoot.getObject("properties").getText("flavor");</b><br/>
	 * System.out.println("Flavor profile: " + flv);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * a variable type.
	 */
	public String getText(String key) throws UnsupportedOperationException{
		readLock.lock();
		try {
			if (this.exists(key)) {
				return this.get(key).asText();
			} else {
				return null;
			}
		} finally {
			readLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an integer. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>int n = docRoot.getInt("numJellyBeans");</b><br/>
	 * System.out.println("There are " + n + " jelly beans");<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * a variable type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public int getInt(String key) throws UnsupportedOperationException, NumberFormatException{
		readLock.lock();try{return this.get(key).asInt();}finally{readLock.unlock();}
	}
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an integer.
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getObject("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns a DaTextObject representing the indicated variable, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * an variable type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public long getLong(String key) throws UnsupportedOperationException, NumberFormatException{
		readLock.lock();try{return this.get(key).asLong();}finally{readLock.unlock();}
	}
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as a decimal number. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>Double grams = docRoot.getNumber("weight");</b><br/>
	 * System.out.println("Mass: " + grams + "g");<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns the text value of the indicated field, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * a variable type.
	 * @throws NumberFormatException Thrown if the field could not be parsed as 
	 * a number (may be due to locale mismatch)
	 */
	public double getNumber(String key) throws UnsupportedOperationException, NumberFormatException{
		readLock.lock();try{return this.get(key).asNumber();}finally{readLock.unlock();}
	}
	/**
	 * Gets a field by the given field name, if it exists, and parses it 
	 * as an array of bytes. The bytes are given as a stream of hexadecimal 
	 * numbers read 2 at a time (whitespace is optional).
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long lot = docRoot.getObject("properties").getLong("lotNumber");</b><br/>
	 * System.out.println("Analyzing lot #" + lot);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @return Returns a DaTextObject representing the indicated variable, 
	 * or <code>null</code> if no such variable exists.
	 * @throws UnsupportedOperationException Thrown if the indicated variable is not 
	 * an variable type.
	 * @throws NumberFormatException Thrown if the hexadecimal stream is not 
	 * well-formatted
	 */
	public byte[] getBinary(String key) throws UnsupportedOperationException, NumberFormatException{
		readLock.lock();try{return this.get(key).asBinary();}finally{readLock.unlock();}
	}
	
	/**
	 * Determines whether or not a given variable is already defined
	 * @param name The variable name
	 * @return <code>true</code> if the given variable is a member of this 
	 * object, <code>false</code> otherwise.
	 */
	public boolean exists(String name){
		readLock.lock();try{return (get(name) != null);}finally{readLock.unlock();}
	}
	/**
	 * Creates a copy that recursively copies all member variables. The copy is 
	 * the root of the copied data tree
	 * @return A deep copy of this object
	 */
	public abstract DaTextObject deepCopy();
	/**
	 * Gets the child object by the given variable name, if it exists, 
	 * or adds a new object if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String title = docRoot.getOrSet("label").getOrSet("title", "Magical Beans");</b><br/>
	 * System.out.println("Title: " + title);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param annotation The annotation for this variable, may be null.
	 * @return Returns the object, if it exists, or the newly created 
	 * one (with default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * and is not an object type.
	 */
	public abstract DaTextObject getOrSet(String key,String annotation) throws UnsupportedOperationException;
	/**
	 * Gets the child object by the given variable name, if it exists, 
	 * or adds a new object if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String title = docRoot.getOrSet("label").getOrSet("title", "Magical Beans");</b><br/>
	 * System.out.println("Title: " + title);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @return Returns the object, if it exists, or the newly created 
	 * one (with default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * and is not an object type.
	 */
	public DaTextObject getOrSet(String key, DaTextObject defaultValue) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if (exists(key)) {
				return get(key).asObject();
			} else {
				putCopyOf(key, defaultValue);
				return get(key).asObject();
			}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>List&lt;String&gt; reviews = docRoot.getOrSet("label").getOrSet("reviews", defaultReviews);</b><br/>
	 * for(String s : reviews){<br/>
	 * &nbsp;&nbsp;System.out.println(s + "!");<br/>
	 * }<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * but is not the correct type.
	 */
	public DaTextList getOrSet(String key, List<DaTextVariable> defaultValue, String annotation) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asList();
		} else {
			put(key,defaultValue,annotation);
			return get(key).asList();
		}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>String title = docRoot.getOrSet("label").getOrSet("title", "Magical Beans");</b><br/>
	 * System.out.println("Title: " + title);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * but is not the correct type.
	 */
	public String getOrSet(String key, String defaultValue, String annotation) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asText();
		} else {
			put(key,defaultValue,annotation);
			return defaultValue;
		}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>int revision = docRoot.getOrSet("label").getOrSet("version", 1);</b><br/>
	 * System.out.println("Revision " + revision);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public  int getOrSet(String key, int defaultValue, String annotation) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asInt();
		} else {
			put(key,defaultValue,annotation);
			return defaultValue;
		}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>long time = docRoot.getOrSet("label").getOrSet("timestamp", System.currentTimeMillis());</b><br/>
	 * System.out.println("Last edited on " + (new Date(time)).toString());<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public long getOrSet(String key, long defaultValue, String annotation) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asLong();
		} else {
			put(key,defaultValue,annotation);
			return defaultValue;
		}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * <b>Double aspect = docRoot.getOrSet("label").getOrSet("aspect ratio", 1.5);</b><br/>
	 * System.out.println("Aspect ratio =  " + aspect);<br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public double getOrSet(String key, double defaultValue, String annotation) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asNumber();
		} else {
			put(key,defaultValue,annotation);
			return defaultValue;
		}
		} finally {
			writeLock.unlock();
		}
	}
	/**
	 * Gets a field by the given field name, if it exists, 
	 * or adds a new one if it doesn't already exist. 
	 * <p/>
	 * Example:<br/>
	 * <i>ex.dtx:</i><br/>
	 * <tt>
	 * numJellyBeans=273<br/>
	 * weight=158.052<br/>
	 * availableColors=[red,blue,yellow,green]<br/>
	 * # Properties of this batch of jelly beans <br/>
	 * properties={<br/>
	 * &nbsp;&nbsp;    lotNumber=29474936<br/>
	 * &nbsp;&nbsp;    texture=shiny<br/>
	 * &nbsp;&nbsp;    flavor=fruity<br/>
	 * &nbsp;&nbsp;    metadata=10 1F BE A2 50 00 22 3A FF<br/>
	 * }<br/>
	 * </tt>
	 * <br/>
	 * <i>java code:</i><br/>
	 * <code>
	 * DaTextObject docRoot = DaTextParser.parse(new File("ex.dtx"));<br/>
	 * ByteArrayOutputStream stream = new ByteArrayOutputStream();<br/>
	 * ImageIO.write(newLabelImg, "png", stream);<br/>
	 * <b>byte[] imageStream = docRoot.getOrSet("label").getOrSet("icon", stream.toByteArray());</b><br/>
	 * </code>
	 * @param key Variable name that identifies the variable being referenced.
	 * @param defaultValue The value to initialize the field to if is doesn't 
	 * already exist (ignored if it does already exist).
	 * @param annotation Annotation that describes this variable. May be null.
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public byte[] getOrSet(String key, byte[] defaultValue, String annotation) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asBinary();
		} else {
			put(key,defaultValue,annotation);
			return defaultValue;
		}
		} finally {
			writeLock.unlock();
		}
	}
	
	
	/**
	 * Adds a new child object to this object, overwriting any 
	 * pre-existing field or object with the same name.
	 * @param key Name of the object for use in the corresponding get method.
	 */
	public abstract void put(String key);
	/**
	 * Adds a new child variable to this object, overwriting any 
	 * pre-existing field or object with the same name.
	 * @param key Name of the object for use in the corresponding get method.
	 * @param value The variable to store.
	 */
	public abstract void put(String key, DaTextVariable value);
	/**
	 * Adds a new child object to this object, overwriting any 
	 * pre-existing field or object with the same name. This method assumes that 
	 * <code>value</code> is a root object (use <code>adopt(value)</code> if it 
	 * isn't).
	 * @param key Variable name of the object for use in the corresponding get method.
	 * @param value The 
	 */
	public abstract void put(String key, DaTextObject value);
	/**
	 * Clones the other variable and adds it to this object.
	 * @param key Name of the variable for use in the corresponding get method.
	 * @param other Element to clone and add.
	 */
	public void putCopyOf(String key, DaTextObject other){
		put(key,other.deepCopy());
	}
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, List<DaTextVariable> value, String annotation);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, String value, String annotation);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, int value, String annotation);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, long value, String annotation);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, double value, String annotation);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 * @param annotation Annotation that describes this variable. May be null.
	 */
	public abstract void put(String key, byte[] value, String annotation);
	
	/**
	 * Deletes a stored variable
	 * @param key Name of the variable to remove.
	 */
	public abstract void remove(String key);
	/**
	 * Enumerates all variables of this object. If there are no 
	 * variables, an empty collection must be returned.
	 * @return A list of all variables (list will have size of 0 if there 
	 * are none).
	 */
	public abstract Collection<DaTextVariable> getAllVariables();

	
	/**
	 * Implementation of the cloneable interface
	 * @return returns <code>deepCopy()</code>
	 */
	@Override public DaTextObject clone(){
		return deepCopy();
	}
	
	
	/**
	 * Gets the value of this variable as an object
	 * @return this object
	 */
	@Override public DaTextObject asObject() throws UnsupportedOperationException{
		return this;
	}
	/**
	 * Gets the value of this object as a String (serialized output)
	 * @return The value stored in this variable.
	 */
	@Override
	public String asText() {
		// TODO: replace with stream output (including escapes)
		readLock.lock();
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			
			sb.append("}");
			return sb.toString();
		}finally{
			readLock.unlock();
		}
	}
	
	// TODO: make an IO helper that has a collection of read and write methods for reading and writing files
	/**
	 * Writes this DaTextObjext as a string to the given stream writer. 
	 * The output is valid DaText that would create an identical copy if parsed.
	 * @param outputStream A writer to the stream.
	 * @throws java.io.IOException Thrown if there was an error writing to the 
	 * stream.
	 */
	public void serialize(java.io.Writer outputStream) throws java.io.IOException{
		serialize(outputStream,true,0);
	}
	/**
	 * Writes this DaTextObjext as a string to the given stream writer. 
	 * The output is valid DaText that would create an identical copy if parsed.
	 * @param outputStream A writer to the stream.
	 * @param doIndent If <code>true</code>, then tab characters will be 
	 * inserted to improve human legibility.
	 * @throws java.io.IOException Thrown if there was an error writing to the 
	 * stream.
	 */
	public void serialize(java.io.Writer outputStream, boolean doIndent) throws java.io.IOException{
		serialize(outputStream,doIndent,0);
	}
	
	/**
	 * Writes this DaTextObjext as a string to the given stream writer. 
	 * The output is valid DaText that would create an identical copy if parsed.
	 * @param outputStream A writer to the stream.
	 * @param doIndent If <code>true</code>, then tab characters will be 
	 * inserted to improve human legibility.
	 * @param indent If <code>doIndent</code> is <code>true</code>, then 
	 * this is the number of tabs to indent (typically the toplevel DaText 
	 * object would be written with an indent of 0)
	 * @throws java.io.IOException Thrown if there was an error writing to the 
	 * stream.
	 */
	@Override public void serialize(java.io.Writer outputStream, boolean doIndent, int indent) throws java.io.IOException{
			readLock.lock();
		try {
			if(indent < 0){indent = 0;}
			if(indent > 0){// no brackets for root object
				outputStream.write("{\r\n");
			}
			Collection<String> vars = this.getVariableNames();
			for(String key : vars){
				DaTextVariable v = this.get(key);
				if (v != null) {
					String annote = v.getAnnotation();
					if (annote != null) {
						String[] annoteLines = annote.split("\n");
						for (String a : annoteLines) {
							if (doIndent) {
								for (int i = 0; i < indent; i++) {
									outputStream.write("\t");
								}
							}
							outputStream.write("# ");
							outputStream.write(a.trim());
							outputStream.write("\r\n");
						}
					}
				}
				if(doIndent){
					for(int i = 0; i < indent; i++){
						outputStream.write("\t");
					}
				}
				outputStream.write(key);
				if(v != null){
					outputStream.write("=");
					v.serialize(outputStream, doIndent, indent+1);
					
				}
				outputStream.write("\r\n");
			}
			if (indent > 0) { // no brackets for root object
				if (doIndent) {
					for (int i = 1; i < indent; i++) {
						outputStream.write("\t");
					}
				}
				outputStream.write("}\r\n");
			}
		} finally {
			readLock.unlock();
		}
	}
	/**
	 * Writes this DaTextObjext as a string to the given stream writer. 
	 * The output is valid DaText that would create an identical copy if parsed.
	 * This is a wrapper for the serialize(Writer, doIndent, indent) 
	 * method, provided mostly for debugging convenience.
	 * @param outputStream A PrintStream to take the serialized text.
	 * @param doIndent If <code>true</code>, then tab characters will be 
	 * inserted to improve human legibility.
	 * @param indent If <code>doIndent</code> is <code>true</code>, then 
	 * this is the number of tabs to indent (typically the toplevel DaText 
	 * object would be written with an indent of 0)
	 * @throws java.io.IOException Thrown if there was an error writing to the 
	 * stream.
	 */
	public final void serialize(java.io.PrintStream outputStream, boolean doIndent, int indent) throws java.io.IOException{
		java.io.PrintWriter w = new java.io.PrintWriter(outputStream);
		serialize(w,doIndent,indent);
		w.flush();
	}
	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public int asInt() throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to numerical value");
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public long asLong() throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to numerical value");
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public double asNumber() throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to numerical value");
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public byte[] asBinary() throws UnsupportedOperationException, NumberFormatException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to numerical value");
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public DaTextList asList() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to " + DaTextList.class.getSimpleName());
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(String value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(int value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(long value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(double value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(byte[] value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(DaTextList value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(DaTextObject value) {
		throw new UnsupportedOperationException("Cannot change value of "+DaTextObject.class.getSimpleName()); 
	}

}
