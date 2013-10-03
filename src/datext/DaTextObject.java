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
	

	/** Points to the DaText above this one. If this is the root object, 
	 * then this will be null. */
	private DaTextObject parent = null;
	/** Locale for parsing data, default to English (programming standard) */
	private java.util.Locale locale = java.util.Locale.ENGLISH;
	/**
	 * Gets the locale being used by this object for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @return This object's locale
	 */
	public java.util.Locale getLocale(){
		readLock.lock();
		try{
			return locale;
		}finally {
			readLock.unlock();
		}
	}
	/**
	 * Sets the locale used by this object (but not necessarily child 
	 * objects) for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @param newLocale The new locale to use.
	 */
	public void setLocale(java.util.Locale newLocale){
		writeLock.lock();
		try{
			locale = newLocale;
		}finally {
			writeLock.unlock();
		}
	}
	/**
	 * Sets the locale used by this object and all child 
	 * objects for parsing numbers.
	 * <p/>This method is thread-safe.
	 * @param newLocale The new locale to use.
	 */
	public void setLocaleForAllChildren(java.util.Locale newLocale){
		// TODO: this method
		writeLock.lock();
		try{
			locale = newLocale;
//			for(DaTextObject child : getChildElements()){
//				child.setLocaleForAllChildren(newLocale);
//			}
		}finally {
			writeLock.unlock();
		}
	}
	
	
	/**
	 * Gets the root (aka Document) object that this object belongs to.
	 * <p/>This method is thread-safe.
	 * @return The top of the DaText tree, or this object if it is the root.
	 */
	public DaTextObject getRoot(){
		readLock.lock();
		try{
			DaTextObject root = this;
			while(root.getParent() != null){
				root = root.getParent();
			}
			return root;
		}finally {
			readLock.unlock();
		}
	}
	/** 
	 * Gets the object that owns this object.
	 * <p/>This method is thread-safe.
	 * @return The object above this one, or null if this object is the root.
	 */
	public DaTextObject getParent(){
		readLock.lock();
		try{
			return parent;
		}finally {
			readLock.unlock();
		}
	}
	
	/**
	 * Sets the owning object. This method alone is not sufficient to transfer 
	 * an object from one owner to another. Setting the parent to 
	 * <code>null</code> will make it behave as a root object
	 * <p/>This method is thread-safe.
	 * @param newParent New owning object
	 */
	protected void setParent(DaTextObject newParent){
		writeLock.lock();
		try{
			parent = newParent;
		}finally {
			writeLock.unlock();
		}
	}
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
	public List<DaTextVariable> getList(String key) throws UnsupportedOperationException{
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
		readLock.lock();try{return this.get(key).asText();}finally{readLock.unlock();}
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
	 * @return Returns the object, if it exists, or the newly created 
	 * one (with default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * and is not an object type.
	 */
	public DaTextObject getOrSet(String key) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if (exists(key)) {
				return get(key).asObject();
			}
			put(key);
			return get(key).asObject();
		} finally {
			writeLock.unlock();
		}
	}
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * but is not the correct type.
	 */
	public List<DaTextVariable> getOrSet(String key, List<DaTextVariable> defaultValue) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asList();
		} else {
			put(key,defaultValue);
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated variable exists 
	 * but is not the correct type.
	 */
	public String getOrSet(String key, String defaultValue) throws UnsupportedOperationException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asText();
		} else {
			put(key,defaultValue);
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public  int getOrSet(String key, int defaultValue) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asInt();
		} else {
			put(key,defaultValue);
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public long getOrSet(String key, long defaultValue) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asLong();
		} else {
			put(key,defaultValue);
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public double getOrSet(String key, double defaultValue) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asNumber();
		} else {
			put(key,defaultValue);
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
	 * @return Returns the referenced field, if it exists, or the newly created 
	 * one (with the provided default value) it it didn't exist.
	 * @throws UnsupportedOperationException Thrown if the indicated field exists 
	 * but is not the correct type.
	 * @throws NumberFormatException Thrown if the indicated field exists 
	 * but is not correctly formatted.
	 */
	public byte[] getOrSet(String key, byte[] defaultValue) throws UnsupportedOperationException, NumberFormatException{
		writeLock.lock();
		try {
			if(exists(key)){
			return get(key).asBinary();
		} else {
			put(key,defaultValue);
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
	 */
	public abstract void put(String key, List value);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void put(String key, Number[] value);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void put(String key, String value);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void put(String key, Number value);
	/**
	 * Stores a value in this object, overwriting any previous object 
	 * or value that may have previously been stored under the same 
	 * field name.
	 * @param key Name of the field for use in the corresponding get method.
	 * @param value Value to store
	 */
	public abstract void put(String key, byte[] value);
	
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
	
	@Override
	public String asText() throws UnsupportedOperationException {
		// TODO: replace with stream output (including escapes)
		readLock.lock();
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for(String n : this.getVariableNames()){
				sb.append(n).append("=").append(get(n).asText().replace("\n", "\\\n").replace("\"", "\\\"")).append("\n");
			}
			sb.append("}");
			return sb.toString();
		}finally{
			readLock.unlock();
		}
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
	public List<DaTextVariable> asList() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(DaTextObject.class.getSimpleName() 
				+ " is not convertable to list value");
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * Throws an UnsupportedOperationException because object types are not 
	 * convertible. 
	 */
	@Deprecated
	@Override
	public void set(List<DaTextVariable> value) {
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
